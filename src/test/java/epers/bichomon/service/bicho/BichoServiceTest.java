package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.BichoIncorrectoException;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.*;
import epers.bichomon.model.ubicacion.busqueda.BusquedaFracasoException;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
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
        IntStream.range(2, 16).boxed().forEach(i -> testService.crearEntidad(new Bicho(e)));

        Guarderia g = new Guarderia("guardaBicho");
        g.abandonar(b);
        testService.crearEntidad(g);

        testService.crearEntidad(new Guarderia("guarderia"));
        testService.crearEntidad(new Pueblo("Pueblo"));
        testService.crearEntidad(new Dojo("Dojo"));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_falso_lanza_exception() {
        Entrenador ash = new Entrenador("ash", Sets.newHashSet(), this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(ash);

        assertThrows(BusquedaFracasoException.class, () -> this.service.buscar("ash"));
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_verdadero_obtiene_bicho_abandonado_en_guarderia() {
        Entrenador misty = new Entrenador("misty", Sets.newHashSet(), this.testService.recuperarByName(Guarderia.class, "guardaBicho"), 3);
        this.testService.crearEntidad(misty);

        Bicho b = this.service.buscar("misty");
        assertEquals(1, b.getID());
        assertTrue(this.testService.recuperarByName(Entrenador.class, "misty").contains(b));
    }

    @Test
    void entrenador_abandona_en_pueblo_y_se_lanza_exception() {
        Bicho b1 = this.testService.recuperar(Bicho.class, 2);
        Bicho b2 = this.testService.recuperar(Bicho.class, 3);
        Entrenador pepe = new Entrenador("pepe", Sets.newHashSet(b1, b2), this.testService.recuperarByName(Pueblo.class, "Pueblo"), 3);
        this.testService.crearEntidad(pepe);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("pepe", 2));
    }

    @Test
    void entrenador_abandona_en_dojo_y_se_lanza_exception() {
        Bicho b1 = this.testService.recuperar(Bicho.class, 8);
        Bicho b2 = this.testService.recuperar(Bicho.class, 9);
        Entrenador brock = new Entrenador("brock", Sets.newHashSet(b1, b2), this.testService.recuperarByName(Dojo.class, "Dojo"), 3);
        this.testService.crearEntidad(brock);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("brock", 8));
    }

    @Test
    void entrenador_abandona_bicho_que_no_tiene_y_se_lanza_exception() {
        Entrenador alberto = new Entrenador("alberto", Sets.newHashSet(), this.testService.recuperarByName(Dojo.class, "Dojo"));
        this.testService.crearEntidad(alberto);

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("alberto", 1));
    }

    @Test
    void entrenador_busca_pero_no_tiene_espacio() {
        Entrenador pedro = new Entrenador("pedro", Sets.newHashSet(this.testService.recuperar(Bicho.class, 7)), this.testService.recuperarByName(Dojo.class, "Dojo"), 1);
        this.testService.crearEntidad(pedro);

        assertNull(this.service.buscar("pedro"));
    }

    @Test
    void entrenador_abandona_pero_es_su_ultimo_bicho_lanza_exception() {
        Entrenador marce = new Entrenador("marce", Sets.newHashSet(this.testService.recuperar(Bicho.class, 4)), this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(marce);

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("marce", 4));
    }

    @Test
    void entrenador_abandona_y_su_bicho_queda_en_guarderia() {
        Bicho b1 = this.testService.recuperar(Bicho.class, 5);
        Bicho b2 = this.testService.recuperar(Bicho.class, 6);

        Entrenador lucas = new Entrenador("lucas", Sets.newHashSet(b1, b2), this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(lucas);

        this.service.abandonar("lucas", 5);
        Bicho abandonado = this.testService.recuperar(Bicho.class, 5);
        assertFalse(this.testService.recuperarByName(Entrenador.class, "lucas").contains(abandonado));
        assertTrue(this.testService.recuperarByName(Guarderia.class, "guarderia").contains(abandonado));
    }

    @Test
    void entrenador_busca_y_solo_hay_bicho_abandonado_por_el() {
        Guarderia g = new Guarderia("guarderia2");
        testService.crearEntidad(g);

        Bicho b1 = this.testService.recuperar(Bicho.class, 10);
        Bicho b2 = this.testService.recuperar(Bicho.class, 11);

        Entrenador marcos = new Entrenador("marcos", Sets.newHashSet(b1, b2), g, 3);
        this.testService.crearEntidad(marcos);

        this.service.abandonar("marcos", 10);

        assertThrows(IndexOutOfBoundsException.class, () -> this.service.buscar("marcos"));
    }

    @Test
    void entrenador_busca_en_dojo_y_no_hay_campeon_devuelve_null() {
        Dojo d = new Dojo("CobraKai1");
        testService.crearEntidad(d);
        Entrenador marta = new Entrenador("marta", Sets.newHashSet(), d, 3);
        this.testService.crearEntidad(marta);

        assertNull(this.service.buscar("marta"));
    }

    @Test
    void entrenador_busca_en_dojo_con_campeon_raiz() {
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "Lagartomon"));
        testService.crearEntidad(b);
        Dojo d = new Dojo("CobraKai2", b);
        testService.crearEntidad(d);
        Entrenador andrea = new Entrenador("andrea", Sets.newHashSet(), d, 3);
        this.testService.crearEntidad(andrea);

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
        Entrenador laura = new Entrenador("laura", Sets.newHashSet(), d, 3);
        this.testService.crearEntidad(laura);

        Bicho be = this.service.buscar("laura");
        assertEquals("Lagartomon", be.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "laura").contains(be));
    }

    @Test
    void entrenador_busca_en_pueblo_con_una_sola_especie() {
        Pueblo p = new Pueblo("Paleta",
                Collections.singletonList(new Probabilidad(testService.recuperarByName(Especie.class, "Lagartomon"), 100)));
        testService.crearEntidad(p);
        Entrenador ana = new Entrenador("ana", Sets.newHashSet(), p, 3);
        this.testService.crearEntidad(ana);

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
        Entrenador albert = new Entrenador("albert", Sets.newHashSet(), p, 3);
        this.testService.crearEntidad(albert);

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
        Entrenador brian = new Entrenador("brian", Sets.newHashSet(), p, 3);
        this.testService.crearEntidad(brian);

        Bicho b = this.service.buscar("brian");
        assertEquals("Lagartomon", b.getEspecie().getNombre());
        assertTrue(testService.recuperarByName(Entrenador.class, "brian").contains(b));
    }
}
