package epers.bichomon.model.bicho;

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
