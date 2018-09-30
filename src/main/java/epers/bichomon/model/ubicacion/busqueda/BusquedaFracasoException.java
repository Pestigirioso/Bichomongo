package epers.bichomon.model.ubicacion.busqueda;

public class BusquedaFracasoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusquedaFracasoException(String ubicacion) {
        super("Busqueda fracasó [" + ubicacion + "]");
    }
}
