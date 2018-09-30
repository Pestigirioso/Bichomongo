package epers.bichomon.model.entrenador;

import javax.persistence.*;

@Entity
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer limite;

    private Nivel next = null;

    private Integer rangoMax;

    private Nivel(){}

    public Nivel(Integer id, Integer limite, Integer rangoMax, Nivel next){
        this.id = id;
        this.limite = limite;
        this.rangoMax = rangoMax;
        this.next = next;
    }

    public Integer getNro(Entrenador e){
        if (e.getXP() > this.limite){
            return this.getNext().getNro(e);
        }
        return this.id;
    }

    private Nivel getNext(){
        if (this.next == null){
            this.next = new Nivel(this.id+1, this.limite+this.rangoMax, this.rangoMax, null);
        }
        return this.next;
    }

//    static{
//        Integer rango = 1000;
//        new Nivel(1, 99, rango, new Nivel(2, 189, rango, new Nivel(3, 1241, rango, null)));
//    }
}