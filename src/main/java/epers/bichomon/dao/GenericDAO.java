package epers.bichomon.dao;

import java.io.Serializable;

public interface GenericDAO {
    void guardar(Object object);

    void actualizar(Object object);

    <T> T recuperar(Class<T> tipo, Serializable key);

    <T> T recuperarBy(Class<T> tipo, String param, Serializable value);

    <T> T recuperarByName(Class<T> tipo, String name);

    <T> void borrar(Class<T> tipo, Serializable key);

    <T> void borrarByName(Class<T> tipo, String name);
}
