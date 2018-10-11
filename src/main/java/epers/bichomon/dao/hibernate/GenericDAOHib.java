package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.GenericDAO;
import epers.bichomon.service.runner.Runner;
import org.hibernate.query.Query;

import java.io.Serializable;

public class GenericDAOHib implements GenericDAO {
    @Override
    public void guardar(Object object) {
        Runner.getCurrentSession().save(object);
    }

    @Override
    public void actualizar(Object object) {
        Runner.getCurrentSession().update(object);
    }

    @Override
    public <T> T recuperar(Class<T> tipo, Serializable key) {
        return Runner.getCurrentSession().get(tipo, key);
    }

    @Override
    public <T> T recuperarBy(Class<T> tipo, String param, Serializable value) {
        String hq1 = String.format("from %s i where i.%s = :val", tipo.getName(), param);
        Query<T> query = Runner.getCurrentSession().createQuery(hq1, tipo);
        query.setParameter("val", value);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public <T> T recuperarByName(Class<T> tipo, String name) {
        return this.recuperarBy(tipo, "nombre", name);
    }

    @Override
    public <T> void borrar(Class<T> tipo, Serializable key) {
        Runner.getCurrentSession().delete(this.recuperar(tipo, key));
    }

    @Override
    public <T> void borrarByName(Class<T> tipo, String name) {
        Runner.getCurrentSession().delete(this.recuperarByName(tipo, name));
    }
}
