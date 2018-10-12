package epers.bichomon.model.ubicacion.busqueda;

import epers.bichomon.model.entrenador.Entrenador;

public interface ProbabilidadBusqueda {
    /**
     * La probabilidad que una busqueda de bichos en un cualquier localización
     * resulte exitosa se resolverá de la siguiente forma.
     * <p>
     * - se incrementará conforme a la cantidad de tiempo que haya pasado desde
     * la ultima vez que el entrenador encontró un bicho salvaje.
     * - decrecerá conforme al nivel del entrenador actual aumente.
     * - en los Pueblos decrecerá conforme a la cantidad de entrenadores en esa ubicación aumente.
     * <p>
     * Esto se traduce a:
     * busquedaExitosa = factorTiempo * factorNivel * factorPoblacion * random(0, 1) > 0.5
     */
    Boolean exitosa(Entrenador entrenador);
}
