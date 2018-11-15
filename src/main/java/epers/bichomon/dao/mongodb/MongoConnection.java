package epers.bichomon.dao.mongodb;

import com.mongodb.MongoClient;
import org.jongo.Jongo;

/**
 * As per documentation:
 *
 * "The MongoClient instance actually represents a pool of connections to the database;
 * you will only need one instance of class MongoClient even with multiple threads."
 *
 * This singleton ensures that only one instance of MongoClient ever exists
 */
enum MongoConnection {
    INSTANCE;

    private Jongo jongo;

    @SuppressWarnings("deprecation")
    MongoConnection() {
        MongoClient client = new MongoClient("localhost", 27017);
        this.jongo = new Jongo(client.getDB("bichomongo"));
    }

    public Jongo getJongo() {
        return this.jongo;
    }

}