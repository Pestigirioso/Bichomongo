package epers.bichomon.service.mapa;

import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapaServiceTest {

    private MapaService service;
    private TestService testService;

    @BeforeEach
    void prepare() {
        this.service = new ServiceFactory().getMapService();
        this.testService = new TestService();

        this.testService.crearEntidad(new Entrenador("Alex"));
        this.testService.crearEntidad(new Entrenador("Magali"));
        this.testService.crearEntidad(new Entrenador("Paco"));

        Ubicacion pueblo = new Pueblo("Pueblo Paleta");
        this.testService.crearEntidad(pueblo);

        Ubicacion guarderia = new Guarderia("Guarderia Bicho Feliz");
        this.testService.crearEntidad(guarderia);

        Ubicacion dojo = new Dojo("Escuela de la vida");
        this.testService.crearEntidad(dojo);
    }

    @AfterEach
    void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void le_digo_a_un_entrenador_que_se_mueva_y_lo_hace() {
        service.mover("Alex", "Pueblo Paleta");
        assertEquals("Pueblo Paleta",
                this.testService.recuperarByName(Entrenador.class, "Alex").getUbicacion().getNombre());
    }

    @Test
    void le_digo_a_un_entrenador_que_se_mueva_dos_veces_y_lo_hace() {
        service.mover("Alex", "Pueblo Paleta");
        service.mover("Alex", "Guarderia Bicho Feliz");
        assertEquals("Guarderia Bicho Feliz",
                this.testService.recuperarByName(Entrenador.class, "Alex").getUbicacion().getNombre());
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
