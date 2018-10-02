package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.BichoIncorrectoException;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.UbicacionIncorrrectaException;
import epers.bichomon.model.ubicacion.busqueda.BusquedaFracasoException;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BichoServiceTest {

    private BichoService service = ServiceFactory.getBichoService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        Especie e = new Especie(1, "Rojomon", TipoBicho.FUEGO);
        testService.crearEntidad(e);

        Bicho b = new Bicho(1, e, 10);
        testService.crearEntidad(b);

        Guarderia g = new Guarderia("guarderia");
        g.abandonar(b);
        testService.crearEntidad(g);

        testService.crearEntidad(new Pueblo("Pueblo"));
        testService.crearEntidad(new Dojo("Dojo"));

        IntStream.range(2, 7).boxed().forEach(i -> testService.crearEntidad(new Bicho(i, e, 10)));

//        testService.crearEntidad(new Bicho(2, e, 10));
//        testService.crearEntidad(new Bicho(3, e, 10));
//        testService.crearEntidad(new Bicho(4, e, 10));
//        testService.crearEntidad(new Bicho(5, e, 10));
//        testService.crearEntidad(new Bicho(6, e, 10));
//        testService.crearEntidad(new Bicho(7, e, 10));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_falso_lanza_exception() {
        Entrenador ash = new Entrenador("ash", Sets.newHashSet(),
                this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(ash);

        assertThrows(BusquedaFracasoException.class, () -> this.service.buscar("ash"));
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_verdadero_obtiene_bicho_abandonado_en_guarderia() {
        Entrenador misty = new Entrenador("misty", Sets.newHashSet(),
                this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(misty);

        Bicho b = this.service.buscar("misty");
        assertEquals(1, b.getID());
        assertTrue(this.testService.recuperarByName(Entrenador.class, "misty").contains(1));
    }

    @Test
    void entrenador_abandona_en_pueblo_y_se_lanza_exception() {
        Bicho b1 = this.testService.recuperar(Bicho.class, 2);
        Bicho b2 = this.testService.recuperar(Bicho.class, 3);
        Entrenador pepe = new Entrenador("pepe", Sets.newHashSet(b1, b2),
                this.testService.recuperarByName(Pueblo.class, "Pueblo"), 3);
        this.testService.crearEntidad(pepe);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("pepe", 2));
    }

    @Test
    void entrenador_abandona_en_dojo_y_se_lanza_exception() {
        Entrenador brock = new Entrenador("brock",
                Sets.newHashSet(this.testService.recuperar(Bicho.class, 7)),
                this.testService.recuperarByName(Dojo.class, "Dojo"), 3);
        this.testService.crearEntidad(brock);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("brock", 7));
    }

    @Test
    void entrenador_abandona_bicho_que_no_tiene_y_se_lanza_exception() {
        Entrenador alberto = new Entrenador("alberto", Sets.newHashSet(),
                this.testService.recuperarByName(Dojo.class, "Dojo"));
        this.testService.crearEntidad(alberto);

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("alberto", 1));
    }

    @Test
    void entrenador_busca_pero_no_tiene_espacio() {
        Entrenador pedro = new Entrenador("pedro",
                Sets.newHashSet(this.testService.recuperar(Bicho.class, 7)),
                this.testService.recuperarByName(Dojo.class, "Dojo"), 1);
        this.testService.crearEntidad(pedro);

        assertNull(this.service.buscar("pedro"));
    }

    @Test
    void entrenador_abandona_pero_es_su_ultimo_bicho_lanza_exception() {
        Entrenador marce = new Entrenador("marce",
                Sets.newHashSet(this.testService.recuperar(Bicho.class, 4)),
                this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(marce);

        assertThrows(BichoIncorrectoException.class, () -> this.service.abandonar("marce", 4));
    }

    @Test
    void entrenador_abandona_y_su_bicho_queda_en_guarderia() {
        Bicho b1 = this.testService.recuperar(Bicho.class, 5);
        Bicho b2 = this.testService.recuperar(Bicho.class, 6);

        Entrenador lucas = new Entrenador("lucas", Sets.newHashSet(b1, b2),
                this.testService.recuperarByName(Guarderia.class, "guarderia"), 3);
        this.testService.crearEntidad(lucas);

        this.testService.actualizar(b1);
        this.testService.actualizar(b2);

        this.service.abandonar("lucas", 5);

        assertFalse(this.testService.recuperarByName(Entrenador.class, "lucas").contains(5));
        assertTrue(this.testService.recuperarByName(Guarderia.class, "guarderia").contains(5));
    }

    // TODO testear buscar en Ubicaciones Pueblo y Dojo
}
