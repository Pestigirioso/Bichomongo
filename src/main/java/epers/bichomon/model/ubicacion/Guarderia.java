package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Guarderia extends Ubicacion {
    /**
     * Debido a que los entrenadores poseen un máximo de bichos en su inventario de capturados
     * los mismos podrán abandonar aquellos que no deseen utilizar nuevamente en esta ubicación.
     * Un entrenador no podrá quedarse sin bichos como consecuencia de abandonar.
     */

    // TODO chequear que el entrenador que abandono no pueda recuperarlo???

    /**
     * Al buscar en esta ubicación un entrenador adoptará bichos que hayan sido abandonados
     * anteriormente por algún entrenador distinto a el mismo.
     */

    // TODO agregar atributos a la cardinalidad en todas las clases (mappedBy, cascade, fetch)
    @OneToMany
    private Set<Bicho> bichos = new HashSet<>();

    private Guarderia() {
        super();
    }

    public Guarderia(String nombreGuarderia) {
        super(nombreGuarderia);
    }

    @Override
    public Bicho buscar() {
        return null;
    }

}
