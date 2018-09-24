package epers.bichomon.dao.jdbc.connection;

public interface IConnection {
    <T> T executeWithConnection(ConnectionBlock<T> bloque);
}
