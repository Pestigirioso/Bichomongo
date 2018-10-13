package epers.bichomon;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.entrenador.XPuntos;
import epers.bichomon.model.ubicacion.Ubicacion;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Set;

public abstract class AbstractServiceTest {
    protected static TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    protected static void setUp() {
        testService.save(Nivel.create());
        testService.save(new XPuntos());
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.clear();
    }

    protected Entrenador newEntrenador(String nombre, Set<Bicho> bichos) {
        Entrenador e = new Entrenador(nombre, testService.getBy(Nivel.class, "nro", 1), testService.get(XPuntos.class, 1), bichos);
        testService.save(e);
        return e;
    }

    protected Entrenador newEntrenador(String nombre, Ubicacion ubicacion, Set<Bicho> bichos) {
        Entrenador e = newEntrenador(nombre, bichos);
        e.moverA(ubicacion);
        testService.upd(e);
        return e;
    }

    protected Entrenador newEntrenador(String nombre, Ubicacion ubicacion) {
        return newEntrenador(nombre, ubicacion, Sets.newHashSet());
    }

    protected Entrenador newEntrenador(String nombre) {
        return newEntrenador(nombre, Sets.newHashSet());
    }
}
