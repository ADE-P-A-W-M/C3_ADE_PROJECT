package it.unicam.pawm.c3.autenticazione.gestori;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.gestori.*;
import it.unicam.pawm.c3.persistenza.ClienteRepository;
import it.unicam.pawm.c3.persistenza.NegozioRepository;
import it.unicam.pawm.c3.personale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
@Transactional
public class GestoreAccesso {

    private final ClienteRepository clienteRepository;
    private final NegozioRepository negozioRepository;
    private final GestoreCorrieri gestoreCorrieri;
    private final GestoreClienti gestoreClienti;
    private final GestoreAddetti gestoreAddetti;
    private final GestoreCommercianti gestoreCommercianti;
    private final GestoreAmministratori gestoreAmministratori;

    @Autowired
    public GestoreAccesso(ClienteRepository clienteRepository, NegozioRepository negozioRepository, GestoreCorrieri gestoreCorrieri, GestoreClienti gestoreClienti, GestoreAddetti gestoreAddetti, GestoreCommercianti gestoreCommercianti, GestoreAmministratori gestoreAmministratori) {
        this.clienteRepository = clienteRepository;
        this.negozioRepository = negozioRepository;
        this.gestoreCorrieri = gestoreCorrieri;
        this.gestoreClienti = gestoreClienti;
        this.gestoreAddetti = gestoreAddetti;
        this.gestoreCommercianti = gestoreCommercianti;
        this.gestoreAmministratori = gestoreAmministratori;
    }

//    public String accesso(String email, String password) {
//        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
//        if (cliente.isPresent()) {
//            if(cliente.get().getPassword().equals(password)){
//                if(cliente.get().getRuolo()!=null){
//                    switch (cliente.get().getRuolo().getRuoloSistema()){
//                        case CORRIERE:
//                            gestoreCorrieri.setCorriere((Corriere) cliente.get().getRuolo());
//                            return "CORRIERE";
//                        case AMMINISTRATORE:
//                            gestoreAmministratori.setAmministratore((Amministratore) cliente.get().getRuolo());
//                            return "AMMINISTRATORE";
//                        case ADDETTONEGOZIO:
//                            setNegozioAddetto(cliente.get());
//                            return "ADDETTONEGOZIO";
//                        case COMMERCIANTE:
//                            setNegozioAddetto(cliente.get());
//                            setNegozioCommerciante(cliente.get());
//                            return "COMMERCIANTE";
//                    }
//                }
//                gestoreClienti.setCliente(cliente.get());
//                return "CLIENTE";
//            }
//        }
//        throw new IllegalStateException("CREDENZIALI ACCESSO ERRATE");
//    }

    private void setNegozioAddetto(Cliente cliente){
//        Iterator<Negozio> negozioIterator = negozioRepository.findAll().iterator();
//        while (negozioIterator.hasNext()){
//            Negozio negozio = negozioIterator.next();
//            Iterator<AddettoNegozio> addettoNegozioIterator = negozio.getAddetti().iterator();
//            while (addettoNegozioIterator.hasNext()){
//                AddettoNegozio addettoNegozio = addettoNegozioIterator.next();
//                if(addettoNegozio.equals(cliente.getRuolo())){
//                    gestoreAddetti.setNegozio(negozio);
//                }
//            }
//        }
    }

    private void setNegozioCommerciante(Cliente cliente){
//        Iterator<Negozio> negozioIterator = negozioRepository.findAll().iterator();
//        while (negozioIterator.hasNext()){
//            Negozio negozio = negozioIterator.next();
//            Iterator<AddettoNegozio> addettoNegozioIterator = negozio.getAddetti().iterator();
//            while (addettoNegozioIterator.hasNext()){
//                AddettoNegozio addettoNegozio = addettoNegozioIterator.next();
//                if(addettoNegozio.equals(cliente.getRuolo())){
//                    gestoreCommercianti.setNegozio(negozio);
//                }
//            }
//        }
    }

 }
