package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.AbstractMySQLTest;
import ar.edu.unq.epers.bichomon.backend.dao.mysql.EspecieDAOMySQL;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EspecieServiceMySQLTest extends AbstractMySQLTest {

    private EspecieService service = new EspecieServiceImpl(new EspecieDAOMySQL());

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(EspecieNoExistente.class, () -> service.getEspecie("inexistente"));
    }

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie(0, "prueba", TipoBicho.AGUA);
        especie.setAltura(100);
        especie.setPeso(350);
        especie.setEnergiaIncial(50);
        especie.setUrlFoto("url");

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
    void recuperar_todos_tiene_8_especie() {
        assertEquals(8, service.getAllEspecies().size());
    }

    @Test
    void crear_bicho_aumenta_en_1_la_cantidad_de_bichos() {
        int cantidadDeBichos = service.getEspecie("Rojomon").getCantidadBichos();
        service.crearBicho("Rojomon", "bicho1");
        assertEquals(cantidadDeBichos+1, service.getEspecie("Rojomon").getCantidadBichos());
    }
}
