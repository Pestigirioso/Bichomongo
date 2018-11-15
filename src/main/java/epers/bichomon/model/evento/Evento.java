package epers.bichomon.model.evento;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Evento {
    @MongoId
    @MongoObjectId
    private String id;

    private String entrenador;

    private String ubicacion;

    private Date fecha = new Date();

    private TipoEvento tipoEvento;

    protected Evento() {}

    public Evento(String entrenador, String ubicacion, TipoEvento tipo) {
        this.entrenador = entrenador;
        this.ubicacion = ubicacion;
        this.tipoEvento = tipo;
    }
//
//    public Evento(String entrenador, String ubicacion, TipoEvento tipo, Date fecha){
//        this(entrenador, ubicacion, tipo);
//        this.fecha = fecha;
//    }

    public String getEntrenador() {
        return entrenador;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }
}
