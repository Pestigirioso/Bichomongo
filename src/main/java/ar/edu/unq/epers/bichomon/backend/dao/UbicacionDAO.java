package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Ubicacion;

public interface UbicacionDAO {
    Ubicacion recuperar(String nuevaUbicacion);
}
