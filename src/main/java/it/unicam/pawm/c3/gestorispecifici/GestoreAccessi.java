package it.unicam.pawm.c3.gestorispecifici;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.persistenza.ClienteRepository;
import it.unicam.pawm.c3.persistenza.NegozioRepository;
import it.unicam.pawm.c3.persistenza.UserRepository;
import it.unicam.pawm.c3.personale.AddettoNegozio;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Ruolo;
import it.unicam.pawm.c3.personale.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
    public GestoreAccessi() { }

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

//    public Negozio homeCommerciante(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if(user.isPresent()){
//            Iterator<Negozio> negozioIterator = negozioRepository.findAll().iterator();
//            while(negozioIterator.hasNext()){
//                Negozio negozio = negozioIterator.next();
//                Iterator<AddettoNegozio> addettoNegozioIterator = negozio.getAddetti().iterator();
//                while (addettoNegozioIterator.hasNext()){
//                    AddettoNegozio addettoNegozio = addettoNegozioIterator.next();
//                    for(Ruolo ruolo : user.get().getRuolo()){
//                        if(ruolo.getId()== addettoNegozio.getId()) {
//                            return negozio;
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
}
