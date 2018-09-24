package epers.bichomon.dao.jdbc;

import epers.bichomon.dao.jdbc.connection.Connection;
import epers.bichomon.dao.jdbc.connection.ConnectionBlock;
import epers.bichomon.dao.jdbc.connection.IConnection;

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
