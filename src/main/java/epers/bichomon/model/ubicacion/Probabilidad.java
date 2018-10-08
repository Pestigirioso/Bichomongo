package epers.bichomon.model.ubicacion;

import epers.bichomon.model.especie.Especie;

import javax.persistence.*;

@Entity
public class Probabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    public Especie especie;

    public int probabilidad;

    protected Probabilidad() {
    }

    public Probabilidad(Especie especie, int probabilidad) {
        this.especie = especie;
        this.probabilidad = probabilidad;
    }
}
