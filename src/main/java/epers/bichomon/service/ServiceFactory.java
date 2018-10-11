package epers.bichomon.service;

import epers.bichomon.dao.hibernate.EntrenadorDAOHib;
import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.dao.hibernate.GenericDAOHib;
import epers.bichomon.dao.hibernate.UbicacionDAOHib;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.bicho.BichoServiceImpl;
import epers.bichomon.service.especie.EspecieService;
import epers.bichomon.service.especie.EspecieServiceImpl;
import epers.bichomon.service.leaderboard.LeaderboardService;
import epers.bichomon.service.leaderboard.LeaderboardServiceImpl;
import epers.bichomon.service.mapa.MapaService;
import epers.bichomon.service.mapa.MapaServiceImpl;
import epers.bichomon.service.test.TestService;
import epers.bichomon.service.test.TestServiceImpl;

public class ServiceFactory {
    public static TestService getTestService() {
        return new TestServiceImpl();
    }

    public static EspecieService getEspecieService() {
        return new EspecieServiceImpl(new EspecieDAOHib());
    }

    public static MapaService getMapService() {
        return new MapaServiceImpl(new UbicacionDAOHib(), new EntrenadorDAOHib());
    }

    public static epers.bichomon.service.bicho.BichoService getBichoService() {
        return new BichoServiceImpl(new EntrenadorDAOHib(), new GenericDAOHib());
    }

    public static LeaderboardService getLeaderboardService() {
        return new LeaderboardServiceImpl(new EntrenadorDAOHib());
    }
}
