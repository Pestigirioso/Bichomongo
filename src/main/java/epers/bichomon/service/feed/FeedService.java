package epers.bichomon.service.feed;

import epers.bichomon.model.evento.Evento;

import java.util.List;

public interface FeedService {

    /** Devolverá la lista de eventos que involucren al entrenador provisto.
     *
     * Esa lista incluirá eventos relacionados a:
     *  -   Todos los viajes que haya hecho el entrenador (arribos),
     *  -   Todas las capturas
     *  -   Todos los bichos que haya abandonado
     *  -   Todas las coronaciones en las que haya sido coronado o destronado.
     *
     *  Dicha lista debe contener primero a los eventos más recientes. */
    List<Evento> feedEntrenador(String entrenador);

    /** Devolverá el feed de eventos principal que debe mostrarse al usuario.
     * El mismo deberá incluír todas los eventos de :
     *  -   Su ubicación actual
     *  -   Ubicaciones que estén conectadas con la actual
     *
     *  Dicha lista debe contener primero a los eventos más recientes. */
    List<Evento> feedUbicacion(String entrenador);
}
