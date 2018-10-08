package epers.bichomon.model.ubicacion;

import epers.bichomon.model.IRandom;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class Pueblo extends Ubicacion {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Probabilidad> probabilidades;

    @Transient
    private IRandom random = UbicacionFactory.getProbabilidadRandom();

    /**
     * Son el tipo mas común de ubicación.
     * En las mismas los entrenadores no podrán realizar ninguna otra acción que no sea buscar nuevos bichos.
     */

    /**
     * Al buscar en los Pueblos un entrenador podrá encontrar bichos salvajes de manera aleatoria.
     * Cada pueblo contendrá una lista de especies de bichos que lo habitan, con una probabilidad
     * de ocurrencia propia para cada una de las especies.
     */

    /**
     * Ejemplo: En un pueblo llamado Starland pueden encontrarse
     * bichos de especie Estrellamon, Nubemon y Dragonmon.
     * Las probabilidades para cada uno son 90, 9 y 1,
     * lo que quiere decir que 90 de cada 100 veces que un jugador
     * encuentre un bicho salvaje el mismo será de tipo Estrellamon. *
     */

    protected Pueblo() {
        super();
    }

    public Pueblo(String nombrePueblo) {
        super(nombrePueblo);
    }

    public Pueblo(String nombre, List<Probabilidad> probabilidades) {
        this(nombre);
        this.probabilidades = probabilidades;
    }

    private Especie getEspecie() {
        int nro = random.getInt(1, probabilidades.stream().mapToInt(p -> p.probabilidad).sum());
        int accum = 0;
        for (Probabilidad p : probabilidades) {
            accum += p.probabilidad;
            if (accum > nro)
                return p.especie;
        }
        return null;
    }

    @Override
    protected Bicho buscarBicho(Entrenador entrenador) {
        Especie especie = getEspecie();
        if (especie == null) return null;
        return especie.crearBicho();
    }

}
