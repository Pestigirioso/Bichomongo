package epers.bichomon.service.bicho;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.bicho.BichoNoEvolucionableException;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.entrenador.Nivel;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;
import epers.bichomon.model.especie.condicion.*;
import epers.bichomon.service.ServiceFactory;
import epers.bichomon.service.runner.SessionFactoryProvider;
import epers.bichomon.service.test.TestService;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BichoServiceEvolucionTest {

    static private BichoService service = ServiceFactory.getBichoService();
    static private TestService testService = ServiceFactory.getTestService();

    @BeforeAll
    static void prepare() {
        testService.crearEntidad(new Especie("EspecieFinal", TipoBicho.FUEGO));
    }

    @AfterAll
    static void cleanup() {
        SessionFactoryProvider.destroy();
    }

    private void crearEspecieEvolucionable(String nombre, TipoBicho tipo, Especie evolucion, Set<Condicion> condiciones) {
        testService.crearEntidad(new Especie(nombre, tipo, evolucion, condiciones));
    }

    private int crearBicho(String especie, Entrenador entrenador) {
        Especie e = testService.recuperarByName(Especie.class, especie);
        Bicho b = e.crearBicho();
        if(entrenador != null) {
            b.capturadoPor(entrenador);
        }
        testService.crearEntidad(b);
        return b.getID();
    }


    //-------> Tests sobre condiciones genéricas
    @Test
    void puede_evolucionar_bicho_de_especie_no_evolucionable_false() {
        int id = this.crearBicho("EspecieFinal", null);
        assertFalse(service.puedeEvolucionar(id));
    }

    @Test
    void evolucionar_un_bicho_evolucionable_sin_condicion_especifica_tiene_especie_final() {
        Set<Condicion> set = new HashSet<>();
        this.crearEspecieEvolucionable("EspecieBase", TipoBicho.FUEGO, testService.recuperarByName(Especie.class, "EspecieFinal"), set);
        int id = this.crearBicho("EspecieBase", null);
        service.evolucionar(id);
        assertEquals("EspecieFinal", testService.recuperar(Bicho.class, id).getEspecie().getNombre());
        testService.borrar(Bicho.class, id);
    }

    @Test
    void evolucionar_bicho_de_especie_no_evolucionable_raise_exception() {
        int id = this.crearBicho("EspecieFinal", null);
        assertThrows(BichoNoEvolucionableException.class, () -> service.evolucionar(id));
        testService.borrar(Bicho.class,id);
    }

    //-------> Tests sobre la condicion de edad



    @Test
    void puede_evolucionar_un_bicho_que_no_cumple_con_la_condicion_de_edad_false() {
        this.crearEspecieEvolucionable("EspecieEdad", TipoBicho.FUEGO, testService.recuperarByName(Especie.class, "EspecieFinal"), Sets.newHashSet(new CondicionEdad(30)));
        Entrenador e = new Entrenador("unEntrenador");
        testService.crearEntidad(e);
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieEdad"), e, LocalDate.of(2018, 10, 5));
        testService.crearEntidad(b);
        assertFalse(service.puedeEvolucionar(b.getID()));
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrar(Bicho.class, b.getID());
    }

    @Test
    void un_bicho_que_cumple_con_la_condicion_de_edad_puede_evolucionar() {
        this.crearEspecieEvolucionable("EspecieEdad", TipoBicho.FUEGO, testService.recuperarByName(Especie.class, "EspecieFinal"), Sets.newHashSet(new CondicionEdad(5)));
        Entrenador e = new Entrenador("unEntrenador");
        testService.crearEntidad(e);
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieEdad"), e, LocalDate.of(2018, 1, 3));
        testService.crearEntidad(b);
        assertTrue(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieEdad");
    }

    //-------> Tests sobre la condicion de energia
    @Test
    void puede_evolucionar_un_bicho_que_no_cumple_con_la_condicion_de_energia_false() {
        this.crearEspecieEvolucionable("EspecieEnergia",
                                        TipoBicho.FUEGO,  testService.recuperarByName(Especie.class, "EspecieFinal"),
                                        Sets.newHashSet(new CondicionEnergia(10)));
        Entrenador e = new Entrenador("unEntrenador");
        testService.crearEntidad(e);
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieEnergia"),
                            9);
        testService.crearEntidad(b);
        assertFalse(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieEnergia");
    }

    @Test
    void un_bicho_que_cumple_con_la_condicion_de_energia_puede_evolucionar() {
        this.crearEspecieEvolucionable("EspecieEnergia",
                TipoBicho.FUEGO,  testService.recuperarByName(Especie.class, "EspecieFinal"),
                Sets.newHashSet(new CondicionEnergia(10)));
        Entrenador e = new Entrenador("unEntrenador");
        testService.crearEntidad(e);
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieEnergia"),10);
        testService.crearEntidad(b);
        assertTrue(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieEnergia");
    }

    //-------> Tests sobre la condicion de nivel
    @Test
    void puede_evolucionar_un_bicho_que_no_cumple_con_la_condicion_de_nivel_false() {
        this.crearEspecieEvolucionable("EspecieNivel",
                                        TipoBicho.FUEGO,
                                        testService.recuperarByName(Especie.class, "EspecieFinal"),
                                        Sets.newHashSet(new CondicionNivel(5)));
        Nivel lvl = new Nivel(4,5,15);
        Entrenador e = new Entrenador("unEntrenador",lvl);
        testService.crearEntidad(e);
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieNivel"), e);
        testService.crearEntidad(b);
        assertFalse(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieNivel");
    }

    @Test
    void un_bicho_que_cumple_con_la_condicion_de_nivel_puede_evolucionar() {
        this.crearEspecieEvolucionable("EspecieNivel",
                TipoBicho.FUEGO,
                testService.recuperarByName(Especie.class, "EspecieFinal"),
                Sets.newHashSet(new CondicionNivel(5)));
        Nivel lvl = new Nivel(5,5,15);
        Entrenador e = new Entrenador("unEntrenador",lvl);
        testService.crearEntidad(e);
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieNivel"), e);
        testService.crearEntidad(b);
        assertTrue(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieNivel");
    }

    //-------> Tests sobre la condicion de victorias
    @Test
    void un_bicho_que_no_cumple_con_la_condicion_de_victorias_no_puede_evolucionar() {
        this.crearEspecieEvolucionable("EspecieVictorias",
                                        TipoBicho.FUEGO,
                                        testService.recuperarByName(Especie.class, "EspecieFinal"),
                                        Sets.newHashSet(new CondicionVictorias(5)));
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieVictorias"));
        //Le seteo 4 victorias
        for(int i=0; i<4; i++) b.ganasteDuelo();
        testService.crearEntidad(b);
        assertFalse(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieVictorias");
    }

    @Test
    void un_bicho_que_cumple_con_la_condicion_de_victorias_puede_evolucionar() {
        this.crearEspecieEvolucionable("EspecieVictorias",
                TipoBicho.FUEGO,
                testService.recuperarByName(Especie.class, "EspecieFinal"),
                Sets.newHashSet(new CondicionVictorias(5)));
        Bicho b = new Bicho(testService.recuperarByName(Especie.class, "EspecieVictorias"));
        //Le seteo 5 victorias
        for(int i=0; i<5; i++) b.ganasteDuelo();
        testService.crearEntidad(b);
        assertTrue(service.puedeEvolucionar(b.getID()));
        testService.borrar(Bicho.class, b.getID());
        testService.borrarByName(Entrenador.class, "unEntrenador");
        testService.borrarByName(Especie.class, "EspecieVictorias");
    }

    //-------> Tests combinados
    @Test
    void un_bicho_que_no_cumple_con_todas_las_condiciones_juntas_no_puede_evolucionar() {
        fail();
    }

    @Test
    void un_bicho_que_cumple_con_todas_las_condiciones_juntas_puede_evolucionar() {
        fail();
    }
}
