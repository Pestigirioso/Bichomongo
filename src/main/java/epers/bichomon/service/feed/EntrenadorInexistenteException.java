package epers.bichomon.service.feed;

public class EntrenadorInexistenteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    EntrenadorInexistenteException(String entrenador) {
        super("No se existe el entrenador [" + entrenador + "]");
    }
}
