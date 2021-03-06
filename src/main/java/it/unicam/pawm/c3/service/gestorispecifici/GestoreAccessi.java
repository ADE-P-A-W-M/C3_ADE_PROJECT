package it.unicam.pawm.c3.service.gestorispecifici;

import it.unicam.pawm.c3.model.Negozio;
import it.unicam.pawm.c3.repository.*;
import it.unicam.pawm.c3.model.personale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
@Transactional
public class GestoreAccessi {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private CorriereRepository corriereRepository;
    @Autowired
    private AmministratoreRepository amministratoreRepository;

    @Autowired
    public GestoreAccessi() { }

    /**
     * Il metodo serve per recuperare il ruolo cliente a partire dalla sua email
     *
     * @param email dello user
     * @return cliente
     */
    public Cliente homeCliente(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            Iterator<Cliente> clienteIterator = clienteRepository.findAll().iterator();
            while (clienteIterator.hasNext()){
                Cliente cliente = clienteIterator.next();
                for(Ruolo ruolo : user.get().getRuolo()){
                    if(ruolo.getId() == cliente.getId()) {
                        return cliente;
                    }
                }
            }
        }
        return null;
    }

    /**
     *  Il metodo serve per recuperare il negozio
     *  relativo al ruolo addetto, a partire dalla email dello user
     *
     * @param email dello user
     * @return negozio
     */
    public Negozio homeAddetto(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            Iterator<Negozio> negozioIterator = negozioRepository.findAll().iterator();
            while(negozioIterator.hasNext()){
                Negozio negozio = negozioIterator.next();
                Iterator<AddettoNegozio> addettoNegozioIterator = negozio.getAddetti().iterator();
                while (addettoNegozioIterator.hasNext()){
                    AddettoNegozio addettoNegozio = addettoNegozioIterator.next();
                    for(Ruolo ruolo : user.get().getRuolo()){
                        if(ruolo.getId()== addettoNegozio.getId()) {
                            return negozio;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Il metodo serve per recuperare il ruolo corriere a partire dalla sua email
     *
     * @param email dello user
     * @return corriere
     */
    public Corriere homeCorriere(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            Iterator<Corriere> corriereIterator = corriereRepository.findAll().iterator();
            while (corriereIterator.hasNext()){
                Corriere corriere = corriereIterator.next();
                for(Ruolo ruolo : user.get().getRuolo()){
                    if(ruolo.getId() == corriere.getId()) {
                        return corriere;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Il metodo serve per recuperare il ruolo amministratore a partire dalla sua email
     *
     * @param email dello user
     * @return amministratore
     */
    public Amministratore homeAmministratore(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            Iterator<Amministratore> amministratoreIterator = amministratoreRepository.findAll().iterator();
            while (amministratoreIterator.hasNext()){
                Amministratore amministratore = amministratoreIterator.next();
                for(Ruolo ruolo : user.get().getRuolo()){
                    if(ruolo.getId() == amministratore.getId()) {
                        return amministratore;
                    }
                }
            }
        }
        return null;
    }
}
