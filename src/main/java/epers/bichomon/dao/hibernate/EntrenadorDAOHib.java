package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.service.runner.Runner;
import org.hibernate.query.Query;

import java.util.List;

public class EntrenadorDAOHib extends GenericDAOHib implements EntrenadorDAO {
    @Override
    public void upd(Entrenador entrenador) {
        super.upd(entrenador);
    }

    @Override
    public Entrenador get(String nombreEntrenador) {
        return super.getByName(Entrenador.class, nombreEntrenador);
    }

    @Override
    public Integer cantidadEntrenadores(String ubicacion) {
        String hq1 = "select count(*) from Entrenador i where i.ubicacion.nombre = :ubi";
        Query query = Runner.getCurrentSession().createQuery(hq1);
        query.setParameter("ubi", ubicacion);
        return ((Long) query.uniqueResult()).intValue();
    }

    /**
     * retorna aquellos entrenadores que posean un bicho que actualmente sea campeon de un Dojo,
     * retornando primero aquellos que ocupen el puesto de campeon desde hace mas tiempo.
     */
    @Override
    public List<Entrenador> campeones() {
        String hq1 = "select c.campeon.entrenador from Campeon c where c.hasta is null group by c.campeon.entrenador order by min(c.desde)";
        Query<Entrenador> query = Runner.getCurrentSession().createQuery(hq1, Entrenador.class);
        return query.getResultList();
    }

    /**
     * retorna los diez primeros entrenadores
     * para los cuales el valor de poder combinado de todos sus bichos sea superior.
     */
    @Override
    public List<Entrenador> lideres() {
        String hq1 = "select e from Entrenador e inner join e.bichos b group by e order by sum(b.energia) desc";
        Query<Entrenador> query = Runner.getCurrentSession().createQuery(hq1, Entrenador.class);
        query.setMaxResults(10);
        return query.getResultList();
    }
}
