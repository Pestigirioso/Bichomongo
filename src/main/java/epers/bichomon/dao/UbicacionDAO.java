package epers.bichomon.dao;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Ubicacion;

public interface UbicacionDAO {
    Ubicacion recuperar(String ubicacion);

    Dojo recuperarDojo(String dojo);

    Bicho campeonHistorico(String dojo);
}
