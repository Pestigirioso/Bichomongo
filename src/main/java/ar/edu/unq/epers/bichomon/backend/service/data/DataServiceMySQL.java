package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAOMySQL;
import ar.edu.unq.epers.bichomon.backend.dao.connection.ConnectionMySQL;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.sql.PreparedStatement;

public class DataServiceMySQL implements DataService {

    private ConnectionMySQL con;
    private EspecieDAO dao;

    public DataServiceMySQL() {
        this.con = new ConnectionMySQL();
        this.dao = new EspecieDAOMySQL();
    }

    @Override
    public void eliminarDatos() {
        this.con.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM especie");
            ps.execute();
            ps.close();
            return null;
        });
    }

    private void guardarEspecie(int id, String nombre, TipoBicho tipo, int altura, int peso, int energia, String url) {
        Especie especie = new Especie(id, nombre, tipo);
        especie.setAltura(altura);
        especie.setPeso(peso);
        especie.setEnergiaIncial(energia);
        especie.setUrlFoto(url);
        this.dao.guardar(especie);
    }

    @Override
    public void crearSetDatosIniciales() {
        this.guardarEspecie(1, "Rojomon", TipoBicho.FUEGO, 180, 75,
                100, "/image/rojomon.jpg");
        this.guardarEspecie(2, "Amarillomon", TipoBicho.ELECTRICIDAD, 170, 69,
                300, "/image/amarillomon.jpg");
        this.guardarEspecie(3, "Verdemon", TipoBicho.PLANTA, 150, 55,
                5000, "/image/verdemon.jpg");
        this.guardarEspecie(4, "Tierramon", TipoBicho.TIERRA, 1050, 99,
                5000, "/image/tierramon.jpg");
        this.guardarEspecie(5, "Fantasmon", TipoBicho.AIRE, 1050, 99,
                5000, "/image/fantasmon.jpg");
        this.guardarEspecie(6, "Vampiron", TipoBicho.AIRE, 1050, 99,
                5000, "/image/vampiromon.jpg");
        this.guardarEspecie(7, "Fortmon", TipoBicho.CHOCOLATE, 1050, 99,
                5000, "/image/fortmon.jpg");
        this.guardarEspecie(8, "Dientemon", TipoBicho.AGUA, 1050, 99,
                5000, "/image/dientmon.jpg");
    }
}
