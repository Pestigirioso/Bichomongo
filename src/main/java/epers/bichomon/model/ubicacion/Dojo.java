package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.duelo.Campeon;
import epers.bichomon.model.ubicacion.duelo.Duelo;
import epers.bichomon.model.ubicacion.duelo.ResultadoCombate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Dojo extends Ubicacion {
    /**
     * Los dojos son ubicaciones especiales de combate.
     * Los dojos pueden poseer un campeon (un Bicho específico de un Entrenador específico).
     * Un entrenador (que no sea el campeon actual del Dojo) podrá retar a duelo al campeon en esta localización.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Campeon campeon;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Campeon> campeones = new HashSet<>();

    protected Dojo() {
        super();
    }

    public Dojo(String nombreDojo) {
        super(nombreDojo);
    }

    public Dojo(String nombre, Bicho campeon) {
        this(nombre);
        this.campeon = new Campeon(campeon);
    }

    @Override
    protected Bicho buscarBicho(Entrenador entrenador) {
        if (campeon == null) return null;
        return campeon.getCampeon().getRaiz().crearBicho();
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

    @Override
    public ResultadoCombate duelo(Bicho bicho) {
        if (campeon != null && campeon.getCampeon().equals(bicho)) return null;
        ResultadoCombate resultado = new Duelo(campeon, bicho).getResultado();
        nuevoCampeon(resultado.getGanador());
        return resultado;
    }

    private void nuevoCampeon(Bicho ganador) {
        if (campeon != null) {
            if (campeon.getCampeon().equals(ganador)) return;
            campeon.derrotado();
            campeones.add(campeon);
        }
        campeon = new Campeon(ganador);
    }

    public Bicho getCampeon() {
        return campeon.getCampeon();
    }
}
