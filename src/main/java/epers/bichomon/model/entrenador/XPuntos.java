package epers.bichomon.model.entrenador;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class XPuntos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int combatir = 10;
    private int capturar = 10;
    private int evolucionar = 5;

    public XPuntos() {
    }

    public int getCombatir() {
        return combatir;
    }

    public int getCapturar() {
        return capturar;
    }

    public int getEvolucionar() {
        return evolucionar;
    }

}
