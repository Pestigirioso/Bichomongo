package epers.bichomon.model.ubicacion.duelo;

import epers.bichomon.model.IRandom;

public class DueloRandomMock implements IRandom {
    @Override
    public int getInt(int min, int max) {
        return 1;
    }

    @Override
    public double getDouble(double min, double max) {
        return 1;
    }
}
