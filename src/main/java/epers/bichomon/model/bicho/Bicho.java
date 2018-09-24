package epers.bichomon.model.bicho;

import epers.bichomon.model.especie.Especie;

import javax.persistence.*;

@Entity
public class Bicho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Especie especie;

    private int energia;

    private Bicho() {
    }

    public Bicho(Especie especie) {
        this.especie = especie;
    }

    /**
     * @return la especie a la que este bicho pertenece.
     */
    public Especie getEspecie() {
        return this.especie;
    }

    /**
     * @return la cantidad de puntos de energia de este bicho en
     * particular. Dicha cantidad crecerá (o decrecerá) conforme
     * a este bicho participe en combates contra otros bichomones.
     */
    public int getEnergia() {
        return this.energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

}
