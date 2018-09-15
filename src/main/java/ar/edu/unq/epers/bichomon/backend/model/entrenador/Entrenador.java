package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Ubicacion;

// TODO implementar clase Entrenador
public class Entrenador {

    private String nombre;
    private Ubicacion ubicacion;

    public Entrenador(String nombreEntrenador){
        this.nombre = nombreEntrenador;
    }

    public Ubicacion getUbicacion(){
        return ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void moverA(Ubicacion ubicacion) {
        this.ubicacion= ubicacion;
    }
}
