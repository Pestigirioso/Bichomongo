package ar.edu.unq.epers.bichomon.backend;

import ar.edu.unq.epers.bichomon.backend.dao.mysql.EspecieDAOMySQL;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AbstractMySQLTest {

    private DataService data = new DataServiceImpl(new EspecieDAOMySQL());

    @BeforeEach
    void setUp() {
        data.eliminarDatos();
        data.crearSetDatosIniciales();
    }

    @AfterEach
    void tierDown() {
        data.eliminarDatos();
    }
}
