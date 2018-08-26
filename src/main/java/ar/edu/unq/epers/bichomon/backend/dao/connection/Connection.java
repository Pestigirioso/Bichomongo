package ar.edu.unq.epers.bichomon.backend.dao.connection;

import java.sql.DriverManager;
import java.sql.SQLException;

class Connection {

    private String url;

    Connection (String url) {
        this.url = url;
    }

    /**
     * Ejecuta un bloque de codigo contra una conexion.
     */
    <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        java.sql.Connection connection = this.openConnection();
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
    private java.sql.Connection openConnection() {
        try {
            //La url de conexion no deberia estar harcodeada aca
            return DriverManager.getConnection(this.url);
        } catch (SQLException e) {
            throw new RuntimeException("No se puede establecer una conexion", e);
        }
    }

    /**
     * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
     *
     * @param connection - la conexion a cerrar.
     */
    private void closeConnection(java.sql.Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion", e);
        }
    }
}
