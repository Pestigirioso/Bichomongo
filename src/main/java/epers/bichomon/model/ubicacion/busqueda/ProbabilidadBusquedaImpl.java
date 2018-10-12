package epers.bichomon.model.ubicacion.busqueda;

import epers.bichomon.model.IRandom;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.UbicacionFactory;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class ProbabilidadBusquedaImpl implements ProbabilidadBusqueda {

    private IRandom rnd = UbicacionFactory.getProbabilidadRandom();

    /**
     * incrementará conforme a la cantidad de tiempo que haya pasado desde
     * la ultima vez que el entrenador encontró un bicho salvaje.
     */
    private Double factorTiempo(Entrenador entrenador) {
        return Math.min(10, DAYS.between(entrenador.getUltimaCaptura(), LocalDate.now())) / 10.;
    }

    /**
     * decrecerá conforme al nivel del entrenador actual aumente.
     */
    private Double factorNivel(Entrenador entrenador) {
        return 1. / entrenador.getNivel();
    }

    /**
     * en los Pueblos decrecerá conforme a la cantidad de entrenadores en esa ubicación aumente.
     */
    private Double factorPoblacion(Entrenador entrenador) {
        int p = entrenador.getUbicacion().getPoblacion();
        return p == -1 ? 1 : 1. / Math.min(100, p);
    }

    @Override
    public Boolean exitosa(Entrenador entrenador) {
        return factorTiempo(entrenador) * factorNivel(entrenador) * factorPoblacion(entrenador) * rnd.getDouble(0, 1) > 0.5;
    }
}
