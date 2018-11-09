package epers.bichomon.dao.neo4j;

import epers.bichomon.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

import java.util.List;

public class UbicacionDAONeo4j {

    private Driver driver;

    public UbicacionDAONeo4j() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }

    private <T> T runWithSession(SessionBlock<T> bloque) {
        try (Session s = this.driver.session()) {
            return bloque.executeWith(s);
        }
    }

    public void save(Ubicacion ubicacion) {
        runWithSession(s -> s.run("MERGE (n:Ubicacion {id: {elID}})", Values.parameters("elID", ubicacion.getID())));
    }

    public void saveCamino(Ubicacion desde, String camino, Ubicacion hasta) {
        String q = "MATCH (desde:Ubicacion {id: {idDesde}}) " +
                "MATCH (hasta:Ubicacion {id: {idHasta}}) " +
                "MERGE (desde)-[c:" + camino + " {costo: {unCosto}}]->(hasta)";
        runWithSession(s -> s.run(q,
                Values.parameters("idDesde", desde.getID(), "idHasta", hasta.getID(),
                        "unCosto", TipoCamino.valueOf(camino).getCosto())));
    }

    private int viajeMas(String q, Ubicacion desde, Ubicacion hasta) {
        StatementResult result = runWithSession(s -> s.run(q,
                Values.parameters("idDesde", desde.getID(), "idHasta", hasta.getID())));
        return result.single().get(0).asInt();
    }

    public int viajeMasBarato(Ubicacion desde, Ubicacion hasta) {
        String q = "MATCH(:Ubicacion {id: {idDesde}})-[r*]->(:Ubicacion {id: {idHasta}}) " +
                "return reduce(total=0, c IN r | total+c.costo ) as total order by total limit 1";
        return viajeMas(q, desde, hasta);
    }

    public int viajeMasCorto(Ubicacion desde, Ubicacion hasta) {
        String q = "MATCH shortestPath((:Ubicacion {id: {idDesde}})-[r*]->(:Ubicacion {id: {idHasta}})) " +
                "return reduce(total=0, c IN r | total+c.costo)";
        return viajeMas(q, desde, hasta);
    }

    public List<Integer> conectados(Ubicacion ubicacion, String tipoCamino) {
        String q = "MATCH (:Ubicacion {id: {elID}})-[:" + tipoCamino + "]->(u) RETURN DISTINCT u";
        StatementResult result = runWithSession(s -> s.run(q, Values.parameters("elID", ubicacion.getID())));
        return result.list(record -> {
            Value u = record.get(0);
            return u.get("id").asInt();
        });
    }

    public Boolean existeCamino(Ubicacion desde, Ubicacion hasta) {
        String q = "MATCH(c:Ubicacion {id: {desde}})-[*]->(:Ubicacion {id: {hasta}}) RETURN c";
        StatementResult result = runWithSession(s -> s.run(q,
                Values.parameters("desde", desde.getID(), "hasta", hasta.getID())));
        return result.hasNext();
    }

    public void clear() {
        runWithSession(s -> s.run("MATCH (n) DETACH DELETE n"));
    }

}
