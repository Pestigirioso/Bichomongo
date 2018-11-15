package epers.bichomon.service.feed;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.EventoDAO;
import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.evento.Evento;
import epers.bichomon.model.ubicacion.Ubicacion;

import java.util.List;
import java.util.stream.Collectors;

public class FeedServiceImpl implements FeedService {

    private EventoDAO eventoDAO;
    private UbicacionDAO ubicacionDAO;
    private EntrenadorDAO entrenadorDAO;

    public FeedServiceImpl(EventoDAO eventoDAO, UbicacionDAO ubicacionDAO, EntrenadorDAO entrenadorDAO) {
        this.eventoDAO = eventoDAO;
        this.ubicacionDAO = ubicacionDAO;
        this.entrenadorDAO = entrenadorDAO;
    }

    /** Devolverá la lista de eventos que involucren al entrenador provisto.
     *
     * Esa lista incluirá eventos relacionados a:
     *  -   Todos los viajes que haya hecho el entrenador (arribos),
     *  -   Todas las capturas
     *  -   Todos los bichos que haya abandonado
     *  -   Todas las coronaciones en las que haya sido coronado o destronado.
     *
     *  Dicha lista debe contener primero a los eventos más recientes. */
    @Override
    public List<Evento> feedEntrenador(String entrenador) {
        return eventoDAO.getByEntrenador(entrenador);
    }

    /** Devolverá el feed de eventos principal que debe mostrarse al usuario.
     * El mismo deberá incluír todas los eventos de :
     *  -   Su ubicación actual
     *  -   Ubicaciones que estén conectadas con la actual
     *
     *  Dicha lista debe contener primero a los eventos más recientes. */
    @Override
    public List<Evento> feedUbicacion(String entrenador) {
        Entrenador e = entrenadorDAO.get(entrenador);
        return eventoDAO.getByUbicaciones(conectadasA(e.getUbicacion()));
    }

    private List<String> conectadasA(Ubicacion ubicacion) {
        List<Ubicacion> ubicaciones = ubicacionDAO.conectados(ubicacion.getNombre());
        ubicaciones.add(ubicacion);
        return ubicaciones.stream().map(Ubicacion::getNombre).collect(Collectors.toList());
    }
}
