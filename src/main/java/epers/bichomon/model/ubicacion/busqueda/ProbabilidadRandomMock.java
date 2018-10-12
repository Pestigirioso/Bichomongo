package epers.bichomon.model.ubicacion.busqueda;

import epers.bichomon.model.IRandom;

public class ProbabilidadRandomMock implements IRandom {
    @Override
    public int getInt(int min, int max) {
        return 50;
    }

    @Override
    public double getDouble(double min, double max) {
        return 1;
    }
}
