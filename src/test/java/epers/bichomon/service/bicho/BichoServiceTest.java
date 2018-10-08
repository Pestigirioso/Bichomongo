package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.BichoIncorrectoException;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.*;
import epers.bichomon.model.ubicacion.busqueda.BusquedaFracasoException;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BichoServiceTest {

    private BichoService service = ServiceFactory.getBichoService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        Especie e = new Especie("Rojomon", TipoBicho.FUEGO, 10);
        testService.crearEntidad(e);
        Especie lagarto = new Especie("Lagartomon", TipoBicho.FUEGO, 10);
        testService.crearEntidad(lagarto);
        testService.crearEntidad(new Especie("Reptilomon", TipoBicho.FUEGO, lagarto, 10));
        testService.crearEntidad(new Especie("Dragonmon", TipoBicho.FUEGO, lagarto, 10));

        Bicho b = new Bicho(e);
        testService.crearEntidad(b);

        Guarderia g = new Guarderia("guardaBicho");
        g.abandonar(b);
        testService.crearEntidad(g);

        testService.crearEntidad(new Guarderia("guarderia"));
        testService.crearEntidad(new Pueblo("Pueblo"));
        testService.crearEntidad(new Dojo("Dojo"));

        testService.crearEntidad(new Entrenador("nivel", Nivel.create()));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    private Entrenador newEntrenador(String nombre, Ubicacion ubicacion) {
        return newEntrenador(nombre, ubicacion, Sets.newHashSet());
    }

    private Entrenador newEntrenador(String nombre, Ubicacion ubicacion, Set<Bicho> bichos) {
        Entrenador e = new Entrenador(nombre, testService.recuperarBy(Nivel.class, "nro", 1), bichos);
        e.moverA(ubicacion);
        this.testService.crearEntidad(e);
        return e;
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_falso_lanza_exception() {
        newEntrenador("ash", this.testService.recuperarByName(Guarderia.class, "guarderia"));

        assertThrows(BusquedaFracasoException.class, () -> this.service.buscar("ash"));
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_verdadero_obtiene_bicho_abandonado_en_guarderia() {
        newEntrenador("misty", this.testService.recuperarByName(Guarderia.class, "guardaBicho"));

        Bicho b = this.service.buscar("misty");
        assertEquals(1, b.getID());
        assertTrue(this.testService.recuperarByName(Entrenador.class, "misty").contains(b));
    }

    @Test
    void entrenador_abandona_en_pueblo_y_se_lanza_exception() {
        Especie e = this.testService.recuperarByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();
        newEntrenador("pepe", this.testService.recuperarByName(Pueblo.class, "Pueblo"), Sets.newHashSet(b1, b2));

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("pepe", b1.getID()));
    }

    @Test
    void entrenador_abandona_en_dojo_y_se_lanza_exception() {
        Especie e = this.testService.recuperarByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();
        newEntrenador("brock", this.testService.recuperarByName(Dojo.class, "Dojo"), Sets.newHashSet(b1, b2));

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("brock", b1.getID()));
    }

    @Test
    void entrenador_abandona_bicho_que_no_tiene_y_se_lanza_exception() {
        newEntrenador("alberto", this.testService.recuperarByName(Dojo.class, "Dojo"));

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("alberto", 1));
    }

    @Test
    void entrenador_busca_pero_no_tiene_espacio() {
        Especie e = this.testService.recuperarByName(Especie.class, "Rojomon");
        newEntrenador("pedro", this.testService.recuperarByName(Dojo.class, "Dojo"), Sets.newHashSet(e.crearBicho()));

        assertNull(this.service.buscar("pedro"));
    }

    @Test
    void entrenador_abandona_pero_es_su_ultimo_bicho_lanza_exception() {
        Especie e = this.testService.recuperarByName(Especie.class, "Rojomon");
        Bicho b = e.crearBicho();
        newEntrenador("marce", this.testService.recuperarByName(Guarderia.class, "guarderia"), Sets.newHashSet(b));

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("marce", b.getID()));
    }

    @Test
    void entrenador_abandona_y_su_bicho_queda_en_guarderia() {
        Especie e = this.testService.recuperarByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();

        newEntrenador("lucas", this.testService.recuperarByName(Guarderia.class, "guarderia"), Sets.newHashSet(b1, b2));

        this.service.abandonar("lucas", b1.getID());
        Bicho abandonado = this.testService.recuperar(Bicho.class, b1.getID());
        assertFalse(this.testService.recuperarByName(Entrenador.class, "lucas").contains(abandonado));
        assertTrue(this.testService.recuperarByName(Guarderia.class, "guarderia").contains(abandonado));
    }

    @Test
    void entrenador_busca_y_solo_hay_bicho_abandonado_por_el() {
        Guarderia g = new Guarderia("guarderia2");
        testService.crearEntidad(g);

        Especie e = this.testService.recuperarByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();

        newEntrenador("marcos", g, Sets.newHashSet(b1, b2));

        this.service.abandonar("marcos", b1.getID());

        assertThrows(IndexOutOfBoundsException.class, () -> this.service.buscar("marcos"));
    }

    @Test
    void entrenador_busca_en_dojo_y_no_hay_campeon_devuelve_null() {
        Dojo d = new Dojo("CobraKai1");
        testService.crearEntidad(d);
        newEntrenador("marta", d);

        assertNull(this.service.buscar("marta"));
    }

    @Test
    void entrenador_busca_en_dojo_con_campeon_raiz() {
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "Lagartomon"));
        testService.crearEntidad(b);
        Dojo d = new Dojo("CobraKai2", b);
        testService.crearEntidad(d);
        newEntrenador("andrea", d);

        Bicho be = this.service.buscar("andrea");
        assertEquals("Lagartomon", be.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "andrea").contains(be));
    }

    @Test
    void entrenador_busca_en_dojo_con_campeon_evolucion() {
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "Dragonmon"));
        testService.crearEntidad(b);
        Dojo d = new Dojo("CobraKai3", b);
        testService.crearEntidad(d);
        newEntrenador("laura", d);

        Bicho be = this.service.buscar("laura");
        assertEquals("Lagartomon", be.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "laura").contains(be));
    }

    @Test
    void entrenador_busca_en_pueblo_con_una_sola_especie() {
        Pueblo p = new Pueblo("Paleta",
                Collections.singletonList(new Probabilidad(testService.recuperarByName(Especie.class, "Lagartomon"), 100)));
        testService.crearEntidad(p);
        newEntrenador("ana", p);

        Bicho b = this.service.buscar("ana");
        assertEquals("Lagartomon", b.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "ana").contains(b));
    }

    @Test
    void entrenador_busca_en_pueblo_con_dos_especies() {
        Pueblo p = new Pueblo("Quilmes",
                Arrays.asList(
                        new Probabilidad(testService.recuperarByName(Especie.class, "Rojomon"), 90),
                        new Probabilidad(testService.recuperarByName(Especie.class, "Lagartomon"), 10)
                ));
        testService.crearEntidad(p);
        newEntrenador("albert", p);

        Bicho b = this.service.buscar("albert");
        assertEquals("Rojomon", b.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "albert").contains(b));
    }

    @Test
    void entrenador_busca_en_pueblo_con_tres_especies() {
        Pueblo p = new Pueblo("Bera",
                Arrays.asList(
                        new Probabilidad(testService.recuperarByName(Especie.class, "Reptilomon"), 10),
                        new Probabilidad(testService.recuperarByName(Especie.class, "Rojomon"), 20),
                        new Probabilidad(testService.recuperarByName(Especie.class, "Lagartomon"), 70)
                ));
        testService.crearEntidad(p);
        newEntrenador("brian", p);

        Bicho b = this.service.buscar("brian");
        assertEquals("Lagartomon", b.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "brian").contains(b));
    }

    @Test
    void entrenador_gana_xp_sube_nivel_de_a_1() {
        testService.crearEntidad(new Entrenador("ENivel1",
                testService.recuperarBy(Nivel.class, "nro", 1)));

        Entrenador e = testService.recuperarByName(Entrenador.class, "ENivel1");
        e.incXP(120);
        testService.actualizar(e);
        assertEquals(2, testService.recuperarByName(Entrenador.class, "ENivel1").getNivel());

        e.incXP(300);
        testService.actualizar(e);
        assertEquals(3, testService.recuperarByName(Entrenador.class, "ENivel1").getNivel());

        IntStream.range(4, 10).boxed().forEach(i -> {
            e.incXP(1000);
            testService.actualizar(e);
            assertEquals((int) i, testService.recuperarByName(Entrenador.class, "ENivel1").getNivel());
        });

        e.incXP(1000);
        testService.actualizar(e);
        assertEquals(10, testService.recuperarByName(Entrenador.class, "ENivel1").getNivel());
    }

    @Test
    void entrenador_gana_xp_sube_nivel_de_a_varios() {
        testService.crearEntidad(new Entrenador("ENivel2",
                testService.recuperarBy(Nivel.class, "nro", 1)));

        Entrenador e = testService.recuperarByName(Entrenador.class, "ENivel2");
        e.incXP(500);
        testService.actualizar(e);
        assertEquals(3, testService.recuperarByName(Entrenador.class, "ENivel2").getNivel());

        e.incXP(2000);
        testService.actualizar(e);
        assertEquals(5, testService.recuperarByName(Entrenador.class, "ENivel2").getNivel());

        e.incXP(2000);
        testService.actualizar(e);
        assertEquals(7, testService.recuperarByName(Entrenador.class, "ENivel2").getNivel());

        e.incXP(200000);
        testService.actualizar(e);
        assertEquals(10, testService.recuperarByName(Entrenador.class, "ENivel2").getNivel());
    }
}
