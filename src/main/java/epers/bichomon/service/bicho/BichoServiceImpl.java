package epers.bichomon.service.bicho;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.GenericDAO;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.ubicacion.duelo.ResultadoCombate;
import epers.bichomon.service.runner.Runner;

public class BichoServiceImpl implements BichoService {

    private EntrenadorDAO entrenadorDAO;
    private GenericDAO genericDAO;

    public BichoServiceImpl(EntrenadorDAO entrenadorDAO, GenericDAO genericDAO) {
        this.entrenadorDAO = entrenadorDAO;
        this.genericDAO = genericDAO;
    }

    @Override
    public Bicho buscar(String entrenador) {
        return Runner.runInSession(() -> {
            Entrenador e = entrenadorDAO.recuperar(entrenador);
            Bicho b = e.buscar();
            entrenadorDAO.actualizar(e);
            return b;
        });
    }

    @Override
    public void abandonar(String entrenador, int bicho) {
        Runner.runInSession(() -> {
            Bicho b = this.genericDAO.recuperar(Bicho.class, bicho);
            Entrenador e = this.entrenadorDAO.recuperar(entrenador);
            e.abandonar(b);
            entrenadorDAO.actualizar(e);
            return null;
        });
    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        return Runner.runInSession(() -> {
            Bicho b = this.genericDAO.recuperar(Bicho.class, bicho);
            Entrenador e = this.entrenadorDAO.recuperar(entrenador);
            ResultadoCombate resultado = e.duelo(b);
            entrenadorDAO.actualizar(e);
            return resultado;
        });
    }

    @Override
    public boolean puedeEvolucionar(int bicho) {
        // TODO implementar BichoService - boolean puedeEvolucionar
//          trAES BICHI
//  returnt bicho,puedeevolu() //Condicion de evolucion + tener entrenador
//

        return false;
    }

    @Override
    public Bicho evolucionar(int bicho) {
        return Runner.runInSession(() -> {
            Bicho b = genericDAO.recuperar(Bicho.class, bicho);
            b.evolucionar();
            genericDAO.actualizar(b);
            return b;
        });
    }
}
