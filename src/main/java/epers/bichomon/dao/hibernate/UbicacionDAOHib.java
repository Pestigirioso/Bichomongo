package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.runner.Runner;
import org.hibernate.query.Query;

public class UbicacionDAOHib extends GenericDAOHib implements UbicacionDAO {
    @Override
    public Ubicacion recuperar(String ubicacion) {
        return super.recuperarByName(Ubicacion.class, ubicacion);
    }

    @Override
    public Dojo recuperarDojo(String dojo) {
        return super.recuperarByName(Dojo.class, dojo);
    }

    @Override
    public Bicho campeonHistorico(String dojo) {
        String hq1 = "select c.campeon from Campeon c where c.dojo.nombre = :dojo";
        Query<Bicho> query = Runner.getCurrentSession().createQuery(hq1, Bicho.class);
        query.setParameter("dojo", dojo);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
