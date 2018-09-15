package ar.edu.unq.epers.bichomon.backend.service;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.jdbc.EspecieDAOMySQL;
import ar.edu.unq.epers.bichomon.backend.dao.mock.EntrenadorDAOMock;
import ar.edu.unq.epers.bichomon.backend.dao.mock.EspecieDAOMock;
import ar.edu.unq.epers.bichomon.backend.dao.mock.UbicacionDAOMock;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieService;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieServiceImpl;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImpl;

/**
 * Esta clase es un singleton, el cual sera utilizado por equipo de frontend
 * para hacerse con implementaciones a los servicios.
 * 
 * @author Steve Frontend
 * 
 * TODO: Gente de backend, una vez que tengan las implementaciones de sus
 * servicios propiamente realizadas apunten a ellas en los metodos provistos
 * debajo. Gracias!
 */
public class ServiceFactory {
	
	/**
	 * @return un objeto que implementa {@link EspecieService}
	 */
	public EspecieService getEspecieService() {
		return new EspecieServiceImpl(
				new EspecieDAOMock()
				//new EspecieDAOMySQL()
//				new EspecieDAOHibernate()
		);
	}

	public MapaService getMapService(){
		return new MapaServiceImpl(
		        new UbicacionDAOMock(),
                new EntrenadorDAOMock()
        );
	}
	
	/**
	 * @return un objeto que implementa {@link DataService}
	 */
	public DataService getDataService() {
		return new DataServiceImpl(new EspecieDAOHibernate());
	}

}
