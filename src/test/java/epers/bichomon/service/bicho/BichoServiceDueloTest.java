package epers.bichomon.service.bicho;

import epers.bichomon.model.ResultadoCombate;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.UbicacionIncorrrectaException;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BichoServiceDueloTest {

    private BichoService service = ServiceFactory.getBichoService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        Especie e = new Especie("Rojomon", TipoBicho.FUEGO, 10);
        testService.crearEntidad(e);

    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void duelo_esta_en_guarderia_raise_exception() {
        Guarderia g = new Guarderia("guarderia");
        testService.crearEntidad(g);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        testService.crearEntidad(b);

        Entrenador misty = new Entrenador("misty", Sets.newHashSet(b), g, 3);
        this.testService.crearEntidad(misty);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.duelo("misty", b.getID()));
    }

    @Test
    void duelo_esta_en_pueblo_raise_exception() {
        Pueblo p = new Pueblo("pueblo");
        testService.crearEntidad(p);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        testService.crearEntidad(b);

        Entrenador ash = new Entrenador("ash", Sets.newHashSet(b), p, 3);
        this.testService.crearEntidad(ash);

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.duelo("ash", b.getID()));
    }

    // TODO ResultadoCombate debe tener: ganador, perdedor y lista de ataques(quien ataca a quien, con que valor aleatorio y cuanto daño hace)

    @Test
    void duelo_en_dojo_sin_campeon_es_nuevo_campeon() {
        Dojo d = new Dojo("dojo");
        testService.crearEntidad(d);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        testService.crearEntidad(b);

        Entrenador brock = new Entrenador("brock", Sets.newHashSet(b), d, 3);
        this.testService.crearEntidad(brock);

        ResultadoCombate r = service.duelo("brock", b.getID());
        assertEquals(b, r.getGanador());
        assertEquals(b, testService.recuperarByName(Dojo.class, "dojo").getCampeon());
    }
}
