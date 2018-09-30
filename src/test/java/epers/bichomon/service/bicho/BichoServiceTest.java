package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        // TODO testear entrenador busca pero tiene lleno si inventario throw exception
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_falso_lanza_exception() {
        Entrenador ash = new Entrenador("ash");
        ash.moverA(this.testService.recuperarByName(Guarderia.class, "guarderia"));
        this.testService.crearEntidad(ash);

        assertThrows(BusquedaFracasoException.class, () -> this.service.buscar("ash"));
    }

    @Test
    void entrenador_busca_y_la_probabilidad_da_verdadero_obtiene_bicho_abandonado_en_guarderia() {
        Entrenador misty = new Entrenador("misty");
        misty.moverA(this.testService.recuperarByName(Guarderia.class, "guarderia"));
        this.testService.crearEntidad(misty);

        Bicho b = this.service.buscar("misty");
        assertEquals(1, b.getID());
    }

    @Test
    void entrenador_abandona_en_pueblo_y_se_lanza_exception() {
        Pueblo p = new Pueblo("Pueblo");
        this.testService.crearEntidad(p);

        Bicho b = new Bicho(3, this.testService.recuperarByName(Especie.class, "Rojomon"), 10);
        this.testService.crearEntidad(b);
        Entrenador pepe = new Entrenador("pepe", Sets.newHashSet(b));
        pepe.moverA(p);
        this.testService.crearEntidad(pepe);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.abandonar("pepe", 2));
    }
}
