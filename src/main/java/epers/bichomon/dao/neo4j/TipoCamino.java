package epers.bichomon.dao.neo4j;

public enum TipoCamino {
    Terrestre(1),
    Maritimo(2),
    Aereo(5);

    private final int costo;

    TipoCamino(int costo) {
        this.costo = costo;
    }

    public int getCosto() {
        return costo;
    }
}
