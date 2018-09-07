package ar.edu.unq.epers.bichomon.backend.dao.jdbc;

import ar.edu.unq.epers.bichomon.backend.dao.jdbc.connection.Connection;
import ar.edu.unq.epers.bichomon.backend.dao.jdbc.connection.ConnectionBlock;
import ar.edu.unq.epers.bichomon.backend.dao.jdbc.connection.IConnection;

public class ConnectionMySQL implements IConnection {

    private Connection con;

    public ConnectionMySQL(){
        this.con = new Connection("jdbc:mysql://localhost:3306/bichomon?user=root&password=root");
    }

    @Override
    public <T> T executeWithConnection(ConnectionBlock<T> bloque) {
        return this.con.executeWithConnection(bloque);
    }
}
