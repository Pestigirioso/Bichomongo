package epers.bichomon.service.leaderboard;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.entrenador.XPuntos;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.duelo.Campeon;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaderboardServiceTest {

    private LeaderboardService service = ServiceFactory.getLeaderboardService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        testService.crearEntidad(new Especie("poke", TipoBicho.TIERRA));

        testService.crearEntidad(Nivel.create());
        testService.crearEntidad(new XPuntos());
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    private void newEntrenador(String nombre, Set<Bicho> bichos) {
        // TODO pasar creacion de entrenador a un service !!
        Entrenador e = new Entrenador(nombre, testService.recuperarBy(Nivel.class, "nro", 1),
                testService.recuperar(XPuntos.class, 1), bichos);
        this.testService.crearEntidad(e);
    }

    /**
     * retorna aquellos entrenadores que posean un bicho que actualmente sea campeon de un Dojo,
     * retornando primero aquellos que ocupen el puesto de campeon desde hace mas tiempo.
     */
    @Test
    void sin_entrenadores_no_hay_campeones() {
        assertEquals(0, service.campeones().size());
    }

    @Test
    void con_un_solo_entrenador_es_el_unico_campeon() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        newEntrenador("pepe", Sets.newHashSet(b));

        Campeon c = new Campeon(b, LocalDate.of(2018, 1, 1), null);
        testService.crearEntidad(new Dojo("dojo1", c, new HashSet<>()));

        List<Entrenador> campeones = service.campeones();
        assertEquals(1, campeones.size());
        assertEquals("pepe", campeones.get(0).getNombre());

        testService.borrarByName(Dojo.class, "dojo1");
    }

    @Test
    void campeones_dos_entrenador_ordenado_desde_hace_mas_tiempo() {
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        newEntrenador("alberto", Sets.newHashSet(b1));
        testService.crearEntidad(new Dojo("dojo2", b1));

        Bicho b2 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        newEntrenador("juan", Sets.newHashSet(b2));
        testService.crearEntidad(new Dojo("dojo3",
                new Campeon(b2, LocalDate.of(2018, 1, 1), null), new HashSet<>()));

        List<Entrenador> campeones = service.campeones();
        assertEquals(2, campeones.size());
        assertEquals("juan", campeones.get(0).getNombre());
        assertEquals("alberto", campeones.get(1).getNombre());

        testService.borrarByName(Dojo.class, "dojo2");
        testService.borrarByName(Dojo.class, "dojo3");
    }

}
