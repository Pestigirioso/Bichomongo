package ar.edu.unq.epers.bichomon.backend.model.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class Dojo extends Ubicacion {

    public Dojo (String nombreDojo){
        super(nombreDojo);
    }

    @Override
    public Bicho buscar() {
        return null;
    }
}
