package epers.bichomon.dao;

import epers.bichomon.model.especie.Especie;

import java.util.List;

public interface EspecieDAO {
    void save(Especie especie);

    void upd(Especie especie);

    Especie get(String nombreEspecie);

    List<Especie> recuperarTodos();

    List<Especie> getPopulares();

    List<Especie> getImpopulares();

    Especie lider();
}
