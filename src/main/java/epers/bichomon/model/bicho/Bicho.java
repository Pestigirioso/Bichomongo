package epers.bichomon.model.bicho;

import epers.bichomon.model.IRandom;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.ubicacion.UbicacionFactory;

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

    @Transient
    private IRandom rnd = UbicacionFactory.getDueloRandom();

    protected Bicho() {
    }

    public Bicho(Especie especie) {
        this.especie = especie;
        this.energia = especie.getEnergiaInicial();
//        this.entrenadoresAnteriores = new HashSet<>();
    }

    //--------------> Constructor para los test

    //-------> Para test de fecha de captura
    public Bicho(Especie especie, Entrenador trainer, LocalDate fechaDeCaptura) {
        this(especie);
        this.entrenador = trainer;
        this.fechaCaptura = fechaDeCaptura;
    }

    //-------> Para test condicion nivel
    public Bicho(Especie especie, Entrenador e) {
        this(especie);
        entrenador = e;
    }

    public void capturadoPor(Entrenador entrenador) {
        this.entrenador = entrenador;
        this.fechaCaptura = LocalDate.now();
    }

    public void abandonado() {
        if (entrenador != null)
            entrenadoresAnteriores.add(entrenador);
        entrenador = null;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id == ((Bicho) obj).getID();
    }

    public int getID() {
        return this.id;
    }

    public Especie getEspecie() {
        return this.especie;
    }

    public int getEnergia() {
        return this.energia;
    }

    public void incEnergia(int energia) {
        this.energia += energia;
    }

    public int getVictorias() {
        return this.victorias;
    }

    public LocalDate getFechaCaptura() {
        return this.fechaCaptura;
    }

    public Boolean puedeEvolucionar() {
        return especie.puedeEvolucionar(this);
    }

    public Boolean tuvisteEntrenador(Entrenador e) {
        return entrenadoresAnteriores.contains(e);
    }

    public Especie getRaiz() {
        return especie.getRaiz();
    }

    public Boolean nivelMayorA(int nivel) {
        return this.entrenador != null && this.entrenador.getNivel() >= nivel;
    }

    public Boolean edadMayorA(int dias) {
        return this.entrenador != null && getFechaCaptura().plusDays(dias).isBefore(LocalDate.now());
    }

    public void evolucionar() {
        if (!this.puedeEvolucionar()) throw new BichoNoEvolucionableException();
        this.especie = this.especie.getEvolucion();
        if (this.entrenador != null) this.entrenador.evolucion();
    }

    public int getDanio() {
        return (int) Math.round(this.energia * rnd.getDouble(0.5, 1));
    }

    public void ganasteDuelo() {
        this.victorias += 1;
        subirEnergiaPorDuelo();
    }

    public void perdisteDuelo() {
        subirEnergiaPorDuelo();
    }

    private void subirEnergiaPorDuelo() {
        incEnergia(rnd.getInt(1, 5));
    }
}
