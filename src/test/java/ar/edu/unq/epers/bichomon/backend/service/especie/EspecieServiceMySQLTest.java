package ar.edu.unq.epers.bichomon.backend.service.especie;

import ar.edu.unq.epers.bichomon.backend.AbstractMySQLTest;
import ar.edu.unq.epers.bichomon.backend.dao.mysql.EspecieDAOMySQL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EspecieServiceMySQLTest extends AbstractMySQLTest {

    private EspecieService service = new EspecieServiceImpl(new EspecieDAOMySQL());

    @Test
    void actualizar_inexistente_raise_exception() {
        assertThrows(EspecieNoExistente.class, () -> service.getEspecie("inexistente"));
    }

}
