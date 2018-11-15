package epers.bichomon.service;

import epers.bichomon.dao.EventoDAO;
import epers.bichomon.dao.UbicacionDAO;
import epers.bichomon.dao.UbicacionDAOImpl;
import epers.bichomon.dao.hibernate.EntrenadorDAOHib;
import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.dao.hibernate.GenericDAOHib;
import epers.bichomon.dao.hibernate.UbicacionDAOHib;
import epers.bichomon.dao.mongodb.EventoDAOMongo;
import epers.bichomon.dao.neo4j.UbicacionDAONeo4j;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.bicho.BichoServiceImpl;
import epers.bichomon.service.especie.EspecieService;
import epers.bichomon.service.especie.EspecieServiceImpl;
import epers.bichomon.service.feed.FeedService;
import epers.bichomon.service.feed.FeedServiceImpl;
import epers.bichomon.service.leaderboard.LeaderboardService;
import epers.bichomon.service.leaderboard.LeaderboardServiceImpl;
import epers.bichomon.service.mapa.MapaService;
import epers.bichomon.service.mapa.MapaServiceImpl;
import epers.bichomon.service.test.TestService;
import epers.bichomon.service.test.TestServiceImpl;

public enum ServiceFactory {
    INSTANCE;

    private UbicacionDAO getUbicacionDAO() {
        return new UbicacionDAOImpl(new UbicacionDAOHib(), new UbicacionDAONeo4j());
    }

    public TestService getTestService() {
        return new TestServiceImpl();
    }

    public EspecieService getEspecieService() {
        return new EspecieServiceImpl(new EspecieDAOHib());
    }

    public MapaService getMapService() {
        return new MapaServiceImpl(getUbicacionDAO(), new EntrenadorDAOHib(), new EventoDAOMongo());
    }

    public BichoService getBichoService() {
        return new BichoServiceImpl(new EntrenadorDAOHib(), new GenericDAOHib(), new EventoDAOMongo());
    }

    public LeaderboardService getLeaderboardService() {
        return new LeaderboardServiceImpl(new EntrenadorDAOHib(), new EspecieDAOHib());
    }

    public FeedService getFeedService() {
        return new FeedServiceImpl(new EventoDAOMongo(), getUbicacionDAO(), new EntrenadorDAOHib());
    }
}
