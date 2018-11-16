package epers.bichomon.model.evento;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "_class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventoAbandono.class),
        @JsonSubTypes.Type(value = EventoArribo.class),
        @JsonSubTypes.Type(value = EventoCaptura.class),
        @JsonSubTypes.Type(value = EventoCoronacion.class)
})
public abstract class Evento {
    @MongoId
    @MongoObjectId
    private String id;

    private Date fecha = new Date();

    protected Evento() {}

}
