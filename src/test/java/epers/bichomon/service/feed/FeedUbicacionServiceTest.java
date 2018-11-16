package epers.bichomon.service.feed;

import epers.bichomon.AbstractCaminoTest;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.mapa.MapaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FeedUbicacionServiceTest extends AbstractCaminoTest {

    private MapaService mapService = ServiceFactory.INSTANCE.getMapService();
    private BichoService bichoService = ServiceFactory.INSTANCE.getBichoService();
    private FeedService feedService = ServiceFactory.INSTANCE.getFeedService();

    @BeforeAll
    static void prepare() {
        testService.save(new Especie("rocamon", TipoBicho.TIERRA, 10));
        testService.save(new Especie("metalmon", TipoBicho.TIERRA, 100));
    }

    @Test
    void ubicacionInexistenteRetornaListaVacia() {
        assertThrows(EntrenadorInexistenteException.class, () -> feedService.feedUbicacion(""));
    }

    @Test
    void ubicacionNuevaSinEventosRetornaListaVacia() {
        mapService.crearUbicacion(new Pueblo("Pueblucho"));
        newEntrenador("nuevo", testService.getByName(Ubicacion.class, "Pueblucho"));
        assertTrue(feedService.feedEntrenador("nuevo").isEmpty());
    }

//    @Test
//    void EntrenadorViajoFeedUbicacionMuestraElViaje() {
//        String e = "entrenador";
//        newEntrenador(e, testService.getByName(Ubicacion.class, "Plantalandia"));
//
//        mapService.mover(e, "Agualandia");
//
//        List<Evento> eventos = feedService.feedEntrenador(e);
//        assertEquals(1, eventos.size());
//        checkEvento(eventos.get(0), e, "Agualandia", TipoEvento.Arribo);
//    }
//
//    @Test
//    void EntrenadorViajo2VecesMuestraViajesEnOrden() {
//        String e = "viajador2";
//        newEntrenador(e, testService.getByName(Ubicacion.class, "Plantalandia"));
//
//        mapService.mover(e, "Agualandia");
//        mapService.mover(e, "Plantalandia");
//
//        List<Evento> eventos = feedService.feedEntrenador(e);
//        assertEquals(2, eventos.size());
//        checkEvento(eventos.get(0), e, "Plantalandia", TipoEvento.Arribo);
//        checkEvento(eventos.get(1), e, "Agualandia", TipoEvento.Arribo);
//    }
//
//    @Test
//    void FeedUbicacionConEventoCaptura() {
//        Bicho bichin = testService.getByName(Especie.class, "rocamon").crearBicho();
//        testService.save(bichin);
//        String trainer = "trainer";
//        Ubicacion place = new Guarderia("guardaBicho");
//        testService.save(place);
//        place.abandonar(bichin);
//        testService.upd(place);
//        newEntrenador(trainer, place);
//
//        bichoService.buscar(trainer);
//
//        List<Evento> eventos = feedService.feedEntrenador(trainer);
//        assertEquals(1, eventos.size());
//        checkEvento(eventos.get(0), trainer, "guardaBicho", TipoEvento.Captura);
//    }
//
//    @Test
//    void FeedUbicacionConEventoAbandono() {
//        Especie e = testService.getByName(Especie.class, "rocamon");
//        Bicho b1 = e.crearBicho();
//        Bicho b2 = e.crearBicho();
//
//        newEntrenador("lucas", testService.getByName(Guarderia.class, "Poke"), Sets.newHashSet(b1, b2));
//
//        bichoService.abandonar("lucas", b1.getID());
//
//        List<Evento> eventos = feedService.feedEntrenador("lucas");
//        assertEquals(1, eventos.size());
//        checkEvento(eventos.get(0), "lucas", "Poke", TipoEvento.Abandono);
//    }
//
//    @Test
//    void FeedUbicacionConEventoCoronado() {
//        Bicho b = testService.getByName(Especie.class, "rocamon").crearBicho();
//        newEntrenador("brock", testService.getByName(Dojo.class, "Tibet Dojo"), Sets.newHashSet(b));
//
//        bichoService.duelo("brock", b.getID());
//
//        List<Evento> eventos = feedService.feedEntrenador("brock");
//        assertEquals(1, eventos.size());
//        checkEvento(eventos.get(0), "brock", "Tibet Dojo", TipoEvento.Coronacion);
//    }
//
//    @Test
//    void FeedUbicacionConEventoCoronadoYDescoronado() {
//        String dojo = "A1";
//        Bicho b1 = testService.getByName(Especie.class, "rocamon").crearBicho();
//        newEntrenador("alberto", testService.getByName(Dojo.class, dojo), Sets.newHashSet(b1));
//        Bicho b2 = testService.getByName(Especie.class, "metalmon").crearBicho();
//        newEntrenador("julio", testService.getByName(Dojo.class, dojo), Sets.newHashSet(b2));
//
//        bichoService.duelo("alberto", b1.getID());
//        bichoService.duelo("julio", b2.getID());
//
//        List<Evento> eventosAlberto = feedService.feedEntrenador("alberto");
//        assertEquals(2, eventosAlberto.size());
//        checkEvento(eventosAlberto.get(0), "alberto", dojo, TipoEvento.Coronacion);
//        checkEvento(eventosAlberto.get(1), "alberto", dojo, TipoEvento.Coronacion);
//
//        List<Evento> eventosJulio = feedService.feedEntrenador("julio");
//        assertEquals(1, eventosJulio.size());
//        checkEvento(eventosJulio.get(0), "julio", dojo, TipoEvento.Coronacion);
//    }
}
