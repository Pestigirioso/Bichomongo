package epers.bichomon.model.evento;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

public abstract class Evento {
    @MongoId
    @MongoObjectId
    private String id;

    private String nombreEntrenador;

    private String nombreUbicacion;
}
