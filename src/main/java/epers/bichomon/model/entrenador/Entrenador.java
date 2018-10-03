package epers.bichomon.model.entrenador;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Ubicacion;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nombre;

    @ManyToOne
    private Ubicacion ubicacion;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Bicho> bichos = new HashSet<>();

    private int xp = 1;

    // TODO entrenador - nivel
    @ManyToOne
    private Nivel nivel;

    // TODO implementar Entrenador - Nivel
    /*
     * Nivel 1: de 1 a 99 puntos de experiencia
     * Nivel 2: de 100 a 400 puntos de experiencia
     * Nivel 3: de 400 a 1000 puntos de experiencia
     * Niveles de 4 a 10: suben cada 1000 puntos de experiencia
     *
     * Nos destacan la importancia de que dichos limites puedan ser modificados sin necesidad
     * de modificar código de la aplicación ya que es posible que los valores cambien a medida
     * que se tenga información real sobre el uso que los jugadores daran al juego.
     */
    public int getNivel() {
        return nivel.getNro(this);
    }

    public Integer getXP() {
        return this.xp;
    }

    // TODO implementar Entrenador - cantidad max de bichos capturados
    /*
     * La cantidad de bichos capturados no podrá superar un número máximo
     * preestablecido en función de cada nivel. De haberse llegado a dicho
     * máximo el jugador no podrá buscar nuevos Bichos para capturar.
     */
    private int cantMax;

    protected Entrenador() {
    }

    public Entrenador(String nombre) {
        this.nombre = nombre;
    }

    public Entrenador(String nombre, Nivel nivel) {
        this(nombre);
        this.nivel = nivel;
    }

    public Entrenador(String nombre, Set<Bicho> bichos) {
        this.nombre = nombre;
        this.bichos = bichos;
        this.bichos.forEach(b -> b.capturadoPor(this));
    }

    public Entrenador(String nombre, Set<Bicho> bichos, Ubicacion ubicacion) {
        this(nombre, bichos);
        this.ubicacion = ubicacion;
    }

    public Entrenador(String nombre, Set<Bicho> bichos, Ubicacion ubicacion, int cantMax) {
        this(nombre, bichos, ubicacion);
        this.cantMax = cantMax;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void moverA(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Bicho buscar() {
        if (this.bichos.size() >= this.cantMax)
            return null;
        Bicho b = this.ubicacion.buscar(this);
        if (b == null) return null;
        bichos.add(b);
        b.capturadoPor(this);
        return b;
    }

    public boolean contains(int bicho) {
        return this.bichos.stream().anyMatch(b -> b.getID() == bicho);
    }

    public void abandonar(Bicho bicho) {
        if (!contains(bicho.getID()) || this.bichos.size() <= 1)
            throw new BichoIncorrectoException(bicho.getID());
        ubicacion.abandonar(bicho);
        bichos.remove(bicho);
        bicho.abandonado();
    }
}
