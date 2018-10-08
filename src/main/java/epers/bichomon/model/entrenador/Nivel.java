package epers.bichomon.model.entrenador;

import javax.persistence.*;

@Entity
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private Integer nro;

    private Integer limite;

    @OneToOne(cascade = CascadeType.ALL)
    private Nivel next;

    private Integer rangoMax;

    /**
     * La cantidad de bichos capturados no podrá superar un número máximo
     * preestablecido en función de cada nivel. De haberse llegado a dicho
     * máximo el jugador no podrá buscar nuevos Bichos para capturar.
     */
    private int cantMax;

    protected Nivel() {
    }

    public Nivel(Integer nro, Integer limite, Nivel next) {
        this(nro, limite, 0);
        this.next = next;
    }

    public Nivel(Integer nro, Integer limite, Integer rangoMax) {
        this.nro = nro;
        this.limite = limite;
        this.rangoMax = rangoMax;
        this.cantMax = nro * 3;
    }

    public void subeNivel(Entrenador entrenador) {
        Integer maxNivel = 10;
        if (this.nro < maxNivel && entrenador.getXP() > this.limite) {
            entrenador.setNivel(this.getNext());
            this.getNext().subeNivel(entrenador);
        }
    }

    public Integer getCantMax() {
        return this.cantMax;
    }

    public Integer getNro() {
        return this.nro;
    }

    private Nivel getNext() {
        if (this.next == null) {
            this.next = new Nivel(this.nro + 1, this.limite + this.rangoMax, this.rangoMax);
        }
        return this.next;
    }

    /**
     * Nivel 1: de 1 a 99 puntos de experiencia
     * Nivel 2: de 100 a 400 puntos de experiencia
     * Nivel 3: de 400 a 1000 puntos de experiencia
     * Niveles de 4 a 10: suben cada 1000 puntos de experiencia
     * Nos destacan la importancia de que dichos limites puedan ser modificados sin necesidad
     * de modificar código de la aplicación ya que es posible que los valores cambien a medida
     * que se tenga información real sobre el uso que los jugadores daran al juego.
     */
    public static Nivel create() {
        Integer rango = 1000;
        return new Nivel(1, 99, new Nivel(2, 400, new Nivel(3, 1000, rango)));
    }
}