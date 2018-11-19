package epers.bichomon.dao;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.TipoCamino;
import epers.bichomon.model.ubicacion.Ubicacion;

import java.util.List;

public interface UbicacionDAO {
    Ubicacion get(String ubicacion);

    Dojo getDojo(String dojo);

    Bicho campeonHistorico(String dojo);

    void save(Ubicacion ubicacion);

    void conectar(String ubicacion1, String ubicacion2, TipoCamino tipoCamino);

    List<Ubicacion> conectados(String ubicacion, TipoCamino tipoCamino);

    List<Ubicacion> conectados(String ubicacion);

    boolean existeCamino(Ubicacion desde, Ubicacion hasta);

    int viajeMasBarato(Ubicacion desde, Ubicacion hasta);

    int viajeMasCorto(Ubicacion desde, Ubicacion hasta);
}
