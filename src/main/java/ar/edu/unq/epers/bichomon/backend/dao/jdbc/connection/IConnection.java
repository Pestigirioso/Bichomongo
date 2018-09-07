package ar.edu.unq.epers.bichomon.backend.dao.jdbc.connection;

public interface IConnection {
    <T> T executeWithConnection(ConnectionBlock<T> bloque);
}
