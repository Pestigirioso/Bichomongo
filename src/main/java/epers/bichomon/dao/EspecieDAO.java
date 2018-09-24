package epers.bichomon.dao;//

import epers.bichomon.model.especie.Especie;

import java.util.List;

public interface EspecieDAO {

    void guardar(Especie especie);

    void actualizar(Especie especie);

    Especie recuperar(String nombreEspecie);

    List<Especie> recuperarTodos();

}
