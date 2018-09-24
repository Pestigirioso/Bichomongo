package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class Dojo extends Ubicacion {

    private Dojo() {
        super();
    }

    public Dojo (String nombreDojo){
        super(nombreDojo);
    }

    @Override
    public Bicho buscar() {
        return null;
    }
}
