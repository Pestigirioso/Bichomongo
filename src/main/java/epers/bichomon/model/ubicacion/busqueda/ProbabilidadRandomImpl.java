package epers.bichomon.model.ubicacion.busqueda;

import java.util.Random;

public class ProbabilidadRandomImpl implements ProbabilidadRandom {
    @Override
    public int getInt(int max) {
        return new Random().nextInt(max);
    }
}
