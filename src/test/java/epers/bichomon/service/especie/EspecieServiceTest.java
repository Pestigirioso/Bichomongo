package epers.bichomon.service.especie;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.TestService;
import epers.bichomon.service.runner.SessionFactoryProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EspecieServiceTest {

    private EspecieService service;

    private TestService testService = new TestService();

    @BeforeEach
    void prepare() {
        service = ServiceFactory.getEspecieService();

        testService.crearEntidad(new Especie(1, "Rojomon", TipoBicho.FUEGO, 180, 75, 100, "/rojomon.jpg"));
        testService.crearEntidad(new Especie(2, "Amarillomon", TipoBicho.AIRE, 170, 69, 300, "/amarillomon.jpg"));
        testService.crearEntidad(new Especie(3, "Verdemon", TipoBicho.PLANTA, 150, 55, 500, "/verdemon.jpg"));
        testService.crearEntidad(new Especie(4, "Violetamon", TipoBicho.TIERRA, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(5, "Azulmon", TipoBicho.ELECTRICIDAD, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(6, "Naranjamon", TipoBicho.CHOCOLATE, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(7, "Marronmon", TipoBicho.AGUA, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(8, "Lilamon", TipoBicho.AIRE, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(9, "Celestemon", TipoBicho.AGUA, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(10, "Ocremon", TipoBicho.FUEGO, 150, 55, 500, ""));
        testService.crearEntidad(new Especie(11, "Turquesamon", TipoBicho.PLANTA, 150, 55, 500, ""));

    }

    private void crearBicho(Integer id, Especie especie, Entrenador entrenador) {
        Bicho b = new Bicho(id, especie, 10);
        b.capturadoPor(entrenador);
        testService.crearEntidad(b);
    }

    private void crearSeisBichos(Entrenador entrenador) {
        Especie rojo = testService.recuperarByName(Especie.class, "Rojomon");
        crearBicho(1, rojo, entrenador);
        Especie amarillo = testService.recuperarByName(Especie.class, "Amarillomon");
        crearBicho(2, amarillo, entrenador);
        Especie verde = testService.recuperarByName(Especie.class, "Verdemon");
        crearBicho(3, verde, entrenador);
        Especie violeta = testService.recuperarByName(Especie.class, "Violetamon");
        crearBicho(4, violeta, entrenador);
        Especie azul = testService.recuperarByName(Especie.class, "Azulmon");
        crearBicho(5, azul, entrenador);
        Especie naranja = testService.recuperarByName(Especie.class, "Naranjamon");
        crearBicho(6, naranja, entrenador);
    }

    //TODO terminar esto

    private void crearOnceBichos(Entrenador entrenador, List<Especie> especies) {
        crearSeisBichos(entrenador);
        Especie marron = testService.recuperarByName(Especie.class, "Marron");
        crearBicho(7, marron, entrenador);
        Especie lila = testService.recuperarByName(Especie.class, "Lilamon");
        crearBicho(8, lila, entrenador);
        Especie celeste = testService.recuperarByName(Especie.class, "Celestemon");
        crearBicho(9, celeste, entrenador);
        Especie ocre = testService.recuperarByName(Especie.class, "Ocremon");
        crearBicho(10, ocre, entrenador);
        Especie turquesa = testService.recuperarByName(Especie.class, "Turquesamon");
        crearBicho(11, turquesa, entrenador);
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
    void recuperar_todos_tiene_11_especie() {

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
    }

    //Test para cuando hay menos de diez especies (deberían estar todas ellas)

    @Test
    void con_solo_seis_especies_cuyos_bichos_tengan_entrenador_hay_seis_especies_populares() {
//        crearBicho()
        assertEquals(6, service.populares().size());
    }

    //Tests para cuando hay más de 10 especies, y debo elegir las 10 que son populares
    @Test
    void al_recuperan_las_populares_no_esta_la_impopular() {
        assertFalse(service.populares().contains("El que no tiene que estar"));
    }

    @Test
    void se_recuperan_las_populares_y_hay_10() {
        assertEquals(10, service.populares().size());
    }

    // Tests sobre especies impopulares
    // TODO Pulirlas

    @Test
    void sin_especies_creadas_no_hay_especies_impopulares() {
        assertEquals(0, service.impopulares().size());
    }

    //Test para cuando hay menos de diez especies (deberían estar todas ellas)

    @Test
    void con_solo_seis_especies_hay_seis_especies_impopulares() {
        assertEquals(6, service.populares().size());
    }

    //Tests para cuando hay más de 10 especies, y debo elegir las 10 que son impopulares

    @Test
    void al_recuperan_las_impopulares_no_esta_la_popular() {
        assertFalse(service.impopulares().contains("el q no debería estar"));
    }

    @Test
    void se_recuperan_las_impopulares_y_hay_10() {
        assertEquals(10, service.impopulares().size());
    }

}
