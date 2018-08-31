package ar.edu.unq.epers.bichomon.backend.dao.mysql;

import ar.edu.unq.epers.bichomon.backend.dao.connection.Connection;
import ar.edu.unq.epers.bichomon.backend.dao.connection.ConnectionBlock;
import ar.edu.unq.epers.bichomon.backend.dao.connection.IConnection;

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
