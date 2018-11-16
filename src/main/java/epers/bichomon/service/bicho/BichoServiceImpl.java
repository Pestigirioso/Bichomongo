package epers.bichomon.service.bicho;

import epers.bichomon.dao.EntrenadorDAO;
import epers.bichomon.dao.EventoDAO;
import epers.bichomon.dao.GenericDAO;
import epers.bichomon.model.bicho.Bicho;
import epers.bichomon.model.entrenador.Entrenador;
import epers.bichomon.model.evento.Evento;
import epers.bichomon.model.evento.TipoEvento;
import epers.bichomon.model.ubicacion.duelo.ResultadoCombate;
import epers.bichomon.service.runner.Runner;

public class BichoServiceImpl implements BichoService {

    private EntrenadorDAO entrenadorDAO;
    private GenericDAO genericDAO;
    private EventoDAO eventoDAO;

    public BichoServiceImpl(EntrenadorDAO entrenadorDAO, GenericDAO genericDAO, EventoDAO eventoDAO) {
        this.entrenadorDAO = entrenadorDAO;
        this.genericDAO = genericDAO;
        this.eventoDAO = eventoDAO;
    }

    @Override
    public Bicho buscar(String entrenador) {
        return Runner.runInSession(() -> {
            Entrenador e = entrenadorDAO.get(entrenador);
            Bicho b = e.buscar();
            entrenadorDAO.upd(e);
            if (b != null){
                eventoDAO.save(new Evento(e.getNombre(), e.getUbicacion().getNombre(), TipoEvento.Captura));
            }
            return b;
        });
    }

    @Override
    public void abandonar(String entrenador, int bicho) {
        Runner.runInSession(() -> {
            Entrenador e = this.entrenadorDAO.get(entrenador);
            Bicho b = this.genericDAO.get(Bicho.class, bicho);
            e.abandonar(b);
            entrenadorDAO.upd(e);
            eventoDAO.save(new Evento(e.getNombre(), e.getUbicacion().getNombre(), TipoEvento.Abandono));
            return null;
        });
    }

    @Override
    public ResultadoCombate duelo(String entrenador, int bicho) {
        return Runner.runInSession(() -> {
            Entrenador e = this.entrenadorDAO.get(entrenador);
            Bicho b = this.genericDAO.get(Bicho.class, bicho);
            ResultadoCombate resultado = e.duelo(b);
            entrenadorDAO.upd(e);
            if (resultado.getGanador().equals(b)) {
                eventoDAO.save(new Evento(e.getNombre(), e.getUbicacion().getNombre(), TipoEvento.Coronacion));

                // TODO arreglar esta poronga
                if (resultado.getPerdedor() != null) {
                    String descoronado = resultado.getPerdedor().getEntrenador().getNombre();
                    // TODO preguntar descoronado tiene evento de coronacion ??
                    eventoDAO.save(new Evento(descoronado, e.getUbicacion().getNombre(), TipoEvento.Coronacion));
                }
            }
            return resultado;
        });
    }

    @Override
    public boolean puedeEvolucionar(int bicho) {
        return Runner.runInSession(() -> this.genericDAO.get(Bicho.class, bicho).puedeEvolucionar());
    }

    @Override
    public Bicho evolucionar(int bicho) {
        return Runner.runInSession(() -> {
            Bicho b = genericDAO.get(Bicho.class, bicho);
            b.evolucionar();
            genericDAO.upd(b);
            return b;
        });
    }
}
