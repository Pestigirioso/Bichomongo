package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.EspecieDAO;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.service.runner.Runner;

import javax.persistence.NoResultException;
import java.util.List;

public class EspecieDAOHib extends GenericDAOHib implements EspecieDAO {

    @Override
    public void save(Especie especie) {
        super.save(especie);
    }

    @Override
    public void upd(Especie especie) {
        super.upd(especie);
    }

    @Override
    public Especie get(String nombreEspecie) {
        try {
            return super.getByName(Especie.class, nombreEspecie);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Especie> recuperarTodos() {
        String hq1 = "from Especie i order by i.nombre asc";
        return Runner.getCurrentSession().createQuery(hq1, Especie.class).getResultList();
    }

    @Override
    public List<Especie> getPopulares() {
        String hq1 = "select i.especie from Bicho i where i.entrenador <> null group by i.especie order by count(*) desc";
        return Runner.getCurrentSession().createQuery(hq1, Especie.class)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<Especie> getImpopulares() {
        String hq1 = "select i.especie from Bicho i where i.entrenador = null group by i.especie order by count(*) asc";
        return Runner.getCurrentSession().createQuery(hq1, Especie.class)
                .setMaxResults(10)
                .getResultList();
    }

    /**
     * retorna la especie que tenga mas bichos que haya sido campeones de cualquier dojo.
     * Cada bicho deberá ser contado una sola vez
     * (independientemente de si haya sido coronado campeon mas de una vez o en mas de un Dojo)
     */
    @Override
    public Especie lider() {
        String hq1 = "select c.campeon.especie from Campeon c group by c.campeon.especie order by COUNT(DISTINCT c.campeon) desc";
        return Runner.getCurrentSession().createQuery(hq1, Especie.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
