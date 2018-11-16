package epers.bichomon.service.feed;

import epers.bichomon.AbstractCaminoTest;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.mapa.MapaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
