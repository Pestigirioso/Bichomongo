package epers.bichomon.service.mapa;

import epers.bichomon.model.bicho.Bicho;

public interface MapaService {

    /**
     * se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
     */
    void mover(String entrenador, String ubicacion);

    /**
     * se deberá devolver la cantidad de entrenadores que se encuentren actualmente en dicha localización.
     */
    int cantidadEntrenadores(String ubicacion);

    /**
     * retorna el actual campeon del Dojo especificado.
     */
    Bicho campeon(String dojo);

    /**
     * retorna el bicho que haya sido campeon por mas tiempo en el Dojo.
     */
    Bicho campeonHistorico(String dojo);
}
