package epers.bichomon.model.ubicacion.duelo;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.ubicacion.Dojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Campeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Bicho campeon;

    @ManyToOne
    private Dojo dojo;

    private LocalDate desde;

    private LocalDate hasta;

    protected Campeon() {
    }

    public Campeon(Bicho campeon, Dojo dojo) {
        this.campeon = campeon;
        this.dojo = dojo;
        this.desde = LocalDate.now();
    }

    public Campeon(Bicho campeon, Dojo dojo, LocalDate desde, LocalDate hasta) {
        this(campeon, dojo);
        this.desde = desde;
        this.hasta = hasta;
    }

    public Bicho getCampeon() {
        return campeon;
    }

    public void derrotado() {
        this.hasta = LocalDate.now();
    }
}
