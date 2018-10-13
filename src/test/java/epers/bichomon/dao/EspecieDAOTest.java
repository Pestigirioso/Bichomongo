package epers.bichomon.dao;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.service.runner.Runner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EspecieDAOTest extends AbstractServiceTest {

    private EspecieDAO dao = new EspecieDAOHib();

    @BeforeAll
    static void prepare() {
        testService.save(new Especie("Rojomon", TipoBicho.FUEGO, 180, 75, 100, "/image/rojomon.jpg"));
        testService.save(new Especie("Amarillomon", TipoBicho.ELECTRICIDAD, 170, 69, 300, "/image/amarillomon.jpg"));
        testService.save(new Especie("Verdemon", TipoBicho.PLANTA, 150, 55, 5000, "/image/verdemon.jpg"));
        testService.save(new Especie("Tierramon", TipoBicho.TIERRA, 1050, 99, 5000, "/image/tierramon.jpg"));
        testService.save(new Especie("Fantasmon", TipoBicho.AIRE, 1050, 99, 5000, "/image/fantasmon.jpg"));
        testService.save(new Especie("Vampiron", TipoBicho.AIRE, 1050, 99, 5000, "/image/vampiromon.jpg"));
        testService.save(new Especie("Fortmon", TipoBicho.CHOCOLATE, 1050, 99, 5000, "/image/fortmon.jpg"));
        testService.save(new Especie("Dientemon", TipoBicho.AGUA, 1050, 99, 5000, "/image/dientmon.jpg"));
    }

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie("prueba", TipoBicho.AGUA, 100, 350, 50, "url");
        Especie restored = Runner.runInSession(() -> {
            dao.save(especie);
            return dao.get("prueba");
        });
        assertEquals(especie.getNombre(), restored.getNombre());
        assertEquals(especie.getTipo(), restored.getTipo());
        assertEquals(especie.getAltura(), restored.getAltura());
        assertEquals(especie.getPeso(), restored.getPeso());
        assertEquals(especie.getEnergiaInicial(), restored.getEnergiaInicial());
        assertEquals(especie.getUrlFoto(), restored.getUrlFoto());
        assertEquals(0, restored.getCantidadBichos());

        testService.deleteByName(Especie.class, "prueba");
    }

    @Test
    void recuperar_inexistente_retorna_null() {
        assertNull(Runner.runInSession(() -> dao.get("inexistente")));
    }

    @Test
    void actualizar_todos_los_datos() {
        Especie rojo = Runner.runInSession(() -> dao.get("Rojomon"));
        rojo.setAltura(99);
        rojo.setPeso(190);
        rojo.setTipo(TipoBicho.CHOCOLATE);
        rojo.setEnergiaInicial(0);
        rojo.setUrlFoto("fake.com");
        rojo.setCantidadBichos(46);

        Especie recuperado = Runner.runInSession(() -> {
            dao.upd(rojo);
            return dao.get("Rojomon");
        });

        assertEquals(99, rojo.getAltura());
        assertEquals(190, rojo.getPeso());
        assertEquals(TipoBicho.CHOCOLATE, rojo.getTipo());
        assertEquals(0, rojo.getEnergiaInicial());
        assertEquals("fake.com", rojo.getUrlFoto());
        assertEquals(46, recuperado.getCantidadBichos());
    }

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(RuntimeException.class, () -> Runner.runInSession(() -> {
            dao.upd(new Especie(0, "inexistente", TipoBicho.AGUA));
            return null;
        }));
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
        assertThrows(RuntimeException.class, () -> dao.save(new Especie(0, "Rojomon", TipoBicho.AGUA)));
    }

}
