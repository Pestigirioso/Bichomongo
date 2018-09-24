package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class Guarderia extends Ubicacion {

    private Guarderia() {
        super();
    }

    public Guarderia(String nombreGuarderia){
        super(nombreGuarderia);
    }

    @Override
    public Bicho buscar() {
        return null;
    }

}
