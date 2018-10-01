package epers.bichomon.model.entrenador;

public class BichoIncorrectoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BichoIncorrectoException(Integer id) {
        super("Entrenador no tiene Bicho [" + id.toString() + "]");
    }
}
