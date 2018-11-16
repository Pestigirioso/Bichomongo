package epers.bichomon.service.feed;

import epers.bichomon.AbstractCaminoTest;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.evento.*;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
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

public class FeedEntrenadorServiceTest extends AbstractCaminoTest {

    private MapaService mapService = ServiceFactory.INSTANCE.getMapService();
    private BichoService bichoService = ServiceFactory.INSTANCE.getBichoService();
    private FeedService feedService = ServiceFactory.INSTANCE.getFeedService();

    @BeforeAll
    static void prepare() {
        testService.save(new Especie("rocamon", TipoBicho.TIERRA, 10));
        testService.save(new Especie("metalmon", TipoBicho.TIERRA, 100));
    }

    @Test
    void entrenadorInexistenteRetornaListaVacia() {
        assertTrue(feedService.feedEntrenador("fruta").isEmpty());
    }

    @Test
    void entrenadorNuevoSinEventosRetornaListaVacia() {
        newEntrenador("nuevo", testService.getByName(Ubicacion.class, "Plantalandia"));
        assertTrue(feedService.feedEntrenador("nuevo").isEmpty());
    }

    private void checkEvento(EventoArribo evento, String entrenador, String origen, String destino) {
        assertEquals(entrenador, evento.getEntrenador());
        assertEquals(origen, evento.getOrigen());
        assertEquals(destino, evento.getDestino());
    }

    private void checkEvento(EventoCoronacion evento, String coronado, String descoronado, String ubicacion) {
        assertEquals(coronado, evento.getCoronado());
        assertEquals(descoronado, evento.getDescoronado());
        assertEquals(ubicacion, evento.getUbicacion());
    }

    private void checkEvento(EventoCaptura evento, String entrenador, String ubicacion, String especie) {
        assertEquals(entrenador, evento.getEntrenador());
        assertEquals(ubicacion, evento.getUbicacion());
        assertEquals(especie, evento.getEspecie());
    }

    private void checkEvento(EventoAbandono evento, String entrenador, String ubicacion, String especie) {
        assertEquals(entrenador, evento.getEntrenador());
        assertEquals(ubicacion, evento.getUbicacion());
        assertEquals(especie, evento.getEspecie());
    }

    @Test
    void EntrenadorViajoFeedEntrenadorMuestraElViaje() {
        String e = "entrenador";
        newEntrenador(e, testService.getByName(Ubicacion.class, "Plantalandia"));

        mapService.mover(e, "Agualandia");

        List<Evento> eventos = feedService.feedEntrenador(e);
        assertEquals(1, eventos.size());
        checkEvento((EventoArribo) eventos.get(0), e, "Plantalandia", "Agualandia");
    }

    @Test
    void EntrenadorViajo2VecesMuestraViajesEnOrden() {
        String e = "viajador2";
        newEntrenador(e, testService.getByName(Ubicacion.class, "Plantalandia"));

        mapService.mover(e, "Agualandia");
        mapService.mover(e, "Plantalandia");

        List<Evento> eventos = feedService.feedEntrenador(e);
        assertEquals(2, eventos.size());
        checkEvento((EventoArribo) eventos.get(0), e, "Agualandia", "Plantalandia");
        checkEvento((EventoArribo) eventos.get(1), e, "Plantalandia", "Agualandia");
    }

    @Test
    void FeedEntrenadorConEventoCaptura() {
        Bicho bichin = testService.getByName(Especie.class, "rocamon").crearBicho();
        testService.save(bichin);
        String trainer = "trainer";
        Ubicacion place = new Guarderia("guardaBicho");
        testService.save(place);
        place.abandonar(bichin);
        testService.upd(place);
        newEntrenador(trainer, place);

        bichoService.buscar(trainer);

        List<Evento> eventos = feedService.feedEntrenador(trainer);
        assertEquals(1, eventos.size());
        checkEvento((EventoCaptura) eventos.get(0), trainer, "guardaBicho", "rocamon");
    }

    @Test
    void FeedEntrenadorConEventoAbandono() {
        Especie e = testService.getByName(Especie.class, "rocamon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();

        newEntrenador("lucas", testService.getByName(Guarderia.class, "Poke"), Sets.newHashSet(b1, b2));

        bichoService.abandonar("lucas", b1.getID());

        List<Evento> eventos = feedService.feedEntrenador("lucas");
        assertEquals(1, eventos.size());
        checkEvento((EventoAbandono) eventos.get(0), "lucas", "Poke", "rocamon");
    }

    @Test
    void FeedEntrenadorConEventoCoronado() {
        Bicho b = testService.getByName(Especie.class, "rocamon").crearBicho();
        newEntrenador("brock", testService.getByName(Dojo.class, "Tibet Dojo"), Sets.newHashSet(b));

        bichoService.duelo("brock", b.getID());

        List<Evento> eventos = feedService.feedEntrenador("brock");
        assertEquals(1, eventos.size());
        checkEvento((EventoCoronacion) eventos.get(0), "brock", "", "Tibet Dojo");
    }

    @Test
    void FeedEntrenadorConEventoCoronadoYDescoronado() {
        String dojo = "A1";
        Bicho b1 = testService.getByName(Especie.class, "rocamon").crearBicho();
        newEntrenador("alberto", testService.getByName(Dojo.class, dojo), Sets.newHashSet(b1));
        Bicho b2 = testService.getByName(Especie.class, "metalmon").crearBicho();
        newEntrenador("julio", testService.getByName(Dojo.class, dojo), Sets.newHashSet(b2));

        bichoService.duelo("alberto", b1.getID());
        bichoService.duelo("julio", b2.getID());

        List<Evento> eventosAlberto = feedService.feedEntrenador("alberto");
        assertEquals(2, eventosAlberto.size());
        checkEvento((EventoCoronacion) eventosAlberto.get(0), "julio", "alberto", dojo);
        checkEvento((EventoCoronacion) eventosAlberto.get(1), "alberto", "", dojo);

        List<Evento> eventosJulio = feedService.feedEntrenador("julio");
        assertEquals(1, eventosJulio.size());
        checkEvento((EventoCoronacion) eventosJulio.get(0), "julio", "alberto", dojo);
    }

}
