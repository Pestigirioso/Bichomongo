package epers.bichomon.model.especie.condicion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class CondicionEdad extends Condicion {

    int dias;

    public CondicionEdad(int dias) {
        this.dias = dias;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.edadMayorA(dias);
    }
}
