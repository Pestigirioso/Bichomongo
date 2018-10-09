package epers.bichomon.model.especie.condicion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class CondicionEnergia extends Condicion {

    private int energia;

    protected CondicionEnergia() { super(); }

    CondicionEnergia(int energia) {
        this.energia = energia;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.getEnergia() >= energia;
    }
}
