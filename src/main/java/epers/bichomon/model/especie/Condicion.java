package epers.bichomon.model.especie;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Condicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public abstract Boolean puedeEvolucionar(Bicho bicho);
}
