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

    @ManyToMany
    private Set<Entrenador> entrenadoresAnteriores = new HashSet<>();

    private int energia;
    private int victorias;
    private LocalDate fechaCaptura;
    @ManyToMany
    private Set<Entrenador> entrenadoresAnteriores;

    protected Bicho() {
    }

    public Bicho(Especie especie, int energia) {
        this.especie = especie;
        this.energia = energia;
        entrenadoresAnteriores = new HashSet<>();

    public Bicho (Integer id, Especie especie, int energia){
        this(especie, energia);
        this.id = id;
    }

    public void capturadoPor(Entrenador entrenador){
        this.entrenador=entrenador;
        this.fechaCaptura = LocalDate.now();
    }

    public void abandonado(){
        entrenadoresAnteriores.add(entrenador);
        this.entrenador=null;
    }

    public Especie getEspecie() {
        return this.especie;
    }

    public int getEnergia() {
        return this.energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getVictorias() {
        return this.victorias;
    }

    public LocalDate getFechaCaptura() {
        return this.fechaCaptura;
    }

    public int getNivel() {
        return this.entrenador.getNivel();
    }

    public Boolean puedeEvolucionar() {
        return especie.puedeEvolucionar(this);
    }

    public Boolean tuvisteEntrenador(Entrenador e) {
        return entrenadoresAnteriores.contains(e);
    }
}
