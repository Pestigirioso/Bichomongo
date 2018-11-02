package epers.bichomon.service.mapa;

public class UbicacionMuyLejanaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UbicacionMuyLejanaException(String ubicacion) {
        super("No se puede llegar a la ubicacion [" + ubicacion + "]");
    }
}
