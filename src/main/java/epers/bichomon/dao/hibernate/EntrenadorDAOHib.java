package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.service.runner.Runner;

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
        return ((Long) Runner.getCurrentSession().createQuery(hq1)
                .setParameter("ubi", ubicacion)
                .uniqueResult()).intValue();
    }

    /**
     * retorna aquellos entrenadores que posean un bicho que actualmente sea campeon de un Dojo,
     * retornando primero aquellos que ocupen el puesto de campeon desde hace mas tiempo.
     */
    @Override
    public List<Entrenador> campeones() {
        String hq1 = "select c.campeon.entrenador from Campeon c where c.hasta is null group by c.campeon.entrenador order by min(c.desde)";
        return Runner.getCurrentSession().createQuery(hq1, Entrenador.class).getResultList();
    }

    /**
     * retorna los diez primeros entrenadores
     * para los cuales el valor de poder combinado de todos sus bichos sea superior.
     */
    @Override
    public List<Entrenador> lideres() {
        String hq1 = "select b.entrenador from Bicho b where b.entrenador is not null group by b.entrenador order by sum(b.energia) desc";
        return Runner.getCurrentSession().createQuery(hq1, Entrenador.class)
                .setMaxResults(10)
                .getResultList();
    }
}
