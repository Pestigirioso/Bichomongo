package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class EntrenadorDAOHib implements EntrenadorDAO {
    @Override
    public void actualizar(Entrenador entrenador) {
        Runner.getCurrentSession().update(entrenador);
    }

    @Override
    public Entrenador recuperar(String nombreEntrenador) {
        Session session = Runner.getCurrentSession();
        String hq1 = "from Entrenador i where i.nombre = :nom";
        Query<Entrenador> query = session.createQuery(hq1, Entrenador.class);
        query.setParameter("nom", nombreEntrenador);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public Integer cantidadEntrenadores(String ubicacion) {
        Session session = Runner.getCurrentSession();
        String hq1 = "select count(*) from Entrenador i where i.ubicacion.nombre = :ubi";
        Query query = session.createQuery(hq1);
        query.setParameter("ubi", ubicacion);
        return ((Long) query.uniqueResult()).intValue();
    }
}
