package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.mock.EntrenadorDAOMock;
import ar.edu.unq.epers.bichomon.backend.dao.mock.UbicacionDAOMock;
import ar.edu.unq.epers.bichomon.backend.service.ServiceFactory;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapaServiceTest {

    // TODO implementar un TestService

    private EntrenadorDAO entrenadorDAO = new EntrenadorDAOMock();
    private MapaService service = new MapaServiceImpl(new UbicacionDAOMock(), entrenadorDAO);

    @Test
    public void le_digo_a_un_entrenador_que_se_mueva_y_lo_hace() {
        // Lo muevo a ubicación inicial
        service.mover("Alex", "Pueblo Paleta");
        assertEquals("Pueblo Paleta", entrenadorDAO.recuperar("Alex").getUbicacion().getNombre());
    }

    @Test
    public void le_digo_a_un_entrenador_que_se_mueva_dos_veces_y_lo_hace() {
        // Lo muevo a ubicación inicial
        service.mover("Alex", "Pueblo Paleta");
        service.mover("Alex", "Guarderia Bicho Feliz");
        assertEquals("Guarderia Bicho Feliz", entrenadorDAO.recuperar("Alex").getUbicacion().getNombre());
    }


    @Test
    public void muevo_cinco_entrenadores_al_pueblo_y_hay_3_entrenadores(){
        service.mover("Alex","Pueblo Paleta");
        service.mover("Magali","Pueblo Paleta");
        service.mover("Paco","Pueblo Paleta");
        assertEquals(3, service.cantidadEntrenadores("Pueblo Paleta"));
    }

}
