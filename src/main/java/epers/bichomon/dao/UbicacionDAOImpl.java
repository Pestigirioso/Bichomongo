package epers.bichomon.dao;

import epers.bichomon.dao.hibernate.UbicacionDAOHib;
import epers.bichomon.dao.neo4j.UbicacionDAONeo4j;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.TipoCamino;
import epers.bichomon.model.ubicacion.Ubicacion;

import java.util.List;

public class UbicacionDAOImpl implements UbicacionDAO {

    private UbicacionDAOHib ubicacionDAOHib;
    private UbicacionDAONeo4j ubicacionDAONeo4j;

    public UbicacionDAOImpl(UbicacionDAOHib ubicacionDAOHib, UbicacionDAONeo4j ubicacionDAONeo4j) {
        this.ubicacionDAOHib = ubicacionDAOHib;
        this.ubicacionDAONeo4j = ubicacionDAONeo4j;
    }

    @Override
    public Ubicacion get(String ubicacion) {
        return ubicacionDAOHib.get(ubicacion);
    }

    @Override
    public Dojo getDojo(String dojo) {
        return ubicacionDAOHib.getDojo(dojo);
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        return ubicacionDAOHib.campeonHistorico(dojo);
    }

    @Override
    public void save(Ubicacion ubicacion) {
        ubicacionDAOHib.save(ubicacion);
        ubicacionDAONeo4j.save(ubicacion);
    }

    @Override
    public void conectar(String ubicacion1, String ubicacion2, TipoCamino tipoCamino) {
        ubicacionDAONeo4j.saveCamino(ubicacionDAOHib.get(ubicacion1), tipoCamino, ubicacionDAOHib.get(ubicacion2));
    }

    @Override
    public List<Ubicacion> conectados(String ubicacion, TipoCamino tipoCamino) {
        return ubicacionDAOHib.getByIDs(ubicacionDAONeo4j.conectados(ubicacionDAOHib.get(ubicacion), tipoCamino));
    }

    @Override
    public List<Ubicacion> conectados(Ubicacion ubicacion) {
        return ubicacionDAOHib.getByIDs(ubicacionDAONeo4j.conectados(ubicacion));
    }

    @Override
    public boolean existeCamino(Ubicacion desde, Ubicacion hasta) {
        return ubicacionDAONeo4j.existeCamino(desde, hasta);
    }

    @Override
    public int viajeMasBarato(Ubicacion desde, Ubicacion hasta) {
        return ubicacionDAONeo4j.viajeMasBarato(desde, hasta);
    }

    @Override
    public int viajeMasCorto(Ubicacion desde, Ubicacion hasta) {
        return ubicacionDAONeo4j.viajeMasCorto(desde, hasta);
    }
}
