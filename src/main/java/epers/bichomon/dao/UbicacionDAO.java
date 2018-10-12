package epers.bichomon.dao;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Ubicacion;

public interface UbicacionDAO {
    Ubicacion get(String ubicacion);

    Dojo getDojo(String dojo);

    Bicho campeonHistorico(String dojo);
}
