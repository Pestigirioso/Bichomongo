package epers.bichomon.service.mapa;

import epers.bichomon.AbstractCaminoTest;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapaServiceCaminoTest extends AbstractCaminoTest {

    private MapaService service = ServiceFactory.INSTANCE.getMapService();

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
