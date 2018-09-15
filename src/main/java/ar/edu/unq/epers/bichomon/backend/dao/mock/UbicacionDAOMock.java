package ar.edu.unq.epers.bichomon.backend.dao.mock;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.Ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.HashMap;
import java.util.Map;

public class UbicacionDAOMock implements UbicacionDAO {

    private static Map<String, Ubicacion> DATA = new HashMap<>();

    static{
        Ubicacion pueblo = new Pueblo("Pueblo Paleta");
        DATA.put(pueblo.getNombre(), pueblo);

        Ubicacion guarderia = new Guarderia("Guarderia Bicho Feliz");
        DATA.put(guarderia.getNombre(),guarderia);

        Ubicacion dojo = new Dojo("Escuela de la vida");
        DATA.put(dojo.getNombre(), dojo);
    }

    @Override
    public Ubicacion recuperar(String nuevaUbicacion) {
        return DATA.get(nuevaUbicacion);
    }
}
