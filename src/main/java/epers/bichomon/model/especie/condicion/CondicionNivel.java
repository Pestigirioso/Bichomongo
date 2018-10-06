package epers.bichomon.model.especie.condicion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class CondicionNivel extends Condicion {
    private int nivel;

    CondicionNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.nivelMayorA(nivel);
    }
}
