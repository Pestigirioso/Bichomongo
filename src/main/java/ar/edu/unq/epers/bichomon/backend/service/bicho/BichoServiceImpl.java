package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class BichoServiceImpl implements BichoService {

    //private EspecieDAO especieDAO;

    //public BichoServiceImpl(EspecieDAO dao) { this.especieDAO = dao; }

    @Override
    public Bicho buscar(String entrenador) {
        return null;
    }

    @Override
    public void abandonar(String entrenador, int bicho) {

    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        return null;
    }

    @Override
    public boolean puedeEvolucionar(String entrenador, int bicho) {
        return false;
    }

    @Override
    public Bicho evolucionar(String entrenador, int bicho) {
        return null;
    }
}
