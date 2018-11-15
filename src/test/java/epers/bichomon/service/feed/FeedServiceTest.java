package epers.bichomon.service.feed;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.model.evento.Evento;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.mapa.MapaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FeedServiceTest extends AbstractServiceTest {

    private MapaService mapService = ServiceFactory.INSTANCE.getMapService();
    private FeedService service = ServiceFactory.INSTANCE.getFeedService();

    @BeforeAll
    static void prepare() {
        MapaService mapService = ServiceFactory.INSTANCE.getMapService();
        mapService.crearUbicacion(new Pueblo("Plantalandia"));
        mapService.crearUbicacion(new Pueblo("Agualandia"));
        mapService.crearUbicacion(new Pueblo("Bicholandia"));
        mapService.crearUbicacion(new Pueblo("Lagartolandia"));
        mapService.crearUbicacion(new Dojo("Tibet Dojo"));
        mapService.crearUbicacion(new Guarderia("St.Blah"));
        mapService.crearUbicacion(new Guarderia("Poke"));
        mapService.crearUbicacion(new Dojo("A1"));
        mapService.crearUbicacion(new Dojo("A2"));
        mapService.conectar("St.Blah", "Plantalandia", "Aereo");
        mapService.conectar("St.Blah", "Agualandia", "Terrestre");
        mapService.conectar("Agualandia", "St.Blah", "Terrestre");
        mapService.conectar("Agualandia", "Plantalandia", "Maritimo");
        mapService.conectar("Plantalandia", "Agualandia", "Maritimo");
        mapService.conectar("Agualandia", "Lagartolandia", "Maritimo");
        mapService.conectar("Lagartolandia", "Agualandia", "Maritimo");
        mapService.conectar("Agualandia", "Bicholandia", "Maritimo");
        mapService.conectar("Bicholandia", "Lagartolandia", "Terrestre");
        mapService.conectar("Lagartolandia", "Bicholandia", "Terrestre");
        mapService.conectar("Bicholandia", "Tibet Dojo", "Aereo");
        mapService.conectar("Tibet Dojo", "Bicholandia", "Aereo");
        mapService.conectar("Tibet Dojo", "Plantalandia", "Terrestre");
        mapService.conectar("Poke", "Plantalandia", "Terrestre");
        mapService.conectar("A1", "Poke", "Aereo");
        mapService.conectar("A2", "A1", "Aereo");
    }

    @Test
    void feedEntrenadorInexistenteFalla() {
        assertThrows(NullPointerException.class, () -> service.feedEntrenador(""));
    }

    @Test
    void siElEntrenadorViajoFeedEntrenadorMuestraElViaje() {
        newEntrenador("entrenador", testService.getByName(Ubicacion.class, "Plantalandia"));
        mapService.mover("entrenador","Agualandia");
        List<Evento> eventos = service.feedEntrenador("entrenador");
        assertTrue(eventos.contains());
    }
}
