package ar.edu.unq.epers.bichomon.backend.dao.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.util.ArrayList;
import java.util.List;

public class EspecieDAOMySQL implements EspecieDAO {

    @Override
    public void guardar(Especie especie) {
        this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO especie (nombre, altura, peso, tipo, urlFoto, cantidadBichos, energiaInicial) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, especie.getNombre());
            ps.setInt(2, especie.getAltura());
            ps.setInt(3, especie.getPeso());
            ps.setString(4, especie.getTipo().toString());
            ps.setString(5, especie.getUrlFoto());
            ps.setInt(6, especie.getCantidadBichos());
            ps.setInt(7, especie.getEnergiaInicial());
            ps.execute();

            if (ps.getUpdateCount() != 1) {
                throw new RuntimeException("No se inserto la especie " + especie);
            }
            ps.close();
            return null;
        });

    }

    @Override
    public void actualizar(Especie especie) {
        this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE especie SET altura=?, peso=?, tipo=?, urlFoto=?, cantidadBichos=?, energiaInicial=?) WHERE nombre=?");
            ps.setInt(1, especie.getAltura());
            ps.setInt(2, especie.getPeso());
            ps.setString(3, especie.getTipo().toString());
            ps.setString(4, especie.getUrlFoto());
            ps.setInt(5, especie.getCantidadBichos());
            ps.setInt(6, especie.getEnergiaInicial());
            ps.setString(7, especie.getNombre());
            ps.execute();

            if (ps.getUpdateCount() != 1) {
                throw new RuntimeException("No se actualizo la especie " + especie);
            }
            ps.close();
            return null;
        });
    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        return this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, altura, peso, tipo, urlFoto, cantidadBichos, energiaInicial FROM especie WHERE nombre = ?");
            ps.setString(1, nombreEspecie);

            ResultSet resultSet = ps.executeQuery();

            //Chequear que el resultSet devuelve solo una especie
            Especie especie = null;
            while (resultSet.next()) {
                if (especie != null) {
                    throw new RuntimeException("Existe mas de una especie con el nombre " + nombreEspecie);
                }
                especie = this.sacarEspecie(resultSet);
            }

            ps.close();
            return especie;
        });
    }

    private Especie sacarEspecie(ResultSet resultSet) throws SQLException {
        Especie especie = new Especie(resultSet.getInt("id"), resultSet.getString("nombre"), TipoBicho.valueOf(resultSet.getString("tipo")));
        especie.setAltura(resultSet.getInt("altura"));
        especie.setPeso(resultSet.getInt("peso"));
        especie.setUrlFoto(resultSet.getString("urlFoto"));
        especie.setCantidadBichos(resultSet.getInt("cantidadBichos"));
        especie.setEnergiaIncial(resultSet.getInt("energiaInicial"));
        return especie;
    }

    @Override
    public List<Especie> recuperarTodos() {
        return this.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT id, nombre, altura, peso, tipo, urlFoto, cantidadBichos, energiaInicial FROM especie");

            ResultSet resultSet = ps.executeQuery();
            List<Especie> lista = new ArrayList<>();
            while (resultSet.next()) {
                lista.add(this.sacarEspecie(resultSet));
            }
            ps.close();
            return lista;
        });
    }

    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        Connection connection = this.openConnection();
        try {
            return bloque.executeWith(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error no esperado", e);
        } finally {
            this.closeConnection(connection);
        }
    }

    /**
     * Establece una conexion a la url especificada
     *
     * @return la conexion establecida
     */
    private Connection openConnection() {
        try {
            //La url de conexion no deberia estar harcodeada aca
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bichomon?user=root&password=root");
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     *
     * @param connection - la conexion a cerrar.
     */
    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}
