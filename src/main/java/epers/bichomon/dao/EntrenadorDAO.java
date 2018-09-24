package epers.bichomon.dao;

import epers.bichomon.model.entrenador.Entrenador;

public interface EntrenadorDAO {
    void actualizar(Entrenador entrenador);

    Entrenador recuperar(String nombreEntrenador);

    Integer cantidadEntrenadores(String ubicacion);
}
