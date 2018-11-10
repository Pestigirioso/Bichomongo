package epers.bichomon.service.mapa;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapaServiceCaminoTest extends AbstractServiceTest {

    private MapaService service = ServiceFactory.INSTANCE.getMapService();

    @BeforeAll
    static void prepare() {
        MapaService service = ServiceFactory.INSTANCE.getMapService();
        service.crearUbicacion(new Pueblo("Plantalandia"));
        service.crearUbicacion(new Pueblo("Agualandia"));
        service.crearUbicacion(new Pueblo("Bicholandia"));
        service.crearUbicacion(new Pueblo("Lagartolandia"));
        service.crearUbicacion(new Dojo("Tibet Dojo"));
        service.crearUbicacion(new Guarderia("St.Blah"));
        service.crearUbicacion(new Guarderia("Poke"));
        service.crearUbicacion(new Dojo("A1"));
        service.crearUbicacion(new Dojo("A2"));
        service.conectar("St.Blah", "Plantalandia", "Aereo");
        service.conectar("St.Blah", "Agualandia", "Terrestre");
        service.conectar("Agualandia", "St.Blah", "Terrestre");
        service.conectar("Agualandia", "Plantalandia", "Maritimo");
        service.conectar("Plantalandia", "Agualandia", "Maritimo");
        service.conectar("Agualandia", "Lagartolandia", "Maritimo");
        service.conectar("Lagartolandia", "Agualandia", "Maritimo");
        service.conectar("Agualandia", "Bicholandia", "Maritimo");
        service.conectar("Bicholandia", "Lagartolandia", "Terrestre");
        service.conectar("Lagartolandia", "Bicholandia", "Terrestre");
        service.conectar("Bicholandia", "Tibet Dojo", "Aereo");
        service.conectar("Tibet Dojo", "Bicholandia", "Aereo");
        service.conectar("Tibet Dojo", "Plantalandia", "Terrestre");
        service.conectar("Poke", "Plantalandia", "Terrestre");
        service.conectar("A1", "Poke", "Aereo");
        service.conectar("A2", "A1", "Aereo");
    }

    @Test
    void conectadosAAgualandiaPorMaritimo() {
        List<Ubicacion> cs = service.conectados("Agualandia", "Maritimo");
        assertEquals(3, cs.size());
        List<String> expected = Arrays.asList("Plantalandia", "Lagartolandia", "Bicholandia");
        assertTrue(cs.stream().map(Ubicacion::getNombre).allMatch(expected::contains));
    }

    @Test
    void conectadosAStBlahPorMaritimoNoTiene() {
        assertEquals(0, service.conectados("St.Blah", "Maritimo").size());
    }

    @Test
    void entrenadorMoverNoPuedeExceptionUbicacionMuyLejana() {
        newEntrenador("Alex", testService.getByName(Pueblo.class, "Plantalandia"));
        assertThrows(UbicacionMuyLejanaException.class, () -> service.mover("Alex", "poke"));
    }

    @Test
    void entrenadorMoverMasCortoNoPuedeExceptionUbicacionMuyLejana() {
        newEntrenador("Alex1", testService.getByName(Pueblo.class, "Plantalandia"));
        assertThrows(UbicacionMuyLejanaException.class, () -> service.moverMasCorto("Alex1", "poke"));
    }

    @Test
    void entrenadorMoverNoPuedeExceptionCaminoMuyCostoso() {
        newEntrenador("pepe", testService.getByName(Dojo.class, "A2"));
        assertThrows(CaminoMuyCostosoException.class, () -> service.mover("pepe", "Plantalandia"));
    }

    @Test
    void entrenadorMoverMasCortoNoPuedeExceptionCaminoMuyCostoso() {
        newEntrenador("pepe2", testService.getByName(Dojo.class, "A2"));
        assertThrows(CaminoMuyCostosoException.class, () -> service.moverMasCorto("pepe2", "Plantalandia"));
    }

    @Test
    void entrenadorMoverMasBarato() {
        newEntrenador("jose", testService.getByName(Guarderia.class, "St.Blah"));
        service.mover("jose", "Plantalandia");
        assertEquals(7, testService.getByName(Entrenador.class, "jose").getMonedas());
    }

    @Test
    void entrenadorMoverMasCorto() {
        newEntrenador("jose2", testService.getByName(Guarderia.class, "St.Blah"));
        service.moverMasCorto("jose2", "Plantalandia");
        Entrenador jose2 = testService.getByName(Entrenador.class, "jose2");
        assertEquals(5, jose2.getMonedas());
        assertEquals("Plantalandia", jose2.getUbicacion().getNombre());
    }

    @Test
    void muevo_1_entrenadores_al_pueblo_y_hay_1_entrenadores() {
        newEntrenador("ronny", testService.getByName(Pueblo.class, "Agualandia"));
        service.mover("ronny", "Bicholandia");
        assertEquals(1, service.cantidadEntrenadores("Bicholandia"));
        assertEquals(0, service.cantidadEntrenadores("Agualandia"));
    }

}
