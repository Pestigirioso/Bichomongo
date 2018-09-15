package ar.edu.unq.epers.bichomon.backend.model.Ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class Pueblo extends Ubicacion {

    public Pueblo(String nombrePueblo) {
        super (nombrePueblo);
    }

    @Override
    public Bicho buscar() {
        return null;
    }
}
