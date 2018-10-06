package epers.bichomon.model.ubicacion.busqueda;

public class ProbabilidadRandomMock implements ProbabilidadRandom {
    @Override
    public int getInt(int max) {
        return 50;
    }
}
