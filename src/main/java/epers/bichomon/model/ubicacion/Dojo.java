package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Dojo extends Ubicacion {
    /**
     * Los dojos son ubicaciones especiales de combate.
     * Los dojos pueden poseer un campeon (un Bicho específico de un Entrenador específico).
     * Un entrenador (que no sea el campeon actual del Dojo) podrá retar a duelo al campeon en esta localización.
     */
    @ManyToOne
    private Bicho campeon;


    protected Dojo() {
        super();
    }

    public Dojo(String nombreDojo) {
        super(nombreDojo);
    }

    /**
     * Al buscar en este tipo de ubicación un entrador encontrará bichos de la misma especie
     * que la especie "raiz" del actual campeon del Dojo. Por especie "raiz" nos referimos a
     * aquella que sea la primera en su rama de evolución.
     */

    /**
     * Ejemplo: El dojo CobraKai tiene un Dragonmon como campeon.
     * Si el entrenador MiyaguiSan buscase bichos en este dojo lo unico
     * que encontraría (si es que encontrase algo) serían bichos de tipo Lagartomon.
     */
    @Override
    public Bicho buscar() {
        // TODO implementar Dojo - Buscar
        return null;
    }
}
