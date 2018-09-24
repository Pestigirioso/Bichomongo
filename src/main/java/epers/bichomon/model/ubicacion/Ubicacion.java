package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

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

    public abstract Bicho buscar();

    public String getNombre() {
        return nombre;
    }
}
