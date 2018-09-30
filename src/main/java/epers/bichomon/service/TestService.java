package epers.bichomon.service;

import epers.bichomon.dao.hibernate.GenericDAOHib;
import epers.bichomon.service.runner.Runner;

import java.io.Serializable;

public class TestService extends GenericDAOHib {

    public void crearEntidad(Object object) {
        Runner.runInSession(() -> {
            super.guardar(object);
            return null;
        });
    }

    public <T> T recuperar(Class<T> tipo, Serializable key) {
        return Runner.runInSession(() -> super.recuperar(tipo, key));
    }

    public <T> T recuperarByName(Class<T> tipo, String name) {
        return Runner.runInSession(() -> super.recuperarByName(tipo, name));
    }

    public <T> void borrarByName(Class<T> tipo, String nombre) {
        Runner.runInSession(() -> {
            super.borrarByName(tipo, nombre);
            return null;
        });
    }
}