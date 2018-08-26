package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.AbstractMySQLTest;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecieDAOMySQLTest extends AbstractMySQLTest {

    private EspecieDAO dao = new EspecieDAOMySQL();

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie(0, "prueba", TipoBicho.AGUA);
        especie.setAltura(100);
        especie.setPeso(350);
        especie.setEnergiaIncial(50);
        especie.setUrlFoto("url");

        dao.guardar(especie);
        Especie restored = dao.recuperar("prueba");

        assertEquals(especie.getNombre(), restored.getNombre());
        assertEquals(especie.getTipo(), restored.getTipo());
        assertEquals(especie.getAltura(), restored.getAltura());
        assertEquals(especie.getPeso(), restored.getPeso());
        assertEquals(especie.getEnergiaInicial(), restored.getEnergiaInicial());
        assertEquals(especie.getUrlFoto(), restored.getUrlFoto());
        assertEquals(0, restored.getCantidadBichos());
    }

    @Test
    void recuperar_inexistente_retorna_null() {
        assertNull(dao.recuperar("inexistente"));
    }

    @Test
    void actualizar_cantidad_de_bichos() {
        Especie rojo = dao.recuperar("Rojomon");
        rojo.setCantidadBichos(46);
        dao.actualizar(rojo);
        assertEquals(46, dao.recuperar("Rojomon").getCantidadBichos());
    }

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(RuntimeException.class,
                () -> dao.actualizar(new Especie(0, "inexistente", TipoBicho.AGUA)));
    }

    @Test
    void recuperar_todos_no_tiene_especie_inexistente() {
        assertTrue(dao.recuperarTodos().stream().noneMatch(e -> e.getNombre().equals("inexistente")));
    }

    @Test
    void recuperar_todos_tiene_especie_rojomon() {
        assertTrue(dao.recuperarTodos().stream().anyMatch(e -> e.getNombre().equals("Rojomon")));
    }

}
