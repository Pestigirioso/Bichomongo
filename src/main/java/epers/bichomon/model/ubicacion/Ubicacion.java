package epers.bichomon.model.ubicacion;

import epers.bichomon.model.ResultadoCombate;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;

import javax.persistence.*;

@Entity
public abstract class Ubicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nombre;

//    private Set<Entrenador> entrenadorSet;

//    private Set<Bicho> bichoSet;

    protected Ubicacion() {
    }

    public Ubicacion(String nombreUbicacion) {
        this.nombre = nombreUbicacion;
    }

    public Bicho buscar(Entrenador e){
        //TODO Ubicacion - busquedaExitosa - delegar en una interfaz que encapsulará dicho calculo sin proveer una implementación real para la misma.
        if (!this.busquedaExitosa())
            return null;
        return buscarBicho(e);
    }

    private boolean busquedaExitosa(){
        return true;
    }

    protected abstract Bicho buscarBicho(Entrenador e);

    public void abandonar(Bicho bicho){
        throw new UbicacionIncorrrectaException(nombre);
    }

    public ResultadoCombate duelo(Bicho bicho){
        throw new UbicacionIncorrrectaException(nombre);
    }

    public String getNombre() {
        return nombre;
    }
}
