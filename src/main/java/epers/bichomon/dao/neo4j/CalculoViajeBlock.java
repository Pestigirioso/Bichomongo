package epers.bichomon.dao.neo4j;

import epers.bichomon.model.ubicacion.Ubicacion;

public interface CalculoViajeBlock {
    int calcular(Ubicacion desde, Ubicacion hasta);
}
