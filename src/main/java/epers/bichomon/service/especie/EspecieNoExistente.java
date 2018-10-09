package epers.bichomon.service.especie;

/**
 * Situación excepcional en que una especie buscada no es
 * encontrada.
 */
class EspecieNoExistente extends RuntimeException {

    private static final long serialVersionUID = 1L;

    EspecieNoExistente(String especie) {
        super("No se encuentra la especie [" + especie + "]");
    }

}
