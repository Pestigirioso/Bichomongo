package epers.bichomon.model.ubicacion;

import epers.bichomon.model.Ataque;
import epers.bichomon.model.ResultadoCombate;
import epers.bichomon.model.bicho.Bicho;

import java.util.ArrayList;
import java.util.List;

public class Duelo {

    private Bicho campeon;
    private Bicho retador;
    private List<Ataque> ataques = new ArrayList<>();
    private int ataqueCampeon = 0;
    private int ataqueRetador = 0;

    public Duelo(Bicho campeon, Bicho retador) {
        this.campeon = campeon;
        this.retador = retador;
    }

    public ResultadoCombate getResultado() {
        if (campeon == null) return new ResultadoCombate(retador, campeon, ataques); // TODO sumar energia
        // TODO Si luego de 10 ataques el retador no logra vencer a su rival, entonces perderá el combate.
        while (noHayGanador()) {
            ataqueRetador += retador.getEnergia(); // TODO Duelo generar random
            ataques.add(new Ataque());
            if (noHayGanador()) {
                ataqueCampeon += campeon.getEnergia(); // TODO Duelo generar random
                ataques.add(new Ataque());
            }
        }
        return resultado(); // TODO sumar energia
    }

    private ResultadoCombate resultado() {
        Bicho ganador = ganoRetador() ? retador : campeon;
        Bicho perdedor = ganoRetador() ? campeon : retador;
        return new ResultadoCombate(ganador, perdedor, ataques);
    }

    private boolean ganoRetador(){ return ataqueRetador >= campeon.getEnergia(); }

    private boolean ganoCampeon(){ return ataqueCampeon >= retador.getEnergia(); }

    private boolean noHayGanador() {
        return (!ganoRetador() && !ganoCampeon());
    }
}
