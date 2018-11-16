package epers.bichomon.model.evento;

public class EventoArribo extends Evento {

    private String entrenador;

    private String origen;

    private String destino;

    protected EventoArribo() {
    }

    public EventoArribo(String entrenador, String origen, String destino) {
        this.entrenador = entrenador;
        this.origen = origen;
        this.destino = destino;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }
}
