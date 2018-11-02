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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapaServiceCaminoTest extends AbstractServiceTest {

    private MapaService service = ServiceFactory.getMapService();

    @BeforeAll
    static void prepare() {
        MapaService service = ServiceFactory.getMapService();
        service.crearUbicacion(new Pueblo("Plantalandia"));
        service.crearUbicacion(new Pueblo("Agualandia"));
        service.crearUbicacion(new Pueblo("Bicholandia"));
        service.crearUbicacion(new Pueblo("Lagartolandia"));
        service.crearUbicacion(new Dojo("Tibet Dojo"));
        service.crearUbicacion(new Guarderia("St.Blah"));
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
    void alViajarPorCaminoTerrestreEntrenadorPierdeUnaMoneda(){
        newEntrenador("Trainer", testService.getByName(Ubicacion.class, "Agualandia"));
        service.mover("Trainer","Bicholandia");
        assertEquals(4,testService.getByName(Entrenador.class, "Trainer").getMonedas());
        testService.deleteByName(Entrenador.class, "Trainer");
    }

//    /**
//     * Se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
//     *
//     * - Arroje una excepcion UbicacionMuyLejana si no es posible llegar desde la actual ubicación
//     *      del entrenador a la nueva por medio de un camino.
//     *
//     * - Luego de moverse se decrementa la cantidad de monedas del entrenador en el número adecuado.
//     *
//     * - Arrojar una excepcion CaminoMuyCostoso si el entrenador no tiene suficientes monedas para
//     *      pagar el costo del camino que une a la actual ubicación con la ubicación nueva.
//     *      En caso de existir más de un camino que conecte ambas ubicaciones entonces se deberá
//     *      optar por el más barato.
//     *
//     */
//    @Override
//    public void mover(String nombreEntrenador, String nuevaUbicacion) {
//        Runner.runInSession(() -> {
//            Entrenador entrenador = entrenadorDAO.get(nombreEntrenador);
//            Ubicacion ubicacion = ubicacionDAO.get(nuevaUbicacion);
//            entrenador.moverA(ubicacion);
//            entrenadorDAO.upd(entrenador);
//            return null;
//        });
//        // TODO reimplementar
//    }
//
//    /**
//     * Se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
//     * Optando por el camino mas corto
//     */
//    @Override
//    public void moverMasCorto(String entrenador, String ubicacion) {
//        // TODO implementar
//    }

}
