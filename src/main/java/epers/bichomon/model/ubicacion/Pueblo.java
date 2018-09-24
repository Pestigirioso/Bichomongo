package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class Pueblo extends Ubicacion {

    private Pueblo() {
        super();
    }

    public Pueblo(String nombrePueblo) {
        super (nombrePueblo);
    }

    @Override
    public Bicho buscar() {
        return null;
    }
}
