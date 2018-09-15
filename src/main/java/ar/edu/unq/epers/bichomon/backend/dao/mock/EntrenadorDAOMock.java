package ar.edu.unq.epers.bichomon.backend.dao.mock;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.HashMap;
import java.util.Map;

public class EntrenadorDAOMock implements EntrenadorDAO {

    private static Map<String, Entrenador> DATA = new HashMap<>();

    static{
        Entrenador alex = new Entrenador("Alex");
        DATA.put(alex.getNombre(), alex);

        Entrenador magali = new Entrenador("Magali");
        DATA.put(magali.getNombre(),magali);

        Entrenador paco = new Entrenador("Paco");
        DATA.put(paco.getNombre(),paco);
    }

    @Override
    public Entrenador recuperar(String nombreEntrenador) {
        return DATA.get(nombreEntrenador);
    }

    @Override
    public void actualizar(Entrenador entrenador) {
        DATA.put(entrenador.getNombre(), entrenador);
    }
}
