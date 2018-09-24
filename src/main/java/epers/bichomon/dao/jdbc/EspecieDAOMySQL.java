package epers.bichomon.dao.jdbc;

import epers.bichomon.dao.EspecieDAO;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.model.especie.TipoBicho;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAOMySQL implements EspecieDAO {

    private ConnectionMySQL con;

    public EspecieDAOMySQL() {
        this.con = new ConnectionMySQL();
    }

    private void executeWithEspecie(Especie especie, String sql, String errorMsg) {
        this.con.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, especie.getAltura());
            ps.setInt(2, especie.getPeso());
            ps.setString(3, especie.getTipo().toString());
            ps.setString(4, especie.getUrlFoto());
            ps.setInt(5, especie.getCantidadBichos());
            ps.setInt(6, especie.getEnergiaInicial());
            ps.setString(7, especie.getNombre());
            ps.execute();
            if (ps.getUpdateCount() != 1) {
                throw new RuntimeException(errorMsg + especie);
            }
            ps.close();
            return null;
        });
    }

    @Override
    public void guardar(Especie especie) {
        final String sqlInsert = "INSERT INTO especie (altura, peso, tipo, urlFoto, cantidadBichos, energiaInicial, nombre) VALUES (?,?,?,?,?,?,?)";
        executeWithEspecie(especie, sqlInsert, "No se inserto la especie ");

        this.con.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement("SELECT LAST_INSERT_ID();");
            ResultSet resultSet = ps.executeQuery();
            int id = -1;
            while(resultSet.next()) {
                if(id != -1) {
                    throw new RuntimeException("Se obtuvo más de un ID");
                }
                id = resultSet.getInt(1);
            }
            especie.setId(id);
            ps.close();
            return null;
        });
    }

    @Override
    public void actualizar(Especie especie) {
        final String sqlUpdate = "UPDATE especie SET altura=?, peso=?, tipo=?, urlFoto=?, cantidadBichos=?, energiaInicial=? WHERE nombre=?";
        executeWithEspecie(especie, sqlUpdate, "No se actualizo la especie ");
    }

    @Override
    public Especie recuperar(String nombreEspecie) {
        final String sqlSelect = "SELECT id, nombre, altura, peso, tipo, urlFoto, cantidadBichos, energiaInicial FROM especie WHERE nombre = ?";
        return this.con.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement(sqlSelect);
            ps.setString(1, nombreEspecie);
            ResultSet resultSet = ps.executeQuery();
            Especie especie = null;
            while(resultSet.next()) {
                if(especie != null) {
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
        especie.setEnergiaInicial(resultSet.getInt("energiaInicial"));
        return especie;
    }

    @Override
    public List<Especie> recuperarTodos() {
        final String sqlSelect = "SELECT id, nombre, altura, peso, tipo, urlFoto, cantidadBichos, energiaInicial FROM especie ORDER BY nombre";
        return this.con.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement(sqlSelect);
            ResultSet resultSet = ps.executeQuery();
            List<Especie> lista = new ArrayList<>();
            while(resultSet.next()) {
                lista.add(this.sacarEspecie(resultSet));
            }
            ps.close();
            return lista;
        });
    }

    public void borrarTodo() {
        final String sqlDelete = "DELETE FROM especie";
        this.con.executeWithConnection(conn -> {
            PreparedStatement ps = conn.prepareStatement(sqlDelete);
            ps.execute();
            ps.close();
            return null;
        });
    }

}
