package epers.bichomon.model.bicho;

import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Bicho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Especie especie;

    @ManyToOne
    private Entrenador entrenador;

    private int energia;
    private int victorias;
    private LocalDate fechaCaptura;
    @ManyToMany
    private Set<Entrenador> entrenadoresAnteriores;

    protected Bicho() {
    }

    // TODO bicho - esta bien que guarde una lista de condiciones?
    public Bicho(Especie especie) {
        this.especie = especie;
        entrenadoresAnteriores = new HashSet<>();
    }

    public Bicho (Integer id, Especie especie){
        this(especie);
        this.id = id;
    }

    public void capturadoPor(Entrenador entrenador){
        this.entrenador=entrenador;
    }

    public void abandonado(){
        entrenadoresAnteriores.add(entrenador);
        this.entrenador=null;
    }

    Especie getEspecie() {
        return this.especie;
    }

    int getEnergia() {
        return this.energia;
    }

    void setEnergia(int energia) {
        this.energia = energia;
    }

    int getVictorias() {
        return this.victorias;
    }

    LocalDate getFechaCaptura() {
        return this.fechaCaptura;
    }

    int getNivel() {
        return this.entrenador.getNivel();
    }

}
