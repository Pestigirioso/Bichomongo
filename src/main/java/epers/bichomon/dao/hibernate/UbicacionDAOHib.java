package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UbicacionDAOHib implements UbicacionDAO {
    @Override
    public Ubicacion recuperar(String nuevaUbicacion) {
        Session session = Runner.getCurrentSession();
        String hq1 = "from Ubicacion i where i.nombre = :nom";
        Query<Ubicacion> query = session.createQuery(hq1, Ubicacion.class);
        query.setParameter("nom", nuevaUbicacion);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
