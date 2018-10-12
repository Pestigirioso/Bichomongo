package epers.bichomon.model.ubicacion;

import epers.bichomon.model.IRandom;
import epers.bichomon.model.ubicacion.busqueda.ProbabilidadBusqueda;
import epers.bichomon.model.ubicacion.busqueda.ProbabilidadBusquedaMock;
import epers.bichomon.model.ubicacion.busqueda.ProbabilidadRandomMock;
import epers.bichomon.model.ubicacion.duelo.DueloRandomMock;

public class UbicacionFactory {

    public static ProbabilidadBusqueda getBusqueda() {
//        return new ProbabilidadBusquedaImpl();
        return new ProbabilidadBusquedaMock();
    }

    public static IRandom getProbabilidadRandom() {
//        return new RandomImpl();
        return new ProbabilidadRandomMock();
    }

    public static IRandom getDueloRandom() {
//        return new RandomImpl();
        return new DueloRandomMock();
    }

}
