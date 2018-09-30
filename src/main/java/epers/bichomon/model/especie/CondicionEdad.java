package epers.bichomon.model.especie;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class CondicionEdad extends Condicion {

    int dias;

    CondicionEdad(int dias) {
        this.dias = dias;
    }

    @Override
    public Boolean puedeEvolucionar(Bicho bicho) {
        return bicho.getFechaCaptura().plusDays(dias).isAfter(LocalDate.now());
    }
}
