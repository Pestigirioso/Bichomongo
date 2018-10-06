package epers.bichomon.service.bicho;

public class BichoNoEvolucionableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BichoNoEvolucionableException(){
        super("Este bicho no puede evolucionar");
    }
}
