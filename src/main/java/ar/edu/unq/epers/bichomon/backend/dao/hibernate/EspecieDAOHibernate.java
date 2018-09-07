package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
        return session.get(Especie.class, nombreEspecie);
    }

    @Override
    public List<Especie> recuperarTodos() {
        Session session = Runner.getCurrentSession();
        String hq1 = "from Especie i order by i.nombre asc";
        Query<Especie> query = session.createQuery(hq1, Especie.class);
        return query.getResultList();
    }

}
