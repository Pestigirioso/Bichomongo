package ar.edu.unq.epers.bichomon.backend.model.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.Set;

public abstract class Ubicacion {

    Set<Entrenador> entrenadorSet;
    Set<Bicho> bichoSet;
    String nombre;

    public Ubicacion(){}

    public Ubicacion (String nombreUbicacion) {
        this.nombre = nombreUbicacion;
    }

    public abstract Bicho buscar();

    public String getNombre() {
        return nombre;
    }
}
