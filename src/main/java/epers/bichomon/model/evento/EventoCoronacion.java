package epers.bichomon.model.evento;

public class EventoCoronacion extends Evento {

    private String coronado;

    private String descoronado;

    private String ubicacion;

    protected EventoCoronacion() {
    }

    public EventoCoronacion(String coronado, String descoronado, String ubicacion) {
        this.coronado = coronado;
        this.descoronado = descoronado;
        this.ubicacion = ubicacion;
    }

    public String getCoronado() {
        return coronado;
    }

    public String getDescoronado() {
        return descoronado;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}
