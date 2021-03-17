package it.unicam.pawm.c3.gestori;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.persistenza.ClienteRepository;
import it.unicam.pawm.c3.persistenza.NegozioRepository;
import it.unicam.pawm.c3.persistenza.RuoloRepository;
import it.unicam.pawm.c3.persistenza.UserRepository;
import it.unicam.pawm.c3.personale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreAmministratori {

    private Amministratore amministratore;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    public GestoreAmministratori() {

    }

    public Amministratore getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(Amministratore amministratore){
        this.amministratore = amministratore;
    }

    /**
     * Il metodo serve per cercare un cliente tramite email
     *
     * @param email del cliente da cercare
     * @return cliente
     */
    public User ricercaCliente(String email) {
        Optional<User> user=userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new IllegalStateException("cliente non presente");
        }
    }

    /**
     * Il metodo serve registrare un corriere all'interno della piattaforma insieme alla sua attivita
     *
     * @param id dell'utente registrato nella piattaforma a cui verrà assegnato il ruolo di corriere
     * @param nomeDitta del nuovo corriere
     * @param piva del nuovo corriere
     * @param indirizzo del nuovo corriere
     */
    public void registraCorriere(Long id,String nomeDitta,String piva,String indirizzo) {
        Iterator<User> userIterator=userRepository.findAll().iterator();
        while(userIterator.hasNext()) {
            User user= userIterator.next();
            if(user.getId()==id) {
                User user1=user;
                    Corriere corriere = new Corriere(RuoloSistema.CORRIERE,nomeDitta,indirizzo,piva);
                    user1.setRuolo(corriere);
                    ruoloRepository.save(corriere);
                    userRepository.save(user1);
            }
        }
    }

    /**
     * Il metodo serve per registrare un negozio all'interno della piattaforma insieme al suo commerciante.
     *
     * @param categorie in cui opererà il negozio
     * @param id dell'utente registrato nella piattaforma a cui verrà assegnato il ruolo di commerciante
     * @param nome del nuovo negozio
     * @param piva del nuovo negozio
     * @param indirizzo del nuovo negozio
     */
    public void registraNegozio(List<Categoria> categorie, Long id, String nome, String piva, String indirizzo) {
        Iterator<User> userIterator=userRepository.findAll().iterator();
        while(userIterator.hasNext()) {
            User user= userIterator.next();
            if(user.getId()==id) {
                User user1=user;
                Negozio negozio=new Negozio(nome,indirizzo,piva,categorie);
                Commerciante commerciante=new Commerciante(RuoloSistema.COMMERCIANTE);
                user1.setRuolo(commerciante);
                negozio.addAddettoNegozio(commerciante);
                ruoloRepository.save(commerciante);
                userRepository.save(user1);
                negozioRepository.save(negozio);
            }
        }
    }
}
