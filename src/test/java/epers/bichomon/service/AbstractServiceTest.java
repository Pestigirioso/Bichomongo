package epers.bichomon.service;

import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.entrenador.XPuntos;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractServiceTest {
    protected static TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    protected static void setUp() {
        testService.save(Nivel.create());
        testService.save(new XPuntos());
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }
}
