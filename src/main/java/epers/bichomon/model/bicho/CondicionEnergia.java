package epers.bichomon.model.bicho;

import javax.persistence.Entity;

@Entity
public class CondicionEnergia extends Condicion {

    private int energia;

    CondicionEnergia(int energia) {
        this.energia = energia;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.getEnergia() >= energia;
    }
}
