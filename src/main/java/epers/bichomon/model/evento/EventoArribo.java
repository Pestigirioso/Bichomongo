package epers.bichomon.model.evento;

public class EventoArribo extends Evento {
    /**Un jugador arriba a la localización
     * nombreUbicacion sera la ubicacion a la que llego el jugador
     * el jugador sera representado por nombreEntrenador*/
    public EventoArribo(String entrenador, String ubicacion) {
        super(entrenador, ubicacion);
    }
}
