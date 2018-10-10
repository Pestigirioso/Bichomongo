package epers.bichomon.service.leaderboard;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.service.runner.Runner;

import java.util.List;

public class LeaderboardServiceImpl implements LeaderboardService {

    private EntrenadorDAO entrenadorDAO;

    public LeaderboardServiceImpl(EntrenadorDAO entrenadorDAO) {
        this.entrenadorDAO = entrenadorDAO;
    }

    @Override
    public List<Entrenador> campeones() {
        return Runner.runInSession(() -> this.entrenadorDAO.campeones());
    }

    /**
     * retorna la especie que tenga mas bichos que haya sido campeones de cualquier dojo.
     * Cada bicho deberá ser contando una sola vez
     * (independientemente de si haya sido coronado campeon mas de una vez o en mas de un Dojo)
     */
    @Override
    public Especie especieLider() {

        // TODO implementar LeaderboardService - Especie especieLider
        return null;
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
