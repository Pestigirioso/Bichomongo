package epers.bichomon.model.ubicacion;

import epers.bichomon.model.ResultadoCombate;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

    public Dojo(String nombre, Bicho campeon) {
        this(nombre);
        this.campeon = campeon;
    }

    @Override
    protected Bicho buscarBicho(Entrenador e) {
        if (campeon == null) return null;
        // TODO crear bicho desde el metodo "crearBicho" de Especie??
        return new Bicho(campeon.getRaiz());
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
    public ResultadoCombate duelo(Bicho bicho) {
        // TODO DOJO - duelo Implementar
        return null;
    }
}
