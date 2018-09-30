package epers.bichomon.dao;

import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.Runner;
import epers.bichomon.service.runner.SessionFactoryProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EspecieDAOTest {

    private EspecieDAO dao = new EspecieDAOHib();
    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = new TestService();
        testService.crearEntidad(new Especie(1, "Rojomon", TipoBicho.FUEGO, 180, 75, 100, "/image/rojomon.jpg"));
        testService.crearEntidad(new Especie(2, "Amarillomon", TipoBicho.ELECTRICIDAD, 170, 69, 300, "/image/amarillomon.jpg"));
        testService.crearEntidad(new Especie(3, "Verdemon", TipoBicho.PLANTA, 150, 55, 5000, "/image/verdemon.jpg"));
        testService.crearEntidad(new Especie(4, "Tierramon", TipoBicho.TIERRA, 1050, 99, 5000, "/image/tierramon.jpg"));
        testService.crearEntidad(new Especie(5, "Fantasmon", TipoBicho.AIRE, 1050, 99, 5000, "/image/fantasmon.jpg"));
        testService.crearEntidad(new Especie(6, "Vampiron", TipoBicho.AIRE, 1050, 99, 5000, "/image/vampiromon.jpg"));
        testService.crearEntidad(new Especie(7, "Fortmon", TipoBicho.CHOCOLATE, 1050, 99, 5000, "/image/fortmon.jpg"));
        testService.crearEntidad(new Especie(8, "Dientemon", TipoBicho.AGUA, 1050, 99, 5000, "/image/dientmon.jpg"));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie(0, "prueba", TipoBicho.AGUA, 100, 350, 50, "url");
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

        testService.borrarByName(Especie.class, "prueba");
    }

    @Test
    void recuperar_inexistente_retorna_null() {
        assertNull(Runner.runInSession(() -> dao.recuperar("inexistente")));
    }

    @Test
    void actualizar_todos_los_datos() {
        Especie rojo = Runner.runInSession(() -> dao.recuperar("Rojomon"));
        rojo.setAltura(99);
        rojo.setPeso(190);
        rojo.setTipo(TipoBicho.CHOCOLATE);
        rojo.setEnergiaInicial(0);
        rojo.setUrlFoto("fake.com");
        rojo.setCantidadBichos(46);

        Especie recuperado = Runner.runInSession(() -> {
            dao.actualizar(rojo);
            return dao.recuperar("Rojomon");
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
            dao.actualizar(new Especie(0, "inexistente", TipoBicho.AGUA));
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
        assertThrows(RuntimeException.class, () -> dao.guardar(new Especie(0, "Rojomon", TipoBicho.AGUA)));
    }

}
