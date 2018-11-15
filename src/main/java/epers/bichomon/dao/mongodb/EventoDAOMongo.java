package epers.bichomon.dao.mongodb;

import epers.bichomon.dao.EventoDAO;
import epers.bichomon.model.evento.Evento;
import org.jongo.Find;

import java.util.List;

public class EventoDAOMongo extends GenericDAOMongo<Evento> implements EventoDAO {

    public EventoDAOMongo() {
        super(Evento.class);
    }

    private Find findOrderByDateDesc(Find f) {
        return f.sort("{ fecha: -1 }");
    }

    @Override
    public List<Evento> getByUbicaciones(List<String> ubicaciones) {
        return this.find("{ ubicacion: { $in: # }}", this::findOrderByDateDesc, ubicaciones);
    }

    @Override
    public List<Evento> getByEntrenador(String entrenador) {
        return this.find("{ entrenador: # }", this::findOrderByDateDesc, entrenador);
    }
}
