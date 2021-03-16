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
    private MerceInventarioNegozioRepository merceInventarioNegozioRepository;
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
    public Promozione getPromozione(Long id) {
        return gestoreMerci.getPromozione(id);
    }
    public List<MerceInventarioNegozio> getPromozioniAttive() {
        return gestoreMerci.getPromozioniAttive(getNegozio());
    }

    public List<MerceInventarioNegozio> getPromozioniPossibili() {
        return gestoreMerci.getPromozioniPossibili(getNegozio());
    }

    public void addPromozione(Long id, LocalDate di, LocalDate df, double pp) {
        MerceInventarioNegozio miv=merceInventarioNegozioRepository.findById(id).get();
        gestoreMerci.addPromozione(miv, di, df, pp);
    }

    public void rimuoviPromozione(Long id) {
        MerceInventarioNegozio min = merceInventarioNegozioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promozione :" + id));
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
     * @param id del corriere da aggiungere
     */
    public void addCorriere(Long id) {
        Corriere corriere = corriereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        negozio.addCorriere(corriere);
        negozioRepository.save(negozio);
    }

    /****************Assunzione Addetto*******************/

    /**
     * il metodo permette al commerciante di assumere un nuovo addetto per il negozio
     * @param id dell'utente che diventerà  addetto
     */
    public void assunzioneAddetto(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user:" + id));
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

    public void removeMerce(Long id, double quantita){
        MerceInventarioNegozio min = merceInventarioNegozioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid merce :" + id));
        gestoreMerci.removeMerce(min, quantita, getNegozio());
    }

    public void modificaMerce(Long id,double prezzo, double sconto, double quantita) {
        //MerceInventarioNegozio min=merceInventarioNegozioRepository.findById(id).get();
        Iterator<MerceInventarioNegozio> minList=getNegozio().getMerceInventarioNegozio().iterator();
        while(minList.hasNext()) {
            MerceInventarioNegozio min= minList.next();
            if(min.getId()==id) {
                gestoreMerci.modificaMerce(getNegozio(),min, prezzo,sconto,quantita);
            }
        }
    }

    /************************** Metodi Accessori ******************************/


}
