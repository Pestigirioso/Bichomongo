package epers.bichomon.service.feed;

import epers.bichomon.AbstractConectadosTest;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.evento.*;
import epers.bichomon.model.ubicacion.*;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.mapa.MapaService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FeedUbicacionServiceTest extends AbstractConectadosTest {

    private MapaService mapService = ServiceFactory.INSTANCE.getMapService();
    private BichoService bichoService = ServiceFactory.INSTANCE.getBichoService();
    private FeedService feedService = ServiceFactory.INSTANCE.getFeedService();

    @BeforeAll
    static void prepare() {
        testService.save(new Especie("rocamon", TipoBicho.TIERRA, 10));
        testService.save(new Especie("metalmon", TipoBicho.TIERRA, 100));
        newEntrenador("e1", testService.getByName(Ubicacion.class, "Poke"));
        ServiceFactory.INSTANCE.getMapService().mover("e1", "Plantalandia");
    }

    @Test
    void ubicacionInexistenteRetornaListaVacia() {
        assertThrows(EntrenadorInexistenteException.class, () -> feedService.feedUbicacion("fruta"));
    }

    @Test
    void ubicacionNuevaSinEventosRetornaListaVacia() {
        mapService.crearUbicacion(new Pueblo("Pueblucho"));
        newEntrenador("nuevo", testService.getByName(Ubicacion.class, "Pueblucho"));
        assertTrue(feedService.feedUbicacion("nuevo").isEmpty());
    }

    @Test
    void EntrenadorViajoFeedUbicacionMuestraElViaje() {
        String e = "entrenador";
        mapService.crearUbicacion(new Pueblo("p1"));
        mapService.crearUbicacion(new Pueblo("p2"));
        mapService.conectar("p1", "p2", TipoCamino.Terrestre);
        newEntrenador(e, testService.getByName(Ubicacion.class, "p1"));

        mapService.mover(e, "p2");

        List<Evento> eventos = feedService.feedUbicacion(e);
        assertEquals(1, eventos.size());
        checkEvento((EventoArribo) eventos.get(0), e, "p1", "p2");
    }

    @Test
    void EntrenadorViajo2VecesMuestraViajesEnOrden() {
        String e = "viajador2";
        mapService.crearUbicacion(new Pueblo("p3"));
        mapService.crearUbicacion(new Pueblo("p4"));
        mapService.conectar("p3", "p4", TipoCamino.Terrestre);
        mapService.conectar("p4", "p3", TipoCamino.Terrestre);
        newEntrenador(e, testService.getByName(Ubicacion.class, "p3"));

        mapService.mover(e, "p4");
        mapService.mover(e, "p3");

        List<Evento> eventos = feedService.feedUbicacion(e);
        assertEquals(2, eventos.size());
        checkEvento((EventoArribo) eventos.get(0), e, "p4", "p3");
        checkEvento((EventoArribo) eventos.get(1), e, "p3", "p4");
    }

    @Test
    void FeedUbicacionConEventoCaptura() {
        Bicho bichin = testService.getByName(Especie.class, "rocamon").crearBicho();
        testService.save(bichin);
        String trainer = "trainer";
        Ubicacion place = new Guarderia("guardaBicho");
        testService.save(place);
        place.abandonar(bichin);
        testService.upd(place);
        newEntrenador(trainer, place);

        bichoService.buscar(trainer);

        List<Evento> eventos = feedService.feedUbicacion(trainer);
        assertEquals(1, eventos.size());
        checkEvento((EventoCaptura) eventos.get(0), trainer, "guardaBicho", "rocamon");
    }

    @Test
    void FeedUbicacionConEventoAbandono() {
        mapService.crearUbicacion(new Guarderia("p5"));

        Especie e = testService.getByName(Especie.class, "rocamon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();

        newEntrenador("lucas", testService.getByName(Guarderia.class, "p5"), Sets.newHashSet(b1, b2));

        bichoService.abandonar("lucas", b1.getID());

        List<Evento> eventos = feedService.feedUbicacion("lucas");
        assertEquals(1, eventos.size());
        checkEvento((EventoAbandono) eventos.get(0), "lucas", "p5", "rocamon");
    }

    @Test
    void FeedUbicacionConEventoCoronado() {
        mapService.crearUbicacion(new Dojo("p6"));
        Bicho b = testService.getByName(Especie.class, "rocamon").crearBicho();
        newEntrenador("brock", testService.getByName(Dojo.class, "p6"), Sets.newHashSet(b));

        bichoService.duelo("brock", b.getID());

        List<Evento> eventos = feedService.feedUbicacion("brock");
        assertEquals(1, eventos.size());
        checkEvento((EventoCoronacion) eventos.get(0), "brock", "", "p6");
    }

    @Test
    void FeedUbicacionConEventoCoronadoYDescoronado() {
        mapService.crearUbicacion(new Dojo("p7"));
        Bicho b1 = testService.getByName(Especie.class, "rocamon").crearBicho();
        newEntrenador("alberto", testService.getByName(Dojo.class, "p7"), Sets.newHashSet(b1));
        Bicho b2 = testService.getByName(Especie.class, "metalmon").crearBicho();
        newEntrenador("julio", testService.getByName(Dojo.class, "p7"), Sets.newHashSet(b2));

        bichoService.duelo("alberto", b1.getID());
        bichoService.duelo("julio", b2.getID());

        List<Evento> eventosAlberto = feedService.feedUbicacion("alberto");
        assertEquals(2, eventosAlberto.size());
        checkEvento((EventoCoronacion) eventosAlberto.get(0), "julio", "alberto", "p7");
        checkEvento((EventoCoronacion) eventosAlberto.get(1), "alberto", "", "p7");

        List<Evento> eventosJulio = feedService.feedUbicacion("julio");
        assertEquals(2, eventosJulio.size());
        checkEvento((EventoCoronacion) eventosJulio.get(0), "julio", "alberto", "p7");
        checkEvento((EventoCoronacion) eventosAlberto.get(1), "alberto", "", "p7");
    }

    @Test
    void FeedUbicacionNoRecibeEventoDeUbicacionConectadaEn2doNivel() {
        newEntrenador("e2", testService.getByName(Ubicacion.class, "A2"));
        List<Evento> eventos = feedService.feedUbicacion("e2");
        assertEquals(0, eventos.size());
    }

    @Test
    void FeedUbicacionCentralRecibeEventosDeTodasSusConectadas() {
        Bicho bichin = testService.getByName(Especie.class, "rocamon").crearBicho();
        testService.save(bichin);
        Ubicacion place = testService.getByName(Ubicacion.class, "St.Blah");
        place.abandonar(bichin);
        testService.upd(place);
        newEntrenador("buscador", place);
        bichoService.buscar("buscador");

        Especie e = testService.getByName(Especie.class, "rocamon");
        Bicho b1 = e.crearBicho();
        Bicho b2 = e.crearBicho();
        newEntrenador("abandonador", testService.getByName(Ubicacion.class, "St.Blah"), Sets.newHashSet(b1, b2));
        bichoService.abandonar("abandonador", b1.getID());

        newEntrenador("mochilero", testService.getByName(Ubicacion.class, "Plantalandia"));
        mapService.mover("mochilero", "Agualandia");
        mapService.mover("mochilero", "Lagartolandia");
        mapService.mover("mochilero", "Bicholandia");

        newEntrenador("e4", testService.getByName(Ubicacion.class, "Agualandia"));

        List<Evento> eventos = feedService.feedUbicacion("e4");
        assertEquals(6, eventos.size());
        checkEvento((EventoArribo) eventos.get(0), "mochilero", "Lagartolandia", "Bicholandia");
        checkEvento((EventoArribo) eventos.get(1), "mochilero", "Agualandia", "Lagartolandia");
        checkEvento((EventoArribo) eventos.get(2), "mochilero", "Plantalandia", "Agualandia");
        checkEvento((EventoAbandono) eventos.get(3), "abandonador", "St.Blah", "rocamon");
        checkEvento((EventoCaptura) eventos.get(4), "buscador", "St.Blah", "rocamon");
        checkEvento((EventoArribo) eventos.get(5), "e1", "Poke", "Plantalandia");
    }
}
