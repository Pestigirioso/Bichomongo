package epers.bichomon.service.mapa;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Ubicacion;

import java.util.List;

public interface MapaService {

    /**
     * Se deberá devolver la cantidad de entrenadores que se encuentren actualmente en dicha localización.
     */
    int cantidadEntrenadores(String ubicacion);

    /**
     * Retorna el actual campeon del Dojo especificado.
     */
    Bicho campeon(String dojo);

    /**
     * Retorna el bicho que haya sido campeon por mas tiempo en el Dojo.
     */
    Bicho campeonHistorico(String dojo);

    /**
     * Se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
     * Optando por el camino mas barato
     */
    void mover(String entrenador, String ubicacion);

    /**
     * Se cambiará al entrenador desde su ubicación actual a la especificada por parametro.
     * Optando por el camino mas corto
     */
    void moverMasCorto(String entrenador, String ubicacion);

    /**
     * Devolver todas las Ubicaciones conectadas directamente a una ubicación
     * provista por medio de un tipo de camino especificado.
     */
    List<Ubicacion> conectados(String ubicacion, String tipoCamino);

    /**
     * Crea una nueva ubicación (la cual es provista por parametro) tanto en hibernate como en neo4j.
     */
    void crearUbicacion(Ubicacion ubicacion);

    /**
     * Conectar dos ubicaciones (se asumen preexistentes) por medio de un tipo de camino.
     */
    void conectar(String ubicacion1, String ubicacion2, String tipoCamino);
}
