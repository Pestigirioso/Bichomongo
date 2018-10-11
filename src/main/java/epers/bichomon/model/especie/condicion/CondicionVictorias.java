package epers.bichomon.model.especie.condicion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class CondicionVictorias extends Condicion {
    private int victorias;

    protected CondicionVictorias() { super(); }

    public CondicionVictorias(int victorias) {
        this.victorias = victorias;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.getVictorias() >= victorias;
    }
}
