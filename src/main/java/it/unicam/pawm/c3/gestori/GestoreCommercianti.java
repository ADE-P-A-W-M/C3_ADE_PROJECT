package it.unicam.pawm.c3.gestori;


import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.gestorispecifici.GestoreMerci;
import it.unicam.pawm.c3.merce.*;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class GestoreCommercianti {

    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private CorriereRepository corriereRepository;
    @Autowired
    private GestoreMerci gestoreMerci;

    private Negozio negozio;

    @Autowired
    public GestoreCommercianti() {
        this.gestoreMerci = new GestoreMerci();
    }

    public Negozio getNegozio() {
        return this.negozio;
    }

    public void setNegozio(Negozio negozio){
        this.negozio = negozio;
    }

    /*****************Assegnazione Carta***************/

    public User getCliente(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        throw new IllegalStateException("utente non presente");
    }

    /*****************GestionePromozioni*****************/

    public List<MerceInventarioNegozio> getPromozioniAttive() {
        return gestoreMerci.getPromozioniAttive(getNegozio());
    }

    public List<MerceInventarioNegozio> getPromozioniPossibili() {
        return gestoreMerci.getPromozioniPossibili(getNegozio());
    }

    public void addPromozione(MerceInventarioNegozio miv, LocalDate di, LocalDate df, double pp) {
        gestoreMerci.addPromozione(miv, di, df, pp);
    }

    public void rimuoviPromozione(MerceInventarioNegozio min) {
        gestoreMerci.rimuoviPromozione(min);
    }

    /****************Gestione corrieri*******************/

    /**
     * il metodo serve ad ottenere i corrieri non ancora affiliati col negozio corrente
     * @return la lista dei corrieri disponibili all'aggiunta
     */
    public List<Corriere> getCorrieri() {
        List<Corriere> lc=new ArrayList<>();
        List<Corriere> listaCorretta = new ArrayList<>();
        lc.addAll(corriereRepository.findAll());
        Iterator<Corriere> corriereIterator = negozio.getCorrieri().iterator();
        while (corriereIterator.hasNext()){
            Corriere corriere = corriereIterator.next();
            Optional<Corriere> corriereOptional = corriereRepository.findById(corriere.getId());
            if(corriereOptional.isPresent()){
                listaCorretta.add(corriereOptional.get());
            }
        }
        lc.removeAll(listaCorretta);
        return lc;
    }

    /**
     * il metodo serve per affiliare al negozio la lista di corrieri specificata
     * @param corriereDaAggiungere al negozio
     */
    public void addCorriere(Corriere corriereDaAggiungere) {
        negozio.addCorriere(corriereDaAggiungere);
        negozioRepository.save(negozio);
    }

    /****************Assunzione Addetto*******************/

    /**
     * il metodo permette al commerciante di assumere un nuovo addetto per il negozio
     * @param user che diventerà  addetto
     */
    public void assunzioneAddetto(User user){
        AddettoNegozio addettoNegozio = new AddettoNegozio(RuoloSistema.ADDETTONEGOZIO);
        user.setRuolo(addettoNegozio);
        ruoloRepository.save(addettoNegozio);
        userRepository.save(user);
        getNegozio().addAddettoNegozio(addettoNegozio);
        negozioRepository.save(negozio);
    }

    /***************Gestione Inventario********************/

    /**
     * il metodo serve ad ottenere l'inventario del negozio corente
     * @return la lista delle merci dell'inventario
     */
    public List<MerceInventarioNegozio> getInventario() {
        return negozio.getMerceInventarioNegozio();
    }

    public boolean verificaIdMerce(Long id) {
        return gestoreMerci.verificaIdMerce(id);
    }

    public void addMerce(Long id, String nome, String descrizione, Categoria categoria, double quantita, double prezzo, double sconto) {
        System.out.println(getNegozio());
        gestoreMerci.addMerce(id, nome,descrizione, categoria, quantita, prezzo , sconto , getNegozio());
    }

    public void removeMerce(MerceInventarioNegozio min, double quantita){
        gestoreMerci.removeMerce(min, quantita, getNegozio());
    }

    public void modificaMerce(MerceInventarioNegozio min,double prezzo, double sconto, double quantita) {
        gestoreMerci.modificaMerce(min, prezzo,sconto,quantita);
    }

    /************************** Metodi Accessori ******************************/


}
