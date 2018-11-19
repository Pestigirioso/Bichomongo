package epers.bichomon.model.ubicacion;

public class UbicacionIncorrectaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    UbicacionIncorrectaException(String ubicacion) {
        super("Ubicación incorrecta [" + ubicacion + "]");
    }

}

