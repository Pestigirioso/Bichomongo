package epers.bichomon;

import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.mapa.MapaService;
import org.junit.jupiter.api.BeforeAll;

public class AbstractCaminoTest extends AbstractServiceTest {

    @BeforeAll
    static void prepareCamino() {
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
}
