package epers.bichomon.model.ubicacion.busqueda;

import epers.bichomon.model.entrenador.Entrenador;


public class ProbabilidadBusquedaMock implements ProbabilidadBusqueda {
    @Override
    public Boolean exitosa(Entrenador entrenador) {
        return !entrenador.getNombre().equals("ash");
    }
}
