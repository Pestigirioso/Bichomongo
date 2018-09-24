package epers.bichomon.service.leaderboard;

import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;

import java.util.List;

public interface LeaderboardService {

    /**
     * retorna aquellos entrenadores que posean un bicho que actualmente sea campeon de un Dojo,
     * retornando primero aquellos que ocupen el puesto de campeon desde hace mas tiempo.
     *
     * @return
     */
    List<Entrenador> campeones();


    /**
     * retorna la especie que tenga mas bichos que haya sido campeones de cualquier dojo.
     * Cada bicho deberá ser contando una sola vez
     * (independientemente de si haya sido coronado campeon mas de una vez o en mas de un Dojo)
     *
     * @return
     */
    Especie especieLider();


    /**
     * retorna los diez primeros entrenadores
     * para los cuales el valor de poder combinado de todos sus bichos sea superior.
     *
     * @return
     */
    List<Entrenador> lideres();
}
