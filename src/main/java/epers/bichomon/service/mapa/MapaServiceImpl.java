package epers.bichomon.service.mapa;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.runner.Runner;

public class MapaServiceImpl implements MapaService {

    private UbicacionDAO ubicacionDAO;
    private EntrenadorDAO entrenadorDAO;

    public MapaServiceImpl(UbicacionDAO ubicacionDAO, EntrenadorDAO entrenadorDAO) {
        this.ubicacionDAO = ubicacionDAO;
        this.entrenadorDAO = entrenadorDAO;
    }

    /**
     * se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
     */
    @Override
    public void mover(String nombreEntrenador, String nuevaUbicacion) {
        Runner.runInSession(() -> {
            Entrenador entrenador = entrenadorDAO.recuperar(nombreEntrenador);
            Ubicacion ubicacion = ubicacionDAO.recuperar(nuevaUbicacion);
            entrenador.moverA(ubicacion);
            entrenadorDAO.actualizar(entrenador);
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

    @Override
    public Bicho campeon(String dojo) {
        return null;
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        return null;
    }
}
