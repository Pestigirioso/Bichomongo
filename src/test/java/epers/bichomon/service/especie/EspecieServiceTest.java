package epers.bichomon.service.especie;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EspecieServiceTest {

    private EspecieService service = ServiceFactory.getEspecieService();

    private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        TestService testService = ServiceFactory.getTestService();
        testService.crearEntidad(new Especie("Rojomon", TipoBicho.FUEGO, 180, 75, 100, "/rojomon.jpg"));
        testService.crearEntidad(new Especie("Amarillomon", TipoBicho.AIRE, 170, 69, 300, "/amarillomon.jpg"));
        testService.crearEntidad(new Especie("Verdemon", TipoBicho.PLANTA, 150, 55, 500, "/verdemon.jpg"));
        testService.crearEntidad(new Especie("Violetamon", TipoBicho.TIERRA, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Azulmon", TipoBicho.ELECTRICIDAD, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Naranjamon", TipoBicho.CHOCOLATE, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Marronmon", TipoBicho.AGUA, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Lilamon", TipoBicho.AIRE, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Celestemon", TipoBicho.AGUA, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Ocremon", TipoBicho.FUEGO, 150, 55, 500, ""));
        testService.crearEntidad(new Especie("Turquesamon", TipoBicho.PLANTA, 150, 55, 500, ""));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    private void borrarBichos(List<Integer> bichos){
        bichos.forEach(b -> testService.borrar(Bicho.class, b));
    }

    private void borrarEspecies(List<String> especies){
        especies.forEach(especie -> testService.borrarByName(Especie.class, especie));
    }

    private List<Integer> crearBichos(ArrayList<String> especies, Entrenador entrenador) {
        List<Integer> res = new ArrayList<>();
        especies.forEach(especie ->{
            Especie e = testService.recuperarByName(Especie.class, especie);
            Bicho b = new Bicho(e);
            if (entrenador != null) {
                b.capturadoPor(entrenador);
            }
            testService.crearEntidad(b);
            res.add(b.getID());
        });
        return res;
    }

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(EspecieNoExistente.class, () -> service.getEspecie("inexistente"));
    }

    @Test
    void restaurar_guardado_tiene_mismos_datos() {
        Especie especie = new Especie("prueba", TipoBicho.AGUA, 100, 350, 50, "url");

        service.crearEspecie(especie);
        Especie restored = service.getEspecie("prueba");

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
    void recuperar_todos_no_tiene_especie_inexistente() {
        assertTrue(service.getAllEspecies().stream().noneMatch(e -> e.getNombre().equals("inexistente")));
    }

    @Test
    void recuperar_todos_tiene_especie_rojomon() {
        assertTrue(service.getAllEspecies().stream().anyMatch(e -> e.getNombre().equals("Rojomon")));
    }

    @Test
    void recuperar_todos_tiene_11_especies() {
        assertEquals(11, service.getAllEspecies().size());
    }

    @Test
    void crear_bicho_aumenta_en_1_la_cantidad_de_bichos() {
        int cantidadDeBichos = service.getEspecie("Rojomon").getCantidadBichos();
        service.crearBicho("Rojomon");
        assertEquals(cantidadDeBichos + 1, service.getEspecie("Rojomon").getCantidadBichos());
    }

    // Tests sobre especies populares

    @Test
    void si_no_hay_entrenadores_con_algun_bicho_de_especie_creada_no_hay_populares() {
        testService.crearEntidad(new Entrenador("unEntrenador"));
        assertEquals(0, service.populares().size());
        testService.borrarByName(Entrenador.class, "unEntrenador");
    }

    //Test para cuando hay menos de diez especies (deberían estar todas ellas)

    @Test
    void con_solo_seis_especies_cuyos_bichos_tengan_entrenador_hay_seis_especies_populares() {
        ArrayList<String> especies = new ArrayList<>(Arrays.asList("Rojomon", "Amarillomon", "Verdemon", "Violetamon", "Azulmon", "Lilamon"));
        Entrenador e = new Entrenador("unEntrenador");
        testService.crearEntidad(e);
        List<Integer> bichos = crearBichos(especies, e);
        assertEquals(6, service.populares().size());
        //TearDown
        borrarBichos(bichos);
        testService.borrarByName(Entrenador.class,"unEntrenador");
    }

    //Tests para cuando hay más de 10 especies, y debo elegir las 10 que son populares
    @Test
    void al_recuperar_las_populares_no_esta_la_impopular() {
        ArrayList<String> especies = new ArrayList<>(Arrays.asList("Rojomon", "Amarillomon", "Verdemon", "Violetamon", "Azulmon", "Lilamon","Celestemon","Marronmon","Naranjamon","Ocremon"));
        assertFalse(service.populares().contains(testService.recuperarByName(Especie.class, "Turquesamon")));
    }

    @Test
    void se_recuperan_las_populares_y_hay_10() {
        ArrayList<String> especies = new ArrayList<>(Arrays.asList("Rojomon", "Amarillomon", "Verdemon", "Violetamon", "Azulmon", "Lilamon","Celestemon","Marronmon","Naranjamon","Ocremon"));
        Entrenador e = new Entrenador("unEntrenador3");
        testService.crearEntidad(e);
        List<Integer> bichos = crearBichos(especies, e);
        assertEquals(10, service.populares().size());
        //TearDown
        borrarBichos(bichos);
    }

    // Tests sobre especies impopulares
    // TODO Pulirlas

    @Test
    void sin_bichos_creados_no_hay_especies_impopulares() {
        assertEquals(0, service.impopulares().size());
    }

    //Test para cuando hay menos de diez especies (deberían estar todas ellas)

    @Test
    void con_solo_seis_especies_hay_seis_especies_impopulares() {
        ArrayList<String> especies = new ArrayList<>(Arrays.asList("Rojomon", "Amarillomon", "Verdemon", "Violetamon", "Azulmon", "Lilamon"));
        List<Integer> bichos =crearBichos(especies,null);
        assertEquals(6, service.impopulares().size());
        //TearDown
        borrarBichos(bichos);
    }

    //Tests para cuando hay más de 10 especies, y debo elegir las 10 que son impopulares

    @Test
    void al_recuperar_las_impopulares_no_esta_la_popular() {
        ArrayList<String> especiePopu = new ArrayList<>(Arrays.asList("Rojomon"));
        Entrenador e = new Entrenador("unEntrenador");
        testService.crearEntidad(e);
        List<Integer> bichos = crearBichos(especiePopu,e);
        ArrayList<String> especiesImpopulares = new ArrayList<>(Arrays.asList("Turquesamon", "Amarillomon", "Verdemon", "Violetamon", "Azulmon", "Lilamon","Celestemon","Marronmon","Naranjamon","Ocremon"));
        bichos.addAll(crearBichos(especiesImpopulares,null));
        assertFalse(service.impopulares().contains(testService.recuperarByName(Especie.class, "Rojomon")));
        //TearDown
        borrarBichos(bichos);
        testService.borrarByName(Entrenador.class, "unEntrenador");
    }

    @Test
    void se_recuperan_las_impopulares_y_hay_10() {
        ArrayList<String> especiesImpopulares = new ArrayList<>(Arrays.asList("Turquesamon", "Amarillomon", "Verdemon", "Violetamon", "Azulmon", "Lilamon","Celestemon","Marronmon","Naranjamon","Ocremon"));
        List<Integer> bichos =  crearBichos(especiesImpopulares,null);
        assertEquals(10, service.impopulares().size());
        //TearDown
        borrarBichos(bichos);
    }

}
