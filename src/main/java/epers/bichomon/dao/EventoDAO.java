package epers.bichomon.dao;

import epers.bichomon.model.evento.Evento;

import java.util.List;

public interface EventoDAO {
    List<Evento> getByUbicaciones(List<String> ubicaciones);

    List<Evento> getByEntrenador(String entrenador);
}
