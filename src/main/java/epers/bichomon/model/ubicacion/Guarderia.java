package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.swing.text.StyledEditorKit;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Guarderia extends Ubicacion {
    /**
     * Debido a que los entrenadores poseen un máximo de bichos en su inventario de capturados
     * los mismos podrán abandonar aquellos que no deseen utilizar nuevamente en esta ubicación.
     * Un entrenador no podrá quedarse sin bichos como consecuencia de abandonar.
     */

    /**
     * Al buscar en esta ubicación un entrenador adoptará bichos que hayan sido abandonados
     * anteriormente por algún entrenador distinto a el mismo.
     */

    // TODO agregar atributos a la cardinalidad en todas las clases (mappedBy, cascade, fetch)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Bicho> bichos = new HashSet<>();

    protected Guarderia() {
        super();
    }

    public Guarderia(String nombreGuarderia) {
        super(nombreGuarderia);
    }

    @Override
    public void abandonar(Bicho bicho) {
        bichos.add(bicho);
    }

    @Override
    protected Bicho buscarBicho(Entrenador entrenador) {
        Bicho bicho = bichos.stream().filter(b -> !b.tuvisteEntrenador(entrenador)).collect(Collectors.toList()).get(0);
        bichos.remove(bicho);
        return bicho;
    }

    public boolean contains(Bicho bicho) {
        return bichos.stream().anyMatch(b -> b.equals(bicho));
    }
}
