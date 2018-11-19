package epers.bichomon;

import epers.bichomon.model.evento.EventoAbandono;
import epers.bichomon.model.evento.EventoArribo;
import epers.bichomon.model.evento.EventoCaptura;
import epers.bichomon.model.evento.EventoCoronacion;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Guarderia;
import epers.bichomon.model.ubicacion.Pueblo;
import epers.bichomon.model.ubicacion.TipoCamino;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.mapa.MapaService;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractConectadosTest extends AbstractServiceTest {

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
        service.conectar("St.Blah", "Plantalandia", TipoCamino.Aereo);
        service.conectar("St.Blah", "Agualandia", TipoCamino.Terrestre);
        service.conectar("Agualandia", "St.Blah", TipoCamino.Terrestre);
        service.conectar("Agualandia", "Plantalandia", TipoCamino.Maritimo);
        service.conectar("Plantalandia", "Agualandia", TipoCamino.Maritimo);
        service.conectar("Agualandia", "Lagartolandia", TipoCamino.Maritimo);
        service.conectar("Lagartolandia", "Agualandia", TipoCamino.Maritimo);
        service.conectar("Agualandia", "Bicholandia", TipoCamino.Maritimo);
        service.conectar("Bicholandia", "Lagartolandia", TipoCamino.Terrestre);
        service.conectar("Lagartolandia", "Bicholandia", TipoCamino.Terrestre);
        service.conectar("Bicholandia", "Tibet Dojo", TipoCamino.Aereo);
        service.conectar("Tibet Dojo", "Bicholandia", TipoCamino.Aereo);
        service.conectar("Tibet Dojo", "Plantalandia", TipoCamino.Terrestre);
        service.conectar("Poke", "Plantalandia", TipoCamino.Terrestre);
        service.conectar("A1", "Poke", TipoCamino.Aereo);
        service.conectar("A2", "A1", TipoCamino.Aereo);
    }

    protected void checkEvento(EventoArribo evento, String entrenador, String origen, String destino) {
        assertEquals(entrenador, evento.getEntrenador());
        assertEquals(origen, evento.getOrigen());
        assertEquals(destino, evento.getDestino());
    }

    protected void checkEvento(EventoCoronacion evento, String coronado, String descoronado, String ubicacion) {
        assertEquals(coronado, evento.getCoronado());
        assertEquals(descoronado, evento.getDescoronado());
        assertEquals(ubicacion, evento.getUbicacion());
    }

    protected void checkEvento(EventoCaptura evento, String entrenador, String ubicacion, String especie) {
        assertEquals(entrenador, evento.getEntrenador());
        assertEquals(ubicacion, evento.getUbicacion());
        assertEquals(especie, evento.getEspecie());
    }

    protected void checkEvento(EventoAbandono evento, String entrenador, String ubicacion, String especie) {
        assertEquals(entrenador, evento.getEntrenador());
        assertEquals(ubicacion, evento.getUbicacion());
        assertEquals(especie, evento.getEspecie());
    }
}
