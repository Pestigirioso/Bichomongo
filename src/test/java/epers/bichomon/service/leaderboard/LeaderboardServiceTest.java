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

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardServiceTest {

    private LeaderboardService service = ServiceFactory.getLeaderboardService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        testService.crearEntidad(new Especie("poke", TipoBicho.TIERRA));
        testService.crearEntidad(new Especie("especie1", TipoBicho.TIERRA));
        testService.crearEntidad(new Especie("especie2", TipoBicho.TIERRA));

        testService.crearEntidad(Nivel.create());
        testService.crearEntidad(new XPuntos());
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    private void newEntrenador(String nombre, Set<Bicho> bichos) {
        // TODO pasar creacion de entrenador a un service !!
        Entrenador e = new Entrenador(nombre, testService.recuperarBy(Nivel.class, "nro", 1), testService.recuperar(XPuntos.class, 1), bichos);
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
        testService.borrarByName(Entrenador.class, "pepe");
    }

    @Test
    void campeones_dos_entrenador_ordenado_desde_hace_mas_tiempo() {
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        newEntrenador("alberto", Sets.newHashSet(b1));
        testService.crearEntidad(new Dojo("dojo2", b1));

        Bicho b2 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        newEntrenador("juan", Sets.newHashSet(b2));
        testService.crearEntidad(new Dojo("dojo3", new Campeon(b2, LocalDate.of(2018, 1, 1), null), new HashSet<>()));

        List<Entrenador> campeones = service.campeones();
        assertEquals(2, campeones.size());
        assertEquals("juan", campeones.get(0).getNombre());
        assertEquals("alberto", campeones.get(1).getNombre());

        testService.borrarByName(Dojo.class, "dojo2");
        testService.borrarByName(Dojo.class, "dojo3");

        testService.borrarByName(Entrenador.class, "alberto");
        testService.borrarByName(Entrenador.class, "juan");
    }

    /**
     * retorna la especie que tenga mas bichos que haya sido campeones de cualquier dojo.
     * Cada bicho deberá ser contado una sola vez
     * (independientemente de si haya sido coronado campeon mas de una vez o en mas de un Dojo)
     */

    @Test
    void sin_campeones_no_hay_especie_lider() {
        assertThrows(NoResultException.class, () -> service.especieLider());
    }

    @Test
    void un_solo_campeon_es_la_especie_lider() {
        Bicho b = testService.recuperarByName(Especie.class, "especie1").crearBicho();
        testService.crearEntidad(b);
        testService.crearEntidad(new Dojo("dojo4", b));

        assertEquals("especie1", service.especieLider().getNombre());

        testService.borrarByName(Dojo.class, "dojo4");
    }

    @Test
    void especie_con_dos_campeones_es_la_lider_frente_otra_con_el_mismo_campeon_varias_veces() {
        Bicho c1 = testService.recuperarByName(Especie.class, "especie1").crearBicho();
        testService.crearEntidad(c1);
        testService.crearEntidad(new Dojo("dojo5", c1));
        Bicho c2 = testService.recuperarByName(Especie.class, "especie1").crearBicho();
        testService.crearEntidad(c2);
        testService.crearEntidad(new Dojo("dojo6", c2));

        Bicho b1 = testService.recuperarByName(Especie.class, "especie2").crearBicho();
        testService.crearEntidad(b1);
        testService.crearEntidad(new Dojo("dojo7", b1, Sets.newHashSet(new Campeon(b1), new Campeon(b1))));
        testService.crearEntidad(new Dojo("dojo8", b1, Sets.newHashSet(new Campeon(b1), new Campeon(b1))));

        assertEquals("especie1", service.especieLider().getNombre());

        testService.borrarByName(Dojo.class, "dojo5");
        testService.borrarByName(Dojo.class, "dojo6");
        testService.borrarByName(Dojo.class, "dojo7");
        testService.borrarByName(Dojo.class, "dojo8");
    }

    /**
     * retorna los diez primeros entrenadores
     * para los cuales el valor de poder combinado de todos sus bichos sea superior.
     */
    @Test
    void sin_entrenadores_no_hay_lideres() {
        assertEquals(0, service.lideres().size());
    }

    @Test
    void un_unico_entrenador_es_lider() {
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        newEntrenador("pepito", Sets.newHashSet(b1));

        List<Entrenador> lideres = service.lideres();
        assertEquals(1, lideres.size());
        assertEquals("pepito", lideres.get(0).getNombre());

        testService.borrarByName(Entrenador.class, "pepito");
    }

    @Test
    void con_once_entrenadores_no_aparece_el_que_tiene_menos_poder_combinado() {
        Especie e = testService.recuperarByName(Especie.class, "poke");
        IntStream.range(1, 10).boxed().forEach(i -> newEntrenador(i.toString(), Sets.newHashSet(e.crearBicho())));
        testService.crearEntidad(new Especie("debil", TipoBicho.TIERRA, 1));
        newEntrenador("debil", Sets.newHashSet(testService.recuperarByName(Especie.class, "debil").crearBicho()));

        List<Entrenador> lideres = service.lideres();
        assertEquals(10, lideres.size());
        assertFalse(lideres.contains("debil"));

        IntStream.range(1, 10).boxed().forEach(i -> testService.borrarByName(Entrenador.class, i.toString()));
        testService.borrarByName(Entrenador.class, "debil");
    }
}
