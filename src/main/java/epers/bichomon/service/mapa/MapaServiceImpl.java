package epers.bichomon.service.mapa;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.runner.Runner;

import java.util.List;

public class MapaServiceImpl implements MapaService {

    private UbicacionDAO ubicacionDAO;
    private EntrenadorDAO entrenadorDAO;

    public MapaServiceImpl(UbicacionDAO ubicacionDAO, EntrenadorDAO entrenadorDAO) {
        this.ubicacionDAO = ubicacionDAO;
        this.entrenadorDAO = entrenadorDAO;
    }

    /**
     * Se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
     *
     * - Arroje una excepcion UbicacionMuyLejana si no es posible llegar desde la actual ubicación
     *      del entrenador a la nueva por medio de un camino.
     *
     * - Luego de moverse se decrementa la cantidad de monedas del entrenador en el número adecuado.
     *
     * - Arrojar una excepcion CaminoMuyCostoso si el entrenador no tiene suficientes monedas para
     *      pagar el costo del camino que une a la actual ubicación con la ubicación nueva.
     *      En caso de existir más de un camino que conecte ambas ubicaciones entonces se deberá
     *      optar por el más barato.
     *
     */
    @Override
    public void mover(String nombreEntrenador, String nuevaUbicacion) {
        Runner.runInSession(() -> {
            Entrenador entrenador = entrenadorDAO.get(nombreEntrenador);
            Ubicacion ubicacion = ubicacionDAO.get(nuevaUbicacion);
            entrenador.moverA(ubicacion);
            entrenadorDAO.upd(entrenador);
            return null;
        });
        // TODO reimplementar
    }

    /**
     * Se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
     * Optando por el camino mas corto
     */
    @Override
    public void moverMasCorto(String entrenador, String ubicacion) {
        //TODO Implementar
    }

    /**
     * Devolver todas las Ubicaciones conectadas directamente a una ubicación
     * provista por medio de un tipo de camino especificado.
     */
    @Override
    public List<Ubicacion> conectados(String ubicacion, String tipoCamino) {
        return Runner.runInSession(() -> ubicacionDAO.conectados(ubicacion, tipoCamino));
    }

    /**
     * Crea una nueva ubicación (la cual es provista por parametro) tanto en hibernate como en neo4j.
     */
    @Override
    public void crearUbicacion(Ubicacion ubicacion) {
        Runner.runInSession(() -> {
            ubicacionDAO.save(ubicacion);
            return null;
        });
    }

    /**
     * Conectar dos ubicaciones (se asumen preexistentes) por medio de un tipo de camino.
     */
    @Override
    public void conectar(String ubicacion1, String ubicacion2, String tipoCamino) {
        Runner.runInSession(() -> {
            ubicacionDAO.conectar(ubicacion1, ubicacion2, tipoCamino);
            return null;
        });
    }

    /**
     * se deberá devolver la cantidad de entrenadores que se encuentren actualmente en dicha localización.
     */
    @Override
    public int cantidadEntrenadores(String ubicacion) {
        return Runner.runInSession(() -> entrenadorDAO.cantidadEntrenadores(ubicacion));
    }

    /**
     * retorna el actual campeon del Dojo especificado.
     */
    @Override
    public Bicho campeon(String dojo) {
        return Runner.runInSession(() -> ubicacionDAO.getDojo(dojo).getCampeon());
    }

    /**
     * retorna el bicho que haya sido campeon por mas tiempo en el Dojo.
     */
    @Override
    public Bicho campeonHistorico(String dojo) {
        return Runner.runInSession(() -> ubicacionDAO.campeonHistorico(dojo));
    }
}
