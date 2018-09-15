package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.ArrayList;

public interface EntrenadorDAO {
    Entrenador recuperar(String nombreEntrenador);

    void actualizar(Entrenador entrenador);

    ArrayList<Entrenador> recuperarTodosEn(String ubicacion);
}
