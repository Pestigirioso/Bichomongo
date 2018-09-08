package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class EspecieDAOHibernate implements EspecieDAO {

    @Override
    public void guardar(Especie especie) {
        Session session = Runner.getCurrentSession();
        session.save(especie);
    }

    @Override
    public void actualizar(Especie especie) {
        Session session = Runner.getCurrentSession();
        session.update(especie);
    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        Session session = Runner.getCurrentSession();
        String hq1 = "from Especie i where i.nombre = :nom";
        Query<Especie> query = session.createQuery(hq1, Especie.class);
        query.setParameter("nom", nombreEspecie);
        query.setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Especie> recuperarTodos() {
        Session session = Runner.getCurrentSession();
        String hq1 = "from Especie i order by i.nombre asc";
        Query<Especie> query = session.createQuery(hq1, Especie.class);
        return query.getResultList();
    }

}
