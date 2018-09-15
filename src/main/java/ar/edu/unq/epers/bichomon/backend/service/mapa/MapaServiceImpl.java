package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class MapaServiceImpl implements MapaService {

    private UbicacionDAO ubicacionDAO;
    private EntrenadorDAO entrenadorDAO;

    public MapaServiceImpl(UbicacionDAO ubicacionDAO, EntrenadorDAO entrenadorDAO) {
        this.ubicacionDAO = ubicacionDAO;
        this.entrenadorDAO = entrenadorDAO;
    }

//    * se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
//    *
//    * @param entrenador
//    * @param ubicacion

    @Override
    public void mover(String nombreEntrenador, String nuevaUbicacion) {
//        Runner.runInSession(() -> {
            Entrenador entrenador = entrenadorDAO.recuperar(nombreEntrenador);
            Ubicacion ubicacion = ubicacionDAO.recuperar(nuevaUbicacion);
            entrenador.moverA(ubicacion);
            entrenadorDAO.actualizar(entrenador);
//            return null;
//        });
    }


//     * se deberá devolver la cantidad de entrenadores que se encuentren actualmente en dicha localización.
//
//     * @param ubicacion
//     * @return

    @Override
    public int cantidadEntrenadores(String ubicacion) {
        return 0; //entrenadorDAO.recuperarTodosEn(ubicacion);
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
