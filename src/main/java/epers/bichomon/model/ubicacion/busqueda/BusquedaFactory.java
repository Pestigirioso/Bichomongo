package epers.bichomon.model.ubicacion.busqueda;

public class BusquedaFactory {
    public static ProbabilidadBusqueda getBusqueda() {
        return new ProbabilidadBusquedaMock();
    }
}
