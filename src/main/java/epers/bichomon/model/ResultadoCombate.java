package epers.bichomon.model;

import epers.bichomon.model.bicho.Bicho;

import java.util.List;

public class ResultadoCombate {

    private List<Ataque> ataques;
    private Bicho ganador;
    private Bicho perdedor;

    public ResultadoCombate(Bicho ganador, Bicho perdedor, List<Ataque> ataques){
        this.ganador = ganador;
        this.perdedor = perdedor;
        this.ataques = ataques;
    }

    public Bicho getGanador() {
        return ganador;
    }

    public Bicho getPerdedor(){
        return perdedor;
    }
}
