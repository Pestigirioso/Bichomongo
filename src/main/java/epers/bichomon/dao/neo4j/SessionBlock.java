package epers.bichomon.dao.neo4j;

import org.neo4j.driver.v1.Session;

@FunctionalInterface
public interface SessionBlock<T> {
    T executeWith(Session session);
}
