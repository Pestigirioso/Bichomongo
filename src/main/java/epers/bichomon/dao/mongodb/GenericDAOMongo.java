package epers.bichomon.dao.mongodb;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenericDAOMongo<T> {

    private Class<T> entityType;
    protected MongoCollection mongoCollection;

    public GenericDAOMongo(Class<T> entityType) {
        this.entityType = entityType;
        this.mongoCollection = this.getCollectionFor(entityType);
    }

    private MongoCollection getCollectionFor(Class<T> entityType) {
        Jongo jongo = MongoConnection.INSTANCE.getJongo();
        return jongo.getCollection(entityType.getSimpleName());
    }

    public void deleteAll() {
        this.mongoCollection.drop();
    }

    public void save(T object) {
        this.mongoCollection.insert(object);
    }

    public void save(List<T> objects) {
        this.mongoCollection.insert(objects.toArray());
    }

    public T get(String id) {
        return this.mongoCollection.findOne(new ObjectId(id)).as(this.entityType);
    }

    public List<T> find(String query, FindBlock block, Object... parameters) {
        try {
            MongoCursor<T> all = block.executeWith(this.mongoCollection.find(query, parameters)).as(this.entityType);
            List<T> result = this.copyToList(all);
            all.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> find(String query, Object... parameters) {
        return this.find(query, (f) -> f, parameters);
    }

    /**
     * Copia el contenido de un iterable en una lista
     */
    protected <X> List<X> copyToList(Iterable<X> iterable) {
        List<X> result = new ArrayList<>();
        iterable.forEach(result::add);
        return result;
    }

}



