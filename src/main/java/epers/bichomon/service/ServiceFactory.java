package epers.bichomon.service;

import epers.bichomon.dao.hibernate.EntrenadorDAOHib;
import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.dao.hibernate.UbicacionDAOHib;
import epers.bichomon.service.bicho.BichoService;
import epers.bichomon.service.bicho.BichoServiceImpl;
import epers.bichomon.service.especie.EspecieService;
import epers.bichomon.service.especie.EspecieServiceImpl;
import epers.bichomon.service.mapa.MapaService;
import epers.bichomon.service.mapa.MapaServiceImpl;

public class ServiceFactory {
    public static TestService getTestService() {
        return new TestService();
    }

    public static EspecieService getEspecieService() {
        return new EspecieServiceImpl(new EspecieDAOHib());
    }

    public static MapaService getMapService() {
        return new MapaServiceImpl(new UbicacionDAOHib(), new EntrenadorDAOHib());
    }

    public static BichoService getBichoService() {
        return new BichoServiceImpl(new EntrenadorDAOHib());
    }
}
