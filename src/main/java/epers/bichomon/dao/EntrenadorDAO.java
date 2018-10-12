package epers.bichomon.dao;

import epers.bichomon.model.entrenador.Entrenador;

import java.util.List;

public interface EntrenadorDAO {
    void upd(Entrenador entrenador);

    Entrenador get(String nombreEntrenador);

    Integer cantidadEntrenadores(String ubicacion);

    List<Entrenador> campeones();

    List<Entrenador> lideres();
}
