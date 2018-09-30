package epers.bichomon.model.ubicacion;

public class UbicacionIncorrrectaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    UbicacionIncorrrectaException(String ubicacion) {
        super("Ubicación incorrecta [" + ubicacion + "]");
    }

}

