package ar.edu.unq.epers.bichomon.backend;

import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceMySQL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class AbstractMySQLTest {

    private DataService data = new DataServiceMySQL();

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
