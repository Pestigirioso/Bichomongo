package ar.edu.unq.epers.bichomon.backend.dao.connection;

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
