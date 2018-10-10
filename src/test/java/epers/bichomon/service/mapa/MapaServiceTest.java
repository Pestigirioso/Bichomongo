package epers.bichomon.service.mapa;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.duelo.Campeon;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapaServiceTest {

    private MapaService service = ServiceFactory.getMapService();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();

        testService.crearEntidad(new Entrenador("Alex"));
        testService.crearEntidad(new Entrenador("Magali"));
        testService.crearEntidad(new Entrenador("Paco"));

        testService.crearEntidad(new Pueblo("Pueblo Paleta"));
        testService.crearEntidad(new Guarderia("Guarderia Bicho Feliz"));
        testService.crearEntidad(new Dojo("Escuela de la vida"));

        testService.crearEntidad(new Especie("poke", TipoBicho.TIERRA));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void le_digo_a_un_entrenador_que_se_mueva_y_lo_hace() {
        service.mover("Alex", "Pueblo Paleta");
        assertEquals("Pueblo Paleta",
                testService.recuperarByName(Entrenador.class, "Alex").getUbicacion().getNombre());
    }

    @Test
    void le_digo_a_un_entrenador_que_se_mueva_dos_veces_y_lo_hace() {
        service.mover("Alex", "Pueblo Paleta");
        service.mover("Alex", "Guarderia Bicho Feliz");
        assertEquals("Guarderia Bicho Feliz",
                testService.recuperarByName(Entrenador.class, "Alex").getUbicacion().getNombre());
    }

    @Test
    void muevo_3_entrenadores_al_pueblo_y_hay_3_entrenadores() {
        service.mover("Alex", "Pueblo Paleta");
        service.mover("Magali", "Pueblo Paleta");
        service.mover("Paco", "Pueblo Paleta");
        assertEquals(3, service.cantidadEntrenadores("Pueblo Paleta"));
        assertEquals(0, service.cantidadEntrenadores("Guarderia Bicho Feliz"));
        assertEquals(0, service.cantidadEntrenadores("Escuela de la vida"));
    }

    @Test
    void escuela_sin_entrenadores() {
        assertEquals(0, service.cantidadEntrenadores("Escuela de la vida"));
    }

    @Test
    void dojo_con_campeon() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b);
        testService.crearEntidad(new Dojo("DojoCampeon", b));
        assertEquals(b, service.campeon("DojoCampeon"));
    }

    @Test
    void dojo_sin_campeon() {
        testService.crearEntidad(new Dojo("DojoSinCampeon"));
        assertNull(service.campeon("DojoSinCampeon"));
    }

    @Test
    void campeon_historico_de_dojo_con_un_solo_campeon() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b);
        testService.crearEntidad(new Dojo("DojoCampeonHistorico1", b));
        assertEquals(b, service.campeonHistorico("DojoCampeonHistorico1"));
    }

    @Test
    void campeon_historico_de_dojo_con_dos_campeones() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b);
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b1);
        testService.crearEntidad(new Dojo("DojoCampeonHistorico2", b,
                Sets.newHashSet(new Campeon(b1,
                        LocalDate.of(2018, 10, 1),
                        LocalDate.of(2018, 10, 10))
                )));
        assertEquals(b1, service.campeonHistorico("DojoCampeonHistorico2"));
    }

    @Test
    void campeon_historico_de_dojo_con_tres_campeones() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b);
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b1);
        Bicho b2 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b2);
        testService.crearEntidad(new Dojo("DojoCampeonHistorico3", b,
                Sets.newHashSet(
                        new Campeon(b1,
                                LocalDate.of(2018, 10, 1),
                                LocalDate.of(2018, 10, 10)),
                        new Campeon(b2,
                                LocalDate.of(2017, 10, 1),
                                LocalDate.of(2018, 10, 1))
                )));
        assertEquals(b2, service.campeonHistorico("DojoCampeonHistorico3"));
    }

    @Test
    void campeon_historico_de_dojo_con_cuatro_campeones() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b);
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b1);
        Bicho b2 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b2);
        Bicho b3 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b3);
        testService.crearEntidad(new Dojo("DojoCampeonHistorico4", b,
                Sets.newHashSet(
                        new Campeon(b1,
                                LocalDate.of(2018, 10, 1),
                                LocalDate.of(2018, 10, 10)),
                        new Campeon(b2,
                                LocalDate.of(2002, 10, 1),
                                LocalDate.of(2018, 10, 1)),
                        new Campeon(b3,
                                LocalDate.of(2001, 10, 1),
                                LocalDate.of(2002, 10, 1))
                )));
        assertEquals(b2, service.campeonHistorico("DojoCampeonHistorico4"));
    }

    @Test
    void campeon_historico_de_dojo_con_tres_campeones_es_el_actual() {
        Bicho b = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b);
        Bicho b1 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b1);
        Bicho b2 = testService.recuperarByName(Especie.class, "poke").crearBicho();
        testService.crearEntidad(b2);
        testService.crearEntidad(new Dojo("DojoCampeonHistorico5",
                new Campeon(b, LocalDate.of(2010, 10, 1), null),
                Sets.newHashSet(
                        new Campeon(b1,
                                LocalDate.of(2009, 10, 1),
                                LocalDate.of(2010, 10, 1)),
                        new Campeon(b2,
                                LocalDate.of(2007, 10, 1),
                                LocalDate.of(2009, 10, 1))
                )));
        assertEquals(b, service.campeonHistorico("DojoCampeonHistorico5"));
    }
}
