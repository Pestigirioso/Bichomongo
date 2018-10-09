package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.entrenador.XPuntos;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.*;
import epers.bichomon.model.ubicacion.duelo.ResultadoCombate;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BichoServiceDueloTest {

    private BichoService service = ServiceFactory.getBichoService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        Especie e = new Especie("Rojomon", TipoBicho.FUEGO, 10);
        testService.crearEntidad(e);

        testService.crearEntidad(new Entrenador("nivel", Nivel.create(), new XPuntos()));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    private Entrenador newEntrenador(String nombre, Ubicacion ubicacion, Set<Bicho> bichos) {
        Entrenador e = new Entrenador(nombre, testService.recuperarBy(Nivel.class, "nro", 1),
                testService.recuperar(XPuntos.class, 1), bichos);
        e.moverA(ubicacion);
        this.testService.crearEntidad(e);
        return e;
    }

    @Test
    void duelo_esta_en_guarderia_raise_exception() {
        Guarderia g = new Guarderia("guarderia");
        testService.crearEntidad(g);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("misty", g, Sets.newHashSet(b));

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.duelo("misty", b.getID()));
    }

    @Test
    void duelo_esta_en_pueblo_raise_exception() {
        Pueblo p = new Pueblo("pueblo");
        testService.crearEntidad(p);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("ash", p, Sets.newHashSet(b));

        assertThrows(UbicacionIncorrrectaException.class, () -> this.service.duelo("ash", b.getID()));
    }

    @Test
    void duelo_en_dojo_sin_campeon_es_nuevo_campeon() {
        Dojo d = new Dojo("dojo");
        testService.crearEntidad(d);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("brock", d, Sets.newHashSet(b));

        ResultadoCombate r = service.duelo("brock", b.getID());
        assertEquals(b, r.getGanador());
        assertNull(r.getPerdedor());
        assertEquals(0, r.getAtaques().size());
        assertEquals(b, testService.recuperarByName(Dojo.class, "dojo").getCampeon());
    }

    @Test
    void duelo_en_dojo_con_campeon_gana() {
        Bicho campeon = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        testService.crearEntidad(campeon);

        Dojo d = new Dojo("dojo2", campeon);
        testService.crearEntidad(d);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        b.incEnergia(100);
        newEntrenador("julio", d, Sets.newHashSet(b));

        ResultadoCombate r = service.duelo("julio", b.getID());
        assertEquals(b, r.getGanador());
        assertEquals(campeon, r.getPerdedor());
        assertEquals(1, r.getAtaques().size());
        assertEquals(b, r.getAtaques().get(0).getAtacante());
        assertEquals(110, r.getAtaques().get(0).getPuntos());
        assertEquals(1, r.getGanador().getVictorias());
        assertEquals(111, r.getGanador().getEnergia());
        assertEquals(b, testService.recuperarByName(Dojo.class, "dojo2").getCampeon());
    }

    @Test
    void duelo_en_dojo_con_campeon_pierde() {
        Bicho campeon = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        campeon.incEnergia(100);
        testService.crearEntidad(campeon);

        Dojo d = new Dojo("dojo3", campeon);
        testService.crearEntidad(d);

        Bicho b = testService.recuperarByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("agosto", d, Sets.newHashSet(b));

        ResultadoCombate r = service.duelo("agosto", b.getID());
        assertEquals(campeon, r.getGanador());
        assertEquals(b, r.getPerdedor());

        assertEquals(2, r.getAtaques().size());

        assertEquals(b, r.getAtaques().get(0).getAtacante());
        assertEquals(10, r.getAtaques().get(0).getPuntos());

        assertEquals(campeon, r.getAtaques().get(1).getAtacante());
        assertEquals(110, r.getAtaques().get(1).getPuntos());

        assertEquals(1, r.getGanador().getVictorias());
        assertEquals(111, r.getGanador().getEnergia());
        assertEquals(campeon, testService.recuperarByName(Dojo.class, "dojo3").getCampeon());
    }
}
