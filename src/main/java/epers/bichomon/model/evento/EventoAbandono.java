package epers.bichomon.model.evento;

public class EventoAbandono extends Evento {
    private String entrenador;

    private String ubicacion;

    private String especie;

    protected EventoAbandono() {
    }

    public EventoAbandono(String entrenador, String ubicacion, String especie) {
        this.entrenador = entrenador;
        this.ubicacion = ubicacion;
        this.especie = especie;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getEspecie() {
        return especie;
    }
}
