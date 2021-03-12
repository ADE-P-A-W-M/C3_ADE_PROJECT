package it.unicam.pawm.c3.gestori;


import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.gestorispecifici.GestoreMerci;
import it.unicam.pawm.c3.merce.*;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.*;
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


    private Negozio negozio;
    private RuoloRepository ruoloRepository;
    private ClienteRepository clienteRepository;
    private NegozioRepository negozioRepository;
    private CorriereRepository corriereRepository;
    private GestoreMerci gestoreMerci;

    public GestoreCommercianti(GestoreMerci gestoreMerci, RuoloRepository ruoloRepository, ClienteRepository clienteRepository, NegozioRepository negozioRepository, CorriereRepository corriereRepository) {
        this.gestoreMerci = gestoreMerci;
        this.ruoloRepository = ruoloRepository;
        this.clienteRepository = clienteRepository;
        this.corriereRepository = corriereRepository;
        this.negozioRepository = negozioRepository;
    }

    /*****************Assegnazione Carta***************/

    public Cliente getCliente(String email){
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if(cliente.isPresent()){
            return cliente.get();
        }
        throw new IllegalStateException("cliente non presente");
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

    public void rimuoviPromozione(List<MerceInventarioNegozio> lista) {
        gestoreMerci.rimuoviPromozione(lista);
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
     * @param corrieriDaAggiungere al negozio
     */
    public void addCorrieri(List<Corriere> corrieriDaAggiungere) {
        Iterator<Corriere> corriereIterator = corrieriDaAggiungere.iterator();
        while(corriereIterator.hasNext()){
            Corriere corriere = corriereIterator.next();
            negozio.addCorriere(corriere);
        }
        negozioRepository.save(negozio);
    }

    /****************Assunzione Addetto*******************/

    /**
     * il metodo permette al commerciante di assumere un nuovo addetto per il negozio
     * @param cliente che diventerà  addetto
     */
    public void assunzioneAddetto(Cliente cliente){
        AddettoNegozio addettoNegozio = new AddettoNegozio(RuoloSistema.ADDETTONEGOZIO);
        cliente.setRuolo(addettoNegozio);
        ruoloRepository.save(addettoNegozio);
        clienteRepository.save(cliente);
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
        gestoreMerci.addMerce(id, nome,descrizione, categoria, quantita, prezzo , sconto , getNegozio());
    }

    public void removeMerce(List<MerceInventarioNegozio> list, double quantita){
        gestoreMerci.removeMerce(list, quantita, getNegozio());
    }

    public void modificaMerce(MerceInventarioNegozio min,double prezzo, double sconto, double quantita) {
        gestoreMerci.modificaMerce(min, prezzo,sconto,quantita);
    }

    /************************** Metodi Accessori ******************************/

    public Negozio getNegozio() {
        return this.negozio;
    }

    public void setNegozio(Negozio negozio){
        this.negozio = negozio;
    }

}
