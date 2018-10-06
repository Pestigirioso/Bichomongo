package epers.bichomon.model.ubicacion.duelo;

import epers.bichomon.model.bicho.Bicho;

public class Ataque {

    private Bicho atacante;
    private int puntos;

    public Ataque(Bicho atacante, int puntos) {
        this.atacante = atacante;
        this.puntos = puntos;
    }

    public Bicho getAtacante() {
        return atacante;
    }

    public int getPuntos() {
        return puntos;
    }
}
