package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.model.ubicacion.Ubicacion;

public class UbicacionDAOHib extends GenericDAOHib implements UbicacionDAO {
    @Override
    public Ubicacion recuperar(String nuevaUbicacion) {
        return super.recuperarByName(Ubicacion.class, nuevaUbicacion);
    }
}
