package epers.bichomon.model.entrenador;

import epers.bichomon.model.ubicacion.Ubicacion;

import javax.persistence.*;

@Entity
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nombre;

    @ManyToOne
    private Ubicacion ubicacion;

    private Entrenador() {
    }

    public Entrenador(String nombre) {
        this.nombre = nombre;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void moverA(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}
