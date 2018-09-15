package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface MapaService {

//     * se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
//
//     * @param entrenador
//     * @param ubicacion

    void mover(String entrenador, String ubicacion);


    /**
     * se deberá devolver la cantidad de entrenadores que se encuentren actualmente en dicha localización.
     *
     * @param ubicacion
     * @return
     */
    int cantidadEntrenadores(String ubicacion);

//------------------ Los siguientes son bonus, y se deben resolver con querys ------------------\\
    /**
     * retorna el actual campeon del Dojo especificado.
     *
     * @param dojo
     * @return
     */
    Bicho campeon(String dojo);


    /**
     * retorna el bicho que haya sido campeon por mas tiempo en el Dojo.
     *
     * @param dojo
     * @return
     */
    Bicho campeonHistorico(String dojo);

}
