package epers.bichomon.service.mapa;

public class CaminoMuyCostosoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CaminoMuyCostosoException(String ubicacion) {
        super("No tiene suficiente dinero para llegar a la ubicacion [" + ubicacion + "]");
    }
}
