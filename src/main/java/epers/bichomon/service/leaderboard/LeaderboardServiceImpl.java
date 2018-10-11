package epers.bichomon.service.leaderboard;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.EspecieDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.service.runner.Runner;

import java.util.List;

public class LeaderboardServiceImpl implements LeaderboardService {

    private EntrenadorDAO entrenadorDAO;
    private EspecieDAO especieDAO;

    public LeaderboardServiceImpl(EntrenadorDAO entrenadorDAO, EspecieDAO especieDAO) {
        this.entrenadorDAO = entrenadorDAO;
        this.especieDAO = especieDAO;
    }

    @Override
    public List<Entrenador> campeones() {
        return Runner.runInSession(() -> this.entrenadorDAO.campeones());
    }

    @Override
    public Especie especieLider() {
        return Runner.runInSession(() -> this.especieDAO.lider());
    }

    /**
     * retorna los diez primeros entrenadores
     * para los cuales el valor de poder combinado de todos sus bichos sea superior.
     */
    @Override
    public List<Entrenador> lideres() {
        // TODO implementar LeaderboardService - List<Entrenador> lideres
        return null;
    }
}
