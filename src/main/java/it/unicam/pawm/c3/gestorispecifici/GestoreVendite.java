package it.unicam.pawm.c3.gestorispecifici;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.persistenza.ClienteRepository;
import it.unicam.pawm.c3.persistenza.VenditaSpeditaRepository;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.vendita.StatoConsegna;
import it.unicam.pawm.c3.vendita.Vendita;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreVendite {

    private ClienteRepository clienteRepository;
    private VenditaSpeditaRepository venditaSpeditaRepository;

    public GestoreVendite(ClienteRepository clienteRepository, VenditaSpeditaRepository venditaSpeditaRepository) {
        this.clienteRepository = clienteRepository;
        this.venditaSpeditaRepository = venditaSpeditaRepository;
    }

    /**
     * Il metodo serve per settare lo stato delle vendite spedite presenti nella lista
     *
     * @param list lista delle vendite
     * @param sc stato consegna che verrà settato
     * @param corriere corriere a cui è assegnata la lista di vendite
     */
    public void aggiornaStatoVendita(List<VenditaSpedita> list, StatoConsegna sc, Corriere corriere) {
        List<VenditaSpedita> lista = list;
        for(VenditaSpedita vs : lista){
            Iterator<VenditaSpedita> iterator = corriere.getVendite().iterator();
            while (iterator.hasNext()){
                VenditaSpedita vsIterata = iterator.next();
                if(vsIterata.equals(vs)){
                    vs.setStatoConsegna(sc);
                }
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
     * Il metodo restituisce gli acquisti che il cliente deve ritirare in un determinato negozio
     *
     * @param email email del cliente
     * @param negozio negozio dove sono presenti gli acquisti
     * @return list degli acquisti da ritirare di un determinato cliente
     */
    public List<VenditaSpedita> getAcquistiClienteDaRitirare(String email, Negozio negozio) {
        List<VenditaSpedita> list = new ArrayList<>();
//        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
//        if(cliente.isPresent()){
//            if(!cliente.get().getAcquisti().isEmpty()){
//                Iterator<VenditaSpedita> venditeNegozio = negozio.getVenditeNegozioRitiro().iterator();
//                while(venditeNegozio.hasNext()){
//                    VenditaSpedita vs = venditeNegozio.next();
//                    if(vs.getStatoConsegna().equals(StatoConsegna.CONSEGNATO_AL_NEGOZIO)){
//                        Iterator<Vendita> venditaIterator = cliente.get().getAcquisti().iterator();
//                        while(venditaIterator.hasNext()){
//                            Vendita vendita = venditaIterator.next();
//                            if(vendita.getId() == vs.getId()){
//                                list.add(vs);
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return list;
    }

    /**
     * Il metodo serve per confermare e aggiornare lo stato della vendita da IN_ATTESA_DI_RITIRO a CONSEGNATO_AL_CLIENTE
     *
     * @param vendite lista delle vendite a cui verrà aggiornato lo stato consegna
     */
    public void confermaConsegnaVenditaAssegnata(List<VenditaSpedita> vendite) {
        aggiornaStatoVendita(vendite,StatoConsegna.CONSEGNATO_AL_CLIENTE);
        venditaSpeditaRepository.saveAll(vendite);
    }

    /**
     * Il metodo serve per settare lo stato delle vendite presenti nella lista
     *
     * @param list lista delle vendite spedite a cui verrà settato lo stato consegna
     * @param sc stato consegna che verrà settato
     */
    public void aggiornaStatoVendita(List<VenditaSpedita> list, StatoConsegna sc) {
        Iterator<VenditaSpedita> iterator = list.iterator();
        while (iterator.hasNext()){
            iterator.next().setStatoConsegna(sc);
        }
    }
}
