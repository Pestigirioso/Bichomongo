package epers.bichomon.model.ubicacion.duelo;

import epers.bichomon.model.bicho.Bicho;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Campeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Bicho campeon;

    private LocalDate desde;

    private LocalDate hasta;

    protected Campeon() {
    }

    public Campeon(Bicho campeon) {
        this.campeon = campeon;
        this.desde = LocalDate.now();
    }

    public Campeon(Bicho campeon, LocalDate desde, LocalDate hasta) {
        this.campeon = campeon;
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
