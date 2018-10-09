package epers.bichomon.model.ubicacion.duelo;

import epers.bichomon.model.bicho.Bicho;

import java.util.ArrayList;
import java.util.List;

public class Duelo {

    private Bicho campeon;
    private Bicho retador;
    private List<Ataque> ataques = new ArrayList<>();
    private int ataqueCampeon = 0;
    private int ataqueRetador = 0;

    public Duelo(Campeon campeon, Bicho retador) {
        if (campeon != null) this.campeon = campeon.getCampeon();
        this.retador = retador;
    }

    public ResultadoCombate getResultado() {
        while (!hayGanador()) {
            ataqueRetador += atacar(retador);
            if (!hayGanador()) ataqueCampeon += atacar(campeon);
        }
        return resultado();
    }

    private int atacar(Bicho atacante) {
        int puntos = atacante.getDanio();
        ataques.add(new Ataque(atacante, puntos));
        return puntos;
    }

    private ResultadoCombate resultado() {
        Bicho ganador = ganoRetador() ? retador : campeon;
        Bicho perdedor = ganoRetador() ? campeon : retador;
        if (ganador != null) ganador.ganasteDuelo();
        if (perdedor != null) perdedor.perdisteDuelo();
        return new ResultadoCombate(ganador, perdedor, ataques);
    }

    private boolean ganoRetador() {
        return campeon == null || ataqueRetador >= campeon.getEnergia();
    }

    private boolean ganoCampeon() {
        return ataqueCampeon >= retador.getEnergia();
    }

    private boolean hayGanador() {
        return (ganoRetador() || ganoCampeon());
    }
}
