package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.BichoIncorrectoException;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.entrenador.XPuntos;
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
        testService.save(e);
        Especie lagarto = new Especie("Lagartomon", TipoBicho.FUEGO, 10);
        testService.save(lagarto);
        testService.save(new Especie("Reptilomon", TipoBicho.FUEGO, lagarto, 10));
        testService.save(new Especie("Dragonmon", TipoBicho.FUEGO, lagarto, 10));

        Bicho b = new Bicho(e);
        testService.save(b);

        Guarderia g = new Guarderia("guardaBicho");
        g.abandonar(b);
        testService.save(g);

        testService.save(new Guarderia("guarderia"));
        testService.save(new Pueblo("Pueblo"));
        testService.save(new Dojo("Dojo"));

        testService.save(Nivel.create());
        testService.save(new XPuntos());
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }


    private Entrenador newEntrenador(String nombre) {
        Entrenador e = new Entrenador(nombre, testService.getBy(Nivel.class, "nro", 1), testService.get(XPuntos.class, 1));
        this.testService.save(e);
        return e;
    }

    private Entrenador newEntrenador(String nombre, Ubicacion ubicacion) {
        return newEntrenador(nombre, ubicacion, Sets.newHashSet());
    }

    private Entrenador newEntrenador(String nombre, Ubicacion ubicacion, Set<Bicho> bichos) {
        Entrenador e = new Entrenador(nombre, testService.getBy(Nivel.class, "nro", 1), testService.get(XPuntos.class, 1), bichos);
        e.moverA(ubicacion);
        this.testService.save(e);
        return e;
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_falso_lanza_exception() {
        newEntrenador("ash", this.testService.getByName(Guarderia.class, "guarderia"));

        assertThrows(BusquedaFracasoException.class, () -> this.service.buscar("ash"));
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_verdadero_obtiene_bicho_abandonado_en_guarderia() {
        newEntrenador("misty", this.testService.getByName(Guarderia.class, "guardaBicho"));

        Bicho b = this.service.buscar("misty");
        assertEquals(1, b.getID());
        assertTrue(this.testService.getByName(Entrenador.class, "misty").contains(b));
    }

    @Test
    void entrenador_abandona_en_pueblo_y_se_lanza_exception() {
        Especie e = this.testService.getByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();
        newEntrenador("pepe", this.testService.getByName(Pueblo.class, "Pueblo"), Sets.newHashSet(b1, b2));

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("pepe", b1.getID()));
    }

    @Test
    void entrenador_abandona_en_dojo_y_se_lanza_exception() {
        Especie e = this.testService.getByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();
        newEntrenador("brock", this.testService.getByName(Dojo.class, "Dojo"), Sets.newHashSet(b1, b2));

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("brock", b1.getID()));
    }

    @Test
    void entrenador_abandona_bicho_que_no_tiene_y_se_lanza_exception() {
        newEntrenador("alberto", this.testService.getByName(Dojo.class, "Dojo"));

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("alberto", 1));
    }

    @Test
    void entrenador_busca_pero_no_tiene_espacio() {
        Especie e = this.testService.getByName(Especie.class, "Rojomon");
        newEntrenador("pedro", this.testService.getByName(Dojo.class, "Dojo"), Sets.newHashSet(e.crearBicho()));

        assertNull(this.service.buscar("pedro"));
    }

    @Test
    void entrenador_abandona_pero_es_su_ultimo_bicho_lanza_exception() {
        Especie e = this.testService.getByName(Especie.class, "Rojomon");
        Bicho b = e.crearBicho();
        newEntrenador("marce", this.testService.getByName(Guarderia.class, "guarderia"), Sets.newHashSet(b));

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("marce", b.getID()));
    }

    @Test
    void entrenador_abandona_y_su_bicho_queda_en_guarderia() {
        Especie e = this.testService.getByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();

        newEntrenador("lucas", this.testService.getByName(Guarderia.class, "guarderia"), Sets.newHashSet(b1, b2));

        this.service.abandonar("lucas", b1.getID());
        Bicho abandonado = this.testService.get(Bicho.class, b1.getID());
        assertFalse(this.testService.getByName(Entrenador.class, "lucas").contains(abandonado));
        assertTrue(this.testService.getByName(Guarderia.class, "guarderia").contains(abandonado));
    }

    @Test
    void entrenador_busca_y_solo_hay_bicho_abandonado_por_el() {
        Guarderia g = new Guarderia("guarderia2");
        testService.save(g);

        Especie e = this.testService.getByName(Especie.class, "Rojomon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();

        newEntrenador("marcos", g, Sets.newHashSet(b1, b2));

        this.service.abandonar("marcos", b1.getID());

        assertThrows(IndexOutOfBoundsException.class, () -> this.service.buscar("marcos"));
    }

    @Test
    void entrenador_busca_en_dojo_y_no_hay_campeon_devuelve_null() {
        Dojo d = new Dojo("CobraKai1");
        testService.save(d);
        newEntrenador("marta", d);

        assertNull(this.service.buscar("marta"));
    }

    @Test
    void entrenador_busca_en_dojo_con_campeon_raiz() {
        Bicho b = new Bicho(testService.getByName(Especie.class, "Lagartomon"));
        testService.save(b);
        Dojo d = new Dojo("CobraKai2", b);
        testService.save(d);
        newEntrenador("andrea", d);

        Bicho be = this.service.buscar("andrea");
        assertEquals("Lagartomon", be.getEspecie().getNombre());
        assertTrue(testService.getByName(Entrenador.class, "andrea").contains(be));
    }

    @Test
    void entrenador_busca_en_dojo_con_campeon_evolucion() {
        Bicho b = testService.getByName(Especie.class, "Dragonmon").crearBicho();
        testService.save(b);
        Dojo d = new Dojo("CobraKai3", b);
        testService.save(d);
        newEntrenador("laura", d);

        Bicho be = this.service.buscar("laura");
        assertEquals("Lagartomon", be.getEspecie().getNombre());
        assertTrue(testService.getByName(Entrenador.class, "laura").contains(be));
    }

    @Test
    void entrenador_busca_en_pueblo_con_una_sola_especie() {
        Pueblo p = new Pueblo("Paleta", Collections.singletonList(new Probabilidad(testService.getByName(Especie.class, "Lagartomon"), 100)));
        testService.save(p);
        newEntrenador("ana", p);

        Bicho b = this.service.buscar("ana");
        assertEquals("Lagartomon", b.getEspecie().getNombre());
        assertTrue(testService.getByName(Entrenador.class, "ana").contains(b));
    }

    @Test
    void entrenador_busca_en_pueblo_con_dos_especies() {
        Pueblo p = new Pueblo("Quilmes",
                Arrays.asList(new Probabilidad(testService.getByName(Especie.class, "Rojomon"), 90), new Probabilidad(testService.getByName(Especie.class, "Lagartomon"), 10)
                ));
        testService.save(p);
        newEntrenador("albert", p);

        Bicho b = this.service.buscar("albert");
        assertEquals("Rojomon", b.getEspecie().getNombre());
        assertTrue(testService.getByName(Entrenador.class, "albert").contains(b));
    }

    @Test
    void entrenador_busca_en_pueblo_con_tres_especies() {
        Pueblo p = new Pueblo("Bera",
                Arrays.asList(new Probabilidad(testService.getByName(Especie.class, "Reptilomon"), 10), new Probabilidad(testService.getByName(Especie.class, "Rojomon"), 20), new Probabilidad(testService.getByName(Especie.class, "Lagartomon"), 70)
                ));
        testService.save(p);
        newEntrenador("brian", p);

        Bicho b = this.service.buscar("brian");
        assertEquals("Lagartomon", b.getEspecie().getNombre());
        assertTrue(testService.getByName(Entrenador.class, "brian").contains(b));
    }

    @Test
    void entrenador_gana_xp_sube_nivel_de_a_1() {
        newEntrenador("ENivel1");

        Entrenador e = testService.getByName(Entrenador.class, "ENivel1");
        e.incXP(120);
        testService.upd(e);
        assertEquals(2, testService.getByName(Entrenador.class, "ENivel1").getNivel());

        e.incXP(300);
        testService.upd(e);
        assertEquals(3, testService.getByName(Entrenador.class, "ENivel1").getNivel());

        IntStream.range(4, 10).boxed().forEach(i -> {
            e.incXP(1000);
            testService.upd(e);
            assertEquals((int) i, testService.getByName(Entrenador.class, "ENivel1").getNivel());
        });

        e.incXP(1000);
        testService.upd(e);
        assertEquals(10, testService.getByName(Entrenador.class, "ENivel1").getNivel());
    }

    @Test
    void entrenador_gana_xp_sube_nivel_de_a_varios() {
        Entrenador e = newEntrenador("ENivel2");
        e.incXP(500);
        testService.upd(e);
        assertEquals(3, testService.getByName(Entrenador.class, "ENivel2").getNivel());

        e = testService.getByName(Entrenador.class, "ENivel2");
        e.incXP(2000);
        testService.upd(e);
        assertEquals(5, testService.getByName(Entrenador.class, "ENivel2").getNivel());

        e.incXP(2000);
        testService.upd(e);
        assertEquals(7, testService.getByName(Entrenador.class, "ENivel2").getNivel());

        e.incXP(200000);
        testService.upd(e);
        assertEquals(10, testService.getByName(Entrenador.class, "ENivel2").getNivel());
    }
}
