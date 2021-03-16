package it.unicam.pawm.c3.gestori;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.persistenza.ClienteRepository;
import it.unicam.pawm.c3.persistenza.NegozioRepository;
import it.unicam.pawm.c3.persistenza.RuoloRepository;
import it.unicam.pawm.c3.personale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreAmministratori {

    private Amministratore amministratore;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private NegozioRepository negozioRepository;

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
//    public Cliente ricercaCliente(String email) {
//        Optional<Cliente> cliente=clienteRepository.findByEmail(email);
//        if(cliente.isPresent()){
//            return cliente.get();
//        } else {
//            throw new IllegalStateException("cliente non presente");
//        }
//    }

    /**
     * Il metodo serve registrare un corriere all'interno della piattaforma insieme alla sua attivita
     *
     * @param cliente registrato nella piattaforma a cui verrà assegnato il ruolo di corriere
     * @param nomeDitta del nuovo corriere
     * @param piva del nuovo corriere
     * @param indirizzo del nuovo corriere
     */
    public void registraCorriere(Cliente cliente,String nomeDitta,String piva,String indirizzo) {
//        Corriere corriere = new Corriere(RuoloSistema.CORRIERE,nomeDitta,indirizzo,piva);
//        cliente.setRuolo(corriere);
//        ruoloRepository.save(corriere);
//        clienteRepository.save(cliente);
    }

    /**
     * Il metodo serve per registrare un negozio all'interno della piattaforma insieme al suo commerciante.
     *
     * @param categorie in cui opererà il negozio
     * @param cliente registrato nella piattaforma a cui verrà assegnato il ruolo di commerciante
     * @param nome del nuovo negozio
     * @param piva del nuovo negozio
     * @param indirizzo del nuovo negozio
     */
    public void registraNegozio(List<Categoria> categorie, Cliente cliente, String nome, String piva, String indirizzo) {
//        Negozio negozio=new Negozio(nome,indirizzo,piva,categorie);
//        Commerciante commerciante=new Commerciante(RuoloSistema.COMMERCIANTE);
//        cliente.setRuolo(commerciante);
//        negozio.addAddettoNegozio(commerciante);
//        ruoloRepository.save(commerciante);
//        clienteRepository.save(cliente);
//        negozioRepository.save(negozio);
    }
}
