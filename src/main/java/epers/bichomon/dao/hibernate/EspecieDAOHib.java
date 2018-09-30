package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.EspecieDAO;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class EspecieDAOHib extends GenericDAOHib implements EspecieDAO {

    @Override
    public void guardar(Especie especie) {
        super.guardar(especie);
    }

    @Override
    public void actualizar(Especie especie) {
        super.actualizar(especie);
    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        try {
            return super.recuperarByName(Especie.class, nombreEspecie);
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

    @Override
    public List<Especie> getPopulares(){
        Session session = Runner.getCurrentSession();
        String hq1 = "select i.especie from Bicho i where i.entrenador <> null group by i.especie order by count(*) desc";
        Query<Especie> query = session.createQuery(hq1, Especie.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
