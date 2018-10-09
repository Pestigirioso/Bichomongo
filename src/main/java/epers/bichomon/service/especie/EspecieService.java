package epers.bichomon.service.especie;

import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.especie.Especie;

import java.util.List;

public interface EspecieService {

    /**
     * Este método será utilizado por una interfaz de administración para crear nuevas
     * especies de bichos. Recibe por parametro un objeto {@link Especie} previamente
     * construido y se encarga de persistirlo en la ase de datos.  Tener en cuenta que
     * el nombre de cada especie debe ser único para toda la aplicación.
     *
     * @param especie - un objeto Especie previamente construido por la gente de frontend
     */
    void crearEspecie(Especie especie);

    /**
     * Este método devolverá la {@link Especie} cuyo nombre sea igual al provisto por
     * parámetro.
     * <p>
     * Se espera que este método devuelva, a lo sumo, un solo resultado.
     *
     * @param nombreEspecie - el nombre de la especie que se busca
     * @return la especie encontrada
     * @throws Exception {@link EspecieNoExistente} (no chequeada)
     */
    Especie getEspecie(String nombreEspecie);

    /**
     * @return una lista de todas los objetos {@link Especie} existentes ordenados
     * alfabéticamente por su nombre en forma ascendente
     */
    List<Especie> getAllEspecies();

    /**
     * Crea un nuevo Bicho perteneciente a la especie especificada. El nuevo objeto Bicho no es
     * persistido (de momento), solo devuelto.
     * <p>
     * Para llevar una mejor estadística de los bichos que han sido creados cada objeto
     * {@link Especie} cuenta con un contador cantidadBichos. El mismo deberá ser incrementado
     * en 1.
     *
     * @param nombreEspecie - el nombre de la especie del bicho a crear
     * @return un objeto {@link Bicho} instanciado
     */
    Bicho crearBicho(String nombreEspecie);

    /**
     * retorna aquellos diez especies mas populares, o sea, aquellas que tengan mas bichos en manos
     * de distintos entrenadores. No contaran los bichos en la Guardería.
     *
     * @return
     */
    List<Especie> populares();

    /**
     * retorna aquellos diez especies menos populares,
     * o sea, aquellas que tengan actualmente mas bichos en la Guardería.
     *
     * @return
     */
    List<Especie> impopulares();
}
