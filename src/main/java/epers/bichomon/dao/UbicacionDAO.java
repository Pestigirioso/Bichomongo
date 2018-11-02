package epers.bichomon.dao;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Ubicacion;

import java.util.List;

public interface UbicacionDAO {
    Ubicacion get(String ubicacion);

    Dojo getDojo(String dojo);

    Bicho campeonHistorico(String dojo);

    void save(Ubicacion ubicacion);

    void conectar(String ubicacion1, String ubicacion2, String tipoCamino);

    List<Ubicacion> conectados(String ubicacion, String tipoCamino);
}
