package epers.bichomon.service.mapa;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.duelo.Campeon;
import epers.bichomon.service.ServiceFactory;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapaServiceTest extends AbstractServiceTest {

    private MapaService service = ServiceFactory.getMapService();

    @BeforeAll
    static void prepare() {
        testService.save(new Pueblo("Pueblo Paleta"));
        testService.save(new Guarderia("Guarderia Bicho Feliz"));
        testService.save(new Dojo("Escuela de la vida"));

        testService.save(new Especie("poke", TipoBicho.TIERRA));
    }

    @Test
    void escuela_sin_entrenadores() {
        assertEquals(0, service.cantidadEntrenadores("Escuela de la vida"));
    }

    @Test
    void dojo_con_campeon() {
        Bicho b = testService.getByName(Especie.class, "poke").crearBicho();
        testService.save(b);
        testService.save(new Dojo("DojoCampeon", b));
        assertEquals(b, service.campeon("DojoCampeon"));
    }

    @Test
    void dojo_sin_campeon() {
        testService.save(new Dojo("DojoSinCampeon"));
        assertNull(service.campeon("DojoSinCampeon"));
    }

    @Test
    void campeon_historico_de_dojo_con_un_solo_campeon() {
        Bicho b = testService.getByName(Especie.class, "poke").crearBicho();
        testService.save(b);
        testService.save(new Dojo("DojoCampeonHistorico1", b));
        assertEquals(b, service.campeonHistorico("DojoCampeonHistorico1"));
    }

    @Test
    void campeon_historico_de_dojo_con_dos_campeones() {
        Especie poke = testService.getByName(Especie.class, "poke");
        Bicho b = poke.crearBicho();
        testService.save(b);
        Bicho b1 = poke.crearBicho();
        testService.save(b1);
        testService.save(new Dojo("DojoCampeonHistorico2", b, Sets.newHashSet(new Campeon(b1, LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 10)))));
        assertEquals(b1, service.campeonHistorico("DojoCampeonHistorico2"));
    }

    @Test
    void campeon_historico_de_dojo_con_tres_campeones() {
        Especie poke = testService.getByName(Especie.class, "poke");
        Bicho b = poke.crearBicho();
        testService.save(b);
        Bicho b1 = poke.crearBicho();
        testService.save(b1);
        Bicho b2 = poke.crearBicho();
        testService.save(b2);
        testService.save(new Dojo("DojoCampeonHistorico3", b, Sets.newHashSet(new Campeon(b1, LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 10)), new Campeon(b2, LocalDate.of(2017, 10, 1), LocalDate.of(2018, 10, 1)))));
        assertEquals(b2, service.campeonHistorico("DojoCampeonHistorico3"));
    }

    @Test
    void campeon_historico_de_dojo_con_cuatro_campeones() {
        Especie poke = testService.getByName(Especie.class, "poke");
        Bicho b = poke.crearBicho();
        testService.save(b);
        Bicho b1 = poke.crearBicho();
        testService.save(b1);
        Bicho b2 = poke.crearBicho();
        testService.save(b2);
        Bicho b3 = poke.crearBicho();
        testService.save(b3);
        testService.save(new Dojo("DojoCampeonHistorico4", b, Sets.newHashSet(new Campeon(b1, LocalDate.of(2018, 10, 1), LocalDate.of(2018, 10, 10)), new Campeon(b2, LocalDate.of(2002, 10, 1), LocalDate.of(2018, 10, 1)), new Campeon(b3, LocalDate.of(2001, 10, 1), LocalDate.of(2002, 10, 1)))));
        assertEquals(b2, service.campeonHistorico("DojoCampeonHistorico4"));
    }

    @Test
    void campeon_historico_de_dojo_con_tres_campeones_es_el_actual() {
        Especie poke = testService.getByName(Especie.class, "poke");
        Bicho b = poke.crearBicho();
        testService.save(b);
        Bicho b1 = poke.crearBicho();
        testService.save(b1);
        Bicho b2 = poke.crearBicho();
        testService.save(b2);
        testService.save(new Dojo("DojoCampeonHistorico5", new Campeon(b, LocalDate.of(2010, 10, 1), null), Sets.newHashSet(new Campeon(b1, LocalDate.of(2009, 10, 1), LocalDate.of(2010, 10, 1)), new Campeon(b2, LocalDate.of(2007, 10, 1), LocalDate.of(2009, 10, 1)))));
        assertEquals(b, service.campeonHistorico("DojoCampeonHistorico5"));
    }
}
