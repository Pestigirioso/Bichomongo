package epers.bichomon.dao;

import epers.bichomon.model.entrenador.Entrenador;

import java.util.List;

public interface EntrenadorDAO {
    void actualizar(Entrenador entrenador);

    Entrenador recuperar(String nombreEntrenador);

    Integer cantidadEntrenadores(String ubicacion);

    List<Entrenador> campeones();

    List<Entrenador> lideres();
}
