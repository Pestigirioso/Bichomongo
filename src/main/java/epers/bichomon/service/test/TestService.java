package epers.bichomon.service.test;

import java.io.Serializable;

public interface TestService {

    void crearEntidad(Object object);

    void actualizar(Object object);

    <T> T recuperar(Class<T> tipo, Serializable key);

    <T> T recuperarBy(Class<T> tipo, String param, Serializable value);

    <T> T recuperarByName(Class<T> tipo, String name);

    <T> void borrar(Class<T> tipo, Serializable key);

    <T> void borrarByName(Class<T> tipo, String nombre);
}
