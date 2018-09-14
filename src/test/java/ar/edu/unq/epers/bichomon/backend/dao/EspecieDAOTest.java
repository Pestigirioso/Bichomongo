package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.AbstractTest;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EspecieDAOTest extends AbstractTest {

    private EspecieDAO dao = new EspecieDAOHibernate();

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie(0, "prueba", TipoBicho.AGUA);
        especie.setAltura(100);
        especie.setPeso(350);
        especie.setEnergiaIncial(50);
        especie.setUrlFoto("url");

        Especie restored = Runner.runInSession(() -> {
                    dao.guardar(especie);
                    return dao.recuperar("prueba");
                });

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
        assertNull(Runner.runInSession(() -> dao.recuperar("inexistente")));
    }

    @Test
    void actualizar_cantidad_de_bichos() {
        Especie rojo = Runner.runInSession(() -> dao.recuperar("Rojomon"));
        rojo.setCantidadBichos(46);
        Especie recuperado = Runner.runInSession(() -> {
            dao.actualizar(rojo);
            return dao.recuperar("Rojomon");
        });
        assertEquals(46, recuperado.getCantidadBichos());
    }

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(RuntimeException.class, () -> {
            Runner.runInSession(() -> {
                dao.actualizar(new Especie(0, "inexistente", TipoBicho.AGUA));
                return null;
            });
        });
    }

    @Test
    void recuperar_todos_no_tiene_especie_inexistente() {
        List<Especie> todos = Runner.runInSession(() -> dao.recuperarTodos());
        assertTrue(todos.stream().noneMatch(e -> e.getNombre().equals("inexistente")));
    }

    @Test
    void recuperar_todos_ordenado_por_nombre() {
        List<Especie> lista = Runner.runInSession(() -> dao.recuperarTodos());
        assertEquals("Amarillomon", lista.get(0).getNombre());
        assertEquals("Dientemon", lista.get(1).getNombre());
        assertEquals("Verdemon", lista.get(7).getNombre());
    }

    @Test
    void recuperar_todos_tiene_especie_rojomon() {
        List<Especie> especies = Runner.runInSession(() -> dao.recuperarTodos());
        assertTrue(especies.stream().anyMatch(e -> e.getNombre().equals("Rojomon")));
    }

    @Test
    void recuperar_todos_tiene_8_especie() {
        List<Especie> todos = Runner.runInSession(() -> dao.recuperarTodos());
        assertEquals(8, todos.size());
    }

    @Test
    void guardar_dos_veces_el_mismo_nombre_de_especie() {
        assertThrows(RuntimeException.class, () -> {
            dao.guardar(new Especie(0, "Rojomon", TipoBicho.AGUA));
        });
    }

}
