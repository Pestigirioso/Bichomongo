package epers.bichomon.model.ubicacion;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.Entity;

@Entity
public class Pueblo extends Ubicacion {
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

    private Pueblo() {
        super();
    }

    public Pueblo(String nombrePueblo) {
        super (nombrePueblo);
    }

    @Override
    public Bicho buscar() {
        return null;
    }
}
