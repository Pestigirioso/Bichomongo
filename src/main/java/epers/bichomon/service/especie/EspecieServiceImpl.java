package epers.bichomon.service.especie;

import epers.bichomon.dao.EspecieDAO;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.especie.Especie;
import epers.bichomon.service.runner.Runner;

import java.util.List;

public class EspecieServiceImpl implements EspecieService {

    private EspecieDAO especieDAO;

    public EspecieServiceImpl(EspecieDAO dao) {
        this.especieDAO = dao;
    }

    @Override
    public void crearEspecie(Especie especie) {
        Runner.runInSession(() -> {
            especieDAO.guardar(especie);
            return null;
        });
    }

    @Override
    public Especie getEspecie(String nombreEspecie) {
        return Runner.runInSession(() -> {
            Especie especie = especieDAO.recuperar(nombreEspecie);
            if(especie == null) {
                throw new EspecieNoExistente(nombreEspecie);
            }
            return especie;
        });
    }

    @Override
    public List<Especie> getAllEspecies() {
        return Runner.runInSession(() -> especieDAO.recuperarTodos());
    }

    @Override
    public Bicho crearBicho(String nombreEspecie) {
        return Runner.runInSession(() -> {
            Especie especie = especieDAO.recuperar(nombreEspecie);
            Bicho bicho = especie.crearBicho();
            especieDAO.actualizar(especie);
            return bicho;
        });
    }

    @Override
    public List<Especie> populares() {
        return null;
    }

    @Override
    public List<Especie> impopulares() {
        return null;
    }

}
