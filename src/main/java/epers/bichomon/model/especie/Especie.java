package epers.bichomon.model.especie;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Especie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nombre;

    private int altura;

    private int peso;

    @Enumerated(EnumType.STRING)
    private TipoBicho tipo;

    private int energiaInicial;

    private String urlFoto;

    private int cantidadBichos;

    // TODO a una especie puede evolucionar solo una? o varias? A->C y B->C?
    @OneToOne
    private Especie evolucion;

    private Especie() {
    }

    public Especie(int id, String nombre, TipoBicho tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Especie(int id, String nombre, TipoBicho tipo, int altura, int peso, int energia, String url) {
        this(id, nombre, tipo);
        this.setAltura(altura);
        this.setPeso(peso);
        this.setEnergiaInicial(energia);
        this.setUrlFoto(url);
    }

    public Especie(int id, String nombre, TipoBicho tipo, int altura, int peso, int energia, String url, Especie evolucion) {
        this(id, nombre, tipo, altura, peso, energia, url);
        this.evolucion = evolucion;
    }

    /**
     * @return el nombre de la especie (por ejemplo: Perromon)
     */
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return la altura de todos los bichos de esta especie
     */
    public int getAltura() {
        return this.altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    /**
     * @return el peso de todos los bichos de esta especie
     */
    public int getPeso() {
        return this.peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * @return una url que apunta al un recurso imagen el cual será
     * utilizado para mostrar un thumbnail del bichomon por el frontend.
     */
    public String getUrlFoto() {
        return this.urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    /**
     * @return la cantidad de energia de poder iniciales para los bichos
     * de esta especie.
     */
    public int getEnergiaInicial() {
        return this.energiaInicial;
    }

    public void setEnergiaInicial(int energiaInicial) {
        this.energiaInicial = energiaInicial;
    }

    /**
     * @return el tipo de todos los bichos de esta especie
     */
    public TipoBicho getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoBicho tipo) {
        this.tipo = tipo;
    }

    /**
     * @return la cantidad de bichos que se han creado para esta
     * especie.
     */
    public int getCantidadBichos() {
        return this.cantidadBichos;
    }

    public void setCantidadBichos(int i) {
        this.cantidadBichos = i;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bicho crearBicho() {
        this.cantidadBichos++;
        return new Bicho(this, new ArrayList());
    }

}
