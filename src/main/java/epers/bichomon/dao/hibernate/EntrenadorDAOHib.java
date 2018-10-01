package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.service.runner.Runner;
import org.hibernate.query.Query;

public class EntrenadorDAOHib extends GenericDAOHib implements EntrenadorDAO {
    @Override
    public void actualizar(Entrenador entrenador) {
        super.actualizar(entrenador);
    }

    @Override
    public Entrenador recuperar(String nombreEntrenador) {
        return super.recuperarByName(Entrenador.class, nombreEntrenador);
    }

    @Override
    public Integer cantidadEntrenadores(String ubicacion) {
        String hq1 = "select count(*) from Entrenador i where i.ubicacion.nombre = :ubi";
        Query query = Runner.getCurrentSession().createQuery(hq1);
        query.setParameter("ubi", ubicacion);
        return ((Long) query.uniqueResult()).intValue();
    }
}
