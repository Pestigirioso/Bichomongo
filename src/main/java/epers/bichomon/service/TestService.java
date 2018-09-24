package epers.bichomon.service;

import epers.bichomon.service.runner.Runner;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;

public class TestService {

    public void crearEntidad(Object object) {
        Runner.runInSession(() -> {
            Runner.getCurrentSession().save(object);
            return null;
        });
    }

    public <T> T recuperarEntidad(Class<T> tipo, Serializable key) {
        return Runner.runInSession(() -> Runner.getCurrentSession().get(tipo, key));
    }

    public <T> T recuperarByName(Class<T> tipo, String name) {
        return Runner.runInSession(() -> {
            Session session = Runner.getCurrentSession();
            String hq1 = String.format("from %s i where i.nombre = :nom", tipo.getName());
            Query<T> query = session.createQuery(hq1, tipo);
            query.setParameter("nom", name);
            query.setMaxResults(1);
            return query.getSingleResult();
        });
    }
}