package it.unicam.pawm.c3.service.gestorispecifici;

import it.unicam.pawm.c3.model.Negozio;
import it.unicam.pawm.c3.repository.ClienteRepository;
import it.unicam.pawm.c3.repository.UserRepository;
import it.unicam.pawm.c3.repository.VenditaSpeditaRepository;
import it.unicam.pawm.c3.model.personale.*;
import it.unicam.pawm.c3.model.vendita.LuogoDiRitiro;
import it.unicam.pawm.c3.model.vendita.StatoConsegna;
import it.unicam.pawm.c3.model.vendita.Vendita;
import it.unicam.pawm.c3.model.vendita.VenditaSpedita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreVendite {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VenditaSpeditaRepository venditaSpeditaRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public GestoreVendite() { }

    /**
     * Il metodo serve per settare lo stato di una vendita
     *
     * @param vs vendita su cui setto o stato
     * @param sc stato consegna che verrà settato
     * @param corriere corriere a cui è assegnata la lista di vendite
     */
    public void aggiornaStatoVendita(VenditaSpedita vs , StatoConsegna sc, Corriere corriere){
        for(VenditaSpedita v : corriere.getVendite()){
            if(v.getId()==vs.getId()){
                v.setStatoConsegna(sc);
                venditaSpeditaRepository.save(v);
            }
        }
    }

    /**
     * Il metodo restituisce la lista delle vendite presenti nel negozio che devono essere ritirate
     *
     * @param corriere corriere che deve ritirare la merce
     * @return list delle vendite da ritirare
     */
    public List<VenditaSpedita> getVenditeDaRitirare(Corriere corriere){
        return corriere.getVenditePerStato(StatoConsegna.IN_ATTESA_DI_RITIRO);
    }

    /**
     * restituisce le lista contenente le vendite ritirate
     *
     * @param corriere corriere che deve ritirare la merce dal corriere
     * @return list delle vendite ritirate
     */
    public List<VenditaSpedita> getVenditeRitirate(Corriere corriere){
        return corriere.getVenditePerStato(StatoConsegna.RITIRATO);
    }

    /**
     * Il metodo restituisce la lista contente le vendite consegnate dal corriere
     *
     * @param corriere corriere che deve ritirare la merce
     * @return list delle vendite consegnate
     */
    public List<VenditaSpedita> getVenditeConsegnate(Corriere corriere){
        List<VenditaSpedita> cac = corriere.getVenditePerStato(StatoConsegna.CONSEGNATO_AL_CLIENTE);
        List<VenditaSpedita> caldr = corriere.getVenditePerStato(StatoConsegna.CONSEGNATO_AL_NEGOZIO);
        List<VenditaSpedita> tot = new ArrayList<>();
        tot.addAll(cac);
        tot.addAll(caldr);
        return tot;
    }

    /**
     * Il metodo serve per recuperare gli acquisti da ritirirare di un determinato cliente
     *
     * @param id del cliente
     * @param negozio del negozio dove si trovano gli acquisti
     * @return lista di vendite spedite
     */
    public List<VenditaSpedita> getAcquistiClienteDaRitirare(Long id,Negozio negozio){
        List<VenditaSpedita> list = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        Iterator<Ruolo> ruoloIterator = user.get().getRuolo().iterator();
        while(ruoloIterator.hasNext()){
            Ruolo ruolo =ruoloIterator.next();
            if(ruolo.getRuoloSistema().equals(RuoloSistema.CLIENTE)){
                Cliente cliente = (Cliente) ruolo;
                if(!cliente.getAcquisti().isEmpty()){
                    Iterator<VenditaSpedita> venditeNegozio = negozio.getVenditeNegozioRitiro().iterator();
                    while(venditeNegozio.hasNext()){
                        VenditaSpedita vs = venditeNegozio.next();
                        if(vs.getStatoConsegna().equals(StatoConsegna.CONSEGNATO_AL_NEGOZIO) && vs.getLuogoDiRitiro().equals(LuogoDiRitiro.NEGOZIO)){
                            Iterator<Vendita> venditaIterator = cliente.getAcquisti().iterator();
                            while(venditaIterator.hasNext()){
                                Vendita vendita = venditaIterator.next();
                                if(vendita.getId() == vs.getId()){
                                    list.add(vs);
                                    clienteRepository.save(cliente);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * Il metodo serve per confermare la vendita assegnata
     * e per settare lo stato della vendita ad assegnato
     *
     * @param id della vendita spedita
     * @param negozio del negozio dove si trova la merce
     */
    public void confermaConsegnaVenditaAssegnata(Long id, Negozio negozio){
        Optional<VenditaSpedita> VS = venditaSpeditaRepository.findById(id);
        if(VS.isPresent()) {
            for(VenditaSpedita vs : negozio.getVenditeNegozioRitiro()){
                if(vs.getId()==id){
                    VS.get().setStatoConsegna(StatoConsegna.CONSEGNATO_AL_CLIENTE);
                    venditaSpeditaRepository.save(VS.get());
                }
            }
        }
    }

}
