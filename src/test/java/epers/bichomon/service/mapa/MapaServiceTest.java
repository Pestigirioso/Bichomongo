package epers.bichomon.service.mapa;

import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    void escuela_sin_entrenadores() {
        assertEquals(0, service.cantidadEntrenadores("Escuela de la vida"));
    }

}
