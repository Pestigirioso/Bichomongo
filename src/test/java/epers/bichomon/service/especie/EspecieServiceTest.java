package epers.bichomon.service.especie;

import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecieServiceTest {

    private EspecieService service;

    @BeforeEach
    void prepare() {
        service = new ServiceFactory().getEspecieService();
        TestService testService = new TestService();

        testService.crearEntidad(new Especie(1, "Rojomon", TipoBicho.FUEGO, 180, 75, 100, "/rojomon.jpg"));
        testService.crearEntidad(new Especie(2, "Amarillomon", TipoBicho.AIRE, 170, 69, 300, "/amarillomon.jpg"));
        testService.crearEntidad(new Especie(3, "Verdemon", TipoBicho.PLANTA, 150, 55, 500, "/verdemon.jpg"));
    }

    @AfterEach
    void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(EspecieNoExistente.class, () -> service.getEspecie("inexistente"));
    }

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie(0, "prueba", TipoBicho.AGUA, 100, 350, 50, "url");

        service.crearEspecie(especie);
        Especie restored = service.getEspecie("prueba");

        assertEquals(especie.getNombre(), restored.getNombre());
        assertEquals(especie.getTipo(), restored.getTipo());
        assertEquals(especie.getAltura(), restored.getAltura());
        assertEquals(especie.getPeso(), restored.getPeso());
        assertEquals(especie.getEnergiaInicial(), restored.getEnergiaInicial());
        assertEquals(especie.getUrlFoto(), restored.getUrlFoto());
        assertEquals(0, restored.getCantidadBichos());
    }

    @Test
    void recuperar_todos_no_tiene_especie_inexistente() {
        assertTrue(service.getAllEspecies().stream().noneMatch(e -> e.getNombre().equals("inexistente")));
    }

    @Test
    void recuperar_todos_tiene_especie_rojomon() {
        assertTrue(service.getAllEspecies().stream().anyMatch(e -> e.getNombre().equals("Rojomon")));
    }

    @Test
    void recuperar_todos_tiene_3_especie() {
        assertEquals(3, service.getAllEspecies().size());
    }

    @Test
    void crear_bicho_aumenta_en_1_la_cantidad_de_bichos() {
        int cantidadDeBichos = service.getEspecie("Rojomon").getCantidadBichos();
        service.crearBicho("Rojomon");
        assertEquals(cantidadDeBichos + 1, service.getEspecie("Rojomon").getCantidadBichos());
    }
}
