package epers.bichomon.dao.neo4j;

import epers.bichomon.model.ubicacion.Ubicacion;
import org.neo4j.driver.v1.*;

public class UbicacionNeo4jDAO {

    private Driver driver;

    public UbicacionNeo4jDAO() {
        this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }

    private <T> T runWithSession(SessionBlock<T> bloque) {
        Session session = this.driver.session();
        try {
            return bloque.executeWith(session);
        } finally {
            session.close();
        }
    }

    public void save(Ubicacion ubicacion) {
        runWithSession(session ->
                session.run("MERGE (n:Ubicacion {nombre: {elNombre}})",
                        Values.parameters("elNombre", ubicacion.getNombre())));
    }

    public void saveCamino(Ubicacion desde, String camino, Ubicacion hasta) {
        String query = "MATCH (desde:Ubicacion {nombre: {nombreDesde}}) " +
                "MATCH (hasta:Ubicacion {nombre: {nombreHasta}}) " +
                "MERGE (desde)-[:{elCamino}]->(hasta)";
        runWithSession(session ->
                session.run(query, Values.parameters("nombreDesde", desde.getNombre(),
                        "nombreHasta", hasta.getNombre(),
                        "elCamino", camino)));
    }

//    public List<Persona> getHijosDe(Persona padre) {
//        String query = "MATCH (padre:Persona {dni: {elDniPadre}}) " +
//                "MATCH (hijo)-[:hijoDe]->(padre) " +
//                "RETURN hijo";
//        StatementResult result = runWithSession(session ->
//                session.run(query, Values.parameters("elDniPadre", padre.getDni())));
//        //Similar a list.stream().map(...)
//        return result.list(record -> {
//            Value hijo = record.get(0);
//            String dni = hijo.get("dni").asString();
//            String nombre = hijo.get("nombre").asString();
//            String apellido = hijo.get("apellido").asString();
//            return new Persona(dni, nombre, apellido);
//        });
//    }
}
