package ar.edu.unq.epers.bichomon.backend.model.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class Guarderia extends Ubicacion {

    public Guarderia(String nombreGuarderia){
        super(nombreGuarderia);
    }

    @Override
    public Bicho buscar() {
        return null;
    }

}
