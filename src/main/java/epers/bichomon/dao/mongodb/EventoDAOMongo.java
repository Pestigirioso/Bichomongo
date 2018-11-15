package epers.bichomon.dao.mongodb;

import epers.bichomon.dao.EventoDAO;
import epers.bichomon.model.evento.Evento;

import java.util.List;

public class EventoDAOMongo extends GenericDAOMongo<Evento> implements EventoDAO {
    public EventoDAOMongo() {
        super(Evento.class);
    }

    @Override
    public List<Evento> getByUbicaciones(List<String> ubicaciones) {
        return null;
    }

    @Override
    public List<Evento> getByEntrenador(String entrenador) {
        return null;
    }
}
