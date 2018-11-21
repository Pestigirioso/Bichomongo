package epers.bichomon.dao.hibernate;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.runner.Runner;

import java.util.ArrayList;
import java.util.List;

public class UbicacionDAOHib extends GenericDAOHib {

    public void save(Ubicacion ubicacion) {
        super.save(ubicacion);
    }

    public Ubicacion get(String ubicacion) {
        return super.getByName(Ubicacion.class, ubicacion);
    }

    public List<Ubicacion> getByIDs(List<Integer> ids) {
        if (ids.isEmpty()) return new ArrayList<>();
        return Runner.getCurrentSession()
                .createQuery("from Ubicacion u where u.id in (:ids)", Ubicacion.class)
                .setParameterList("ids", ids)
                .getResultList();
    }

    public Dojo getDojo(String dojo) {
        return super.getByName(Dojo.class, dojo);
    }

    public Bicho campeonHistorico(String dojo) {
        String hql = "select c.campeon from Dojo d inner join d.campeones c where d.nombre = :dojo order by DATEDIFF(IFNULL(c.hasta,NOW()),c.desde) desc";
        return Runner.getCurrentSession().createQuery(hql, Bicho.class)
                .setParameter("dojo", dojo)
                .setMaxResults(1)
                .getSingleResult();
    }
}
