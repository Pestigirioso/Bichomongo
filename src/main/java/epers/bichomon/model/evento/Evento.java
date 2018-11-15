package epers.bichomon.model.evento;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.time.LocalDateTime;

public abstract class Evento {
    @MongoId
    @MongoObjectId
    private String id;

    private String entrenador;

    private String ubicacion;

    private LocalDateTime fecha = LocalDateTime.now();

    protected Evento() {}

    public Evento(String entrenador, String ubicacion) {
        this.entrenador = entrenador;
        this.ubicacion = ubicacion;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public String getUbicacion() {
        return ubicacion;
    }

}
