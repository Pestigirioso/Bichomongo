package epers.bichomon.model.bicho;

import javax.persistence.Entity;

@Entity
public class CondicionNivel extends Condicion {
    private int nivel;

    CondicionNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.getNivel() >= nivel;
    }
}
