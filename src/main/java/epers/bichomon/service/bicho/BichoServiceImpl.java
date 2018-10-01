package epers.bichomon.service.bicho;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.GenericDAO;
import epers.bichomon.model.ResultadoCombate;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.service.runner.Runner;

public class BichoServiceImpl implements BichoService {

    private EntrenadorDAO entrenadorDAO;
    private GenericDAO genericDAO;

    public BichoServiceImpl(EntrenadorDAO entrenadorDAO, GenericDAO genericDAO) {
        this.entrenadorDAO = entrenadorDAO;
        this.genericDAO = genericDAO;
    }

    @Override
    public Bicho buscar(String entrenador) {
        return Runner.runInSession(() -> {
            Entrenador e = entrenadorDAO.recuperar(entrenador);
            return e.buscar();
        });
    }

    @Override
    public void abandonar(String entrenador, int bicho) {
        Runner.runInSession(() -> {
            this.entrenadorDAO.recuperar(entrenador).abandonar(this.genericDAO.recuperar(Bicho.class, bicho));
            return null;
        });
    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        // TODO implementar BichoService - ResultadoCombate duelo
        return null;
    }

    @Override
    public boolean puedeEvolucionar(String entrenador, int bicho) {
        // TODO implementar BichoService - boolean puedeEvolucionar
        return false;
    }

    @Override
    public Bicho evolucionar(String entrenador, int bicho) {
        // TODO implementar BichoService - Bicho evolucionar
        return null;
    }
}
