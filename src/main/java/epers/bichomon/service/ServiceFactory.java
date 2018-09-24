package epers.bichomon.service;

import epers.bichomon.dao.hibernate.EntrenadorDAOHib;
import epers.bichomon.dao.hibernate.EspecieDAOHib;
import epers.bichomon.dao.hibernate.UbicacionDAOHib;
import epers.bichomon.service.especie.EspecieService;
import epers.bichomon.service.especie.EspecieServiceImpl;
import epers.bichomon.service.mapa.MapaService;
import epers.bichomon.service.mapa.MapaServiceImpl;

public class ServiceFactory {

    public EspecieService getEspecieService() {
        return new EspecieServiceImpl(new EspecieDAOHib());
    }

    public MapaService getMapService() {
        return new MapaServiceImpl(new UbicacionDAOHib(), new EntrenadorDAOHib());
    }
}