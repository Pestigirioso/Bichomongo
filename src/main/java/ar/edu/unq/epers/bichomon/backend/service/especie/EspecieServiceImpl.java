package ar.edu.unq.epers.bichomon.backend.service.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

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

}
