package epers.bichomon.service.test;

import epers.bichomon.dao.hibernate.GenericDAOHib;
import epers.bichomon.service.runner.Runner;

import java.io.Serializable;

public class TestServiceImpl extends GenericDAOHib implements TestService {

    public void crearEntidad(Object object) {
        Runner.runInSession(() -> {
            super.guardar(object);
            return null;
        });
    }

    public void actualizar(Object object) {
        Runner.runInSession(() -> {
            super.actualizar(object);
            return null;
        });
    }

    public <T> T recuperar(Class<T> tipo, Serializable key) {
        return Runner.runInSession(() -> super.recuperar(tipo, key));
    }

    public <T> T recuperarBy(Class<T> tipo, String param, Serializable value) {
        return Runner.runInSession(() -> super.recuperarBy(tipo, param, value));
    }

    public <T> T recuperarByName(Class<T> tipo, String name) {
        return Runner.runInSession(() -> super.recuperarByName(tipo, name));
    }

    public <T> void borrar(Class<T> tipo, Serializable key) {
        Runner.runInSession(() -> {
            super.borrar(tipo, key);
            return null;
        });
    }

    public <T> void borrarByName(Class<T> tipo, String nombre) {
        Runner.runInSession(() -> {
            super.borrarByName(tipo, nombre);
            return null;
        });
    }
}