package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.duelo.Duelo;
import epers.bichomon.model.ubicacion.duelo.ResultadoCombate;

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
    protected Bicho buscarBicho(Entrenador entrenador) {
        if (campeon == null) return null;
        return campeon.getRaiz().crearBicho();
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

    /**
     * Será necesario guardar de alguna forma el historial de campeones para cada Dojo
     * con las fechas en las que fue coronado campeon y luego depuesto.
     */

    // TODO implementar historial de campeones
    @Override
    public ResultadoCombate duelo(Bicho bicho) {
        ResultadoCombate resultado = new Duelo(campeon, bicho).getResultado();
        if (campeon != resultado.getGanador())
            nuevoCampeon(resultado.getGanador());
        return resultado;
    }

    private void nuevoCampeon(Bicho ganador) {
        campeon = ganador;
        // TODO generar historial de campeones
    }

    public Bicho getCampeon() {
        return campeon;
    }
}
