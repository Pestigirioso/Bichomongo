package epers.bichomon.service;

import epers.bichomon.dao.UbicacionDAOImpl;
import epers.bichomon.dao.hibernate.EntrenadorDAOHib;
import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.dao.hibernate.GenericDAOHib;
import epers.bichomon.dao.hibernate.UbicacionDAOHib;
import epers.bichomon.dao.neo4j.UbicacionDAONeo4j;
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

public enum ServiceFactory {
    INSTANCE;

    public TestService getTestService() {
        return new TestServiceImpl();
    }

    public EspecieService getEspecieService() {
        return new EspecieServiceImpl(new EspecieDAOHib());
    }

    public MapaService getMapService() {
        return new MapaServiceImpl(new UbicacionDAOImpl(new UbicacionDAOHib(), new UbicacionDAONeo4j()), new EntrenadorDAOHib());
    }

    public BichoService getBichoService() {
        return new BichoServiceImpl(new EntrenadorDAOHib(), new GenericDAOHib());
    }

    public LeaderboardService getLeaderboardService() {
        return new LeaderboardServiceImpl(new EntrenadorDAOHib(), new EspecieDAOHib());
    }
}
