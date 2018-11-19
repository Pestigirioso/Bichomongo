package epers.bichomon.service.bicho;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.UbicacionIncorrectaException;
import epers.bichomon.model.ubicacion.duelo.ResultadoCombate;
import epers.bichomon.service.ServiceFactory;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BichoServiceDueloTest extends AbstractServiceTest {

    private BichoService service = ServiceFactory.INSTANCE.getBichoService();

    @BeforeAll
    static void prepare() {
        testService.save(new Especie("Rojomon", TipoBicho.FUEGO, 10));
    }

    @Test
    void duelo_esta_en_guarderia_raise_exception() {
        Guarderia g = new Guarderia("guarderia");
        testService.save(g);

        Bicho b = testService.getByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("misty", g, Sets.newHashSet(b));

        assertThrows(UbicacionIncorrectaException.class, () -> this.service.duelo("misty", b.getID()));
    }

    @Test
    void duelo_esta_en_pueblo_raise_exception() {
        Pueblo p = new Pueblo("pueblo");
        testService.save(p);

        Bicho b = testService.getByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("ash", p, Sets.newHashSet(b));

        assertThrows(UbicacionIncorrectaException.class, () -> this.service.duelo("ash", b.getID()));
    }

    @Test
    void duelo_en_dojo_sin_campeon_es_nuevo_campeon() {
        Dojo d = new Dojo("dojo");
        testService.save(d);

        Bicho b = testService.getByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("brock", d, Sets.newHashSet(b));

        ResultadoCombate r = service.duelo("brock", b.getID());
        assertEquals(b, r.getGanador());
        assertNull(r.getPerdedor());
        assertEquals(0, r.getAtaques().size());
        assertEquals(b, testService.getByName(Dojo.class, "dojo").getCampeon());
        assertEquals(10, testService.getByName(Entrenador.class, "brock").getXP());
    }

    @Test
    void duelo_en_dojo_con_campeon_gana() {
        Dojo d = new Dojo("dojo2");
        testService.save(d);

        Bicho campeon = testService.getByName(Especie.class, "Rojomon").crearBicho();
        newEntrenador("alguien", d, Sets.newHashSet(campeon));
        service.duelo("alguien", campeon.getID());

        Bicho b = testService.getByName(Especie.class, "Rojomon").crearBicho();
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
        assertEquals(b, testService.getByName(Dojo.class, "dojo2").getCampeon());
        assertEquals(10, testService.getByName(Entrenador.class, "julio").getXP());
    }

    @Test
    void duelo_en_dojo_con_campeon_pierde() {
        Bicho campeon = testService.getByName(Especie.class, "Rojomon").crearBicho();
        campeon.incEnergia(100);
        testService.save(campeon);

        Dojo d = new Dojo("dojo3", campeon);
        testService.save(d);

        Bicho b = testService.getByName(Especie.class, "Rojomon").crearBicho();
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
        assertEquals(campeon, testService.getByName(Dojo.class, "dojo3").getCampeon());
    }
}
