package epers.bichomon.service.feed;

import epers.bichomon.AbstractServiceTest;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.evento.Evento;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.mapa.MapaService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedServiceTest extends AbstractServiceTest {

    private MapaService mapService = ServiceFactory.INSTANCE.getMapService();
    private BichoService bichoService = ServiceFactory.INSTANCE.getBichoService();
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
    void entrenadorInexistenteRetornaListaVacia() {
        assertTrue(service.feedEntrenador("").isEmpty());
    }

    @Test
    void entrenadorNuevoSinEventosRetornaListaVacia() {
        newEntrenador("nuevo", testService.getByName(Ubicacion.class, "Plantalandia"));
        assertTrue(service.feedEntrenador("nuevo").isEmpty());
    }

    @Test
    void siElEntrenadorViajoFeedEntrenadorMuestraElViaje() {
        String e = "entrenador";
        newEntrenador(e, testService.getByName(Ubicacion.class, "Plantalandia"));
        mapService.mover(e, "Agualandia");
        List<Evento> eventos = service.feedEntrenador(e);
        assertEquals(1, eventos.size());
        assertEquals(e, eventos.get(0).getEntrenador());
    }

    @Test
    void siElEntrenadorCapturaUnBichoFeedEntrenadorMuestraLaCaptura(){
        Especie esp = new Especie("rocamon", TipoBicho.TIERRA);
        testService.save(esp);

        Bicho bichin = esp.crearBicho();
        testService.save(bichin);
        String trainer = "trainer";
        Ubicacion place  = new Guarderia("guardaBicho");
        testService.save(place);
        place.abandonar(bichin);
        testService.upd(place);
        newEntrenador(trainer,place);

        bichoService.buscar(trainer);

        List<Evento> eventos = service.feedEntrenador(trainer);
        assertEquals(1, eventos.size());
        assertEquals(trainer,eventos.get(0).getEntrenador());
        assertEquals("guardaBicho", eventos.get(0).getUbicacion());
    }
}
