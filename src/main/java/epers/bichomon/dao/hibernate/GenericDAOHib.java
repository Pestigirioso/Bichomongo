package epers.bichomon.dao.hibernate;

import epers.bichomon.dao.GenericDAO;
import epers.bichomon.service.runner.Runner;
import org.hibernate.Session;
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

    // TODO crear metodo privado para ejecutar query

    @Override
    public <T> T recuperarByName(Class<T> tipo, String name) {
        Session session = Runner.getCurrentSession();
        String hq1 = String.format("from %s i where i.nombre = :nom", tipo.getName());
        Query<T> query = session.createQuery(hq1, tipo);
        query.setParameter("nom", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public <T> void borrar(Class<T> tipo, Serializable key) {
        Session session = Runner.getCurrentSession();
        String hq1 = String.format("delete %s where id = :id", tipo.getName());
        Query query = session.createQuery(hq1);
        query.setParameter("id", key);
        query.executeUpdate();
    }

    @Override
    public <T> void borrarByName(Class<T> tipo, String name) {
        Session session = Runner.getCurrentSession();
        String hq1 = String.format("delete %s where nombre = :nom", tipo.getName());
        Query query = session.createQuery(hq1);
        query.setParameter("nom", name);
        query.executeUpdate();
    }
}
