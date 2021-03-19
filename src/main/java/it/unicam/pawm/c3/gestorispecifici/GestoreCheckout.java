package it.unicam.pawm.c3.gestorispecifici;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.carta.Carta;
import it.unicam.pawm.c3.merce.MerceAlPubblico;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.vendita.MerceVendita;
import it.unicam.pawm.c3.vendita.Vendita;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class GestoreCheckout {

    @Autowired
    private MerceVenditaRepository merceVenditaRepository;
    @Autowired
    private VenditaRepository venditaRepository;
    @Autowired
    private VenditaSpeditaRepository venditaSpeditaRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private GestoreCarte gestoreCarte;
    @Autowired
    private GestoreMerci gestoreMerci;

    private List<MerceVendita> merciCarrello;
    private double prezzoCarrello;
//    public GestoreCheckout(MerceVenditaRepository merceVenditaRepository, VenditaRepository venditaRepository, VenditaSpeditaRepository venditaSpeditaRepository, NegozioRepository negozioRepository, ClienteRepository clienteRepository, RuoloRepository ruoloRepository, GestoreCarte gestoreCarte, GestoreMerci gestoreMerci) {
//        this.merceVenditaRepository = merceVenditaRepository;
//        this.venditaRepository = venditaRepository;
//        this.venditaSpeditaRepository = venditaSpeditaRepository;
//        this.negozioRepository = negozioRepository;
//        this.clienteRepository = clienteRepository;
//        this.ruoloRepository = ruoloRepository;
//        this.gestoreCarte = gestoreCarte;
//        this.gestoreMerci = gestoreMerci;
//        merciCarrello = new ArrayList<>();
//        prezzoCarrello = 0;
//    }
    @Autowired
    public GestoreCheckout() {
        merciCarrello = new ArrayList<>();
        prezzoCarrello = 0;
        gestoreCarte = new GestoreCarte();
        gestoreMerci = new GestoreMerci();
    }

    public List<MerceVendita> getMerciCarrello() {
        return this.merciCarrello;
    }

    public void addMerceCarrello(MerceVendita mv) {
        this.merciCarrello.add(mv);
    }

    public double getPrezzoCarrello(){
        return this.prezzoCarrello;
    }

    /**
     * Il metodo serve per inizializzare il carrello per essere cosi pronto ad un nuovo checkout, andando
     * a resettarne il prezzo e le merce presenti dentro
     *
     */
    public void startCarrello(){
        this.merciCarrello = new ArrayList<>();
        merciCarrello.clear();
        this.prezzoCarrello = 0;
    }

    /**
     * Il metodo serve per ottenere il prezzo della merce di cui è stato inserito l'id, moltiplicato per la quantita.
     * Se la merce non viene trovata all'interno del negozio viene restituito zero e sarà poi l'addetto ad inserire manualmente
     * il prezzo e lo sconto della merce
     *
     * @param id della merce
     * @param quantita della merce
     * @param negozio in cui si sta lavorando
     * @return prezzo unitario della merce multiplicato per la quantita scelta
     */
    public double getPrezzo(long id, double quantita, Negozio negozio){
        return Math.round((gestoreMerci.searchPrezzo(id, negozio, quantita)*quantita)*100.000)/100.000;
    }

    public double getSconto(long id, Negozio negozio) {
        return gestoreMerci.getSconto(id,negozio);
    }

    /**
     * Il metodo serve per calcolare il prezzo di una merce considerando lo sconto
     *
     * @param prezzo merce su cui verrà applicato lo sconto
     * @param sconto da applicare
     * @return prezzo scontato
     */
    public double calcolaPrezzoMerce(double prezzo, double sconto){
        double price = prezzo-((sconto/100)*prezzo);
        return price;
    }

    /**
     * Il metodo serve per calcolare il prezzo del carrello aggiungendo ogni volta il prezzo
     * della merce che viene inserita nel carrello
     *
     * @param prezzo della merce appena inserita
     * @param sconto dellla merce appena inserita
     * @return prezzo del carrello
     */
    public double calcolaPrezzoTotale(double prezzo,double sconto) {
        return prezzoCarrello + calcolaPrezzoMerce(prezzo,sconto);
    }

    /**
     * Il metodo serve per aggiungere la merce nel carrello. La merce viene recuperata, ne viene scalata la quantita
     * all'interno delle merci del negozio, viene aggiunta la merce nel carrello e ne viene calcolato il prezzo.
     *
     * @param prezzo della merce
     * @param sconto della merce
     * @param id della merce
     * @param quantita della merce
     * @param negozio in cui si sta lavorando
     * @return
     */
    public double aggiuntaMerceNelCarrello(double prezzo, double sconto, long id, double quantita, Negozio negozio) {
        MerceAlPubblico mp = gestoreMerci.getMerce(id,prezzo,quantita, negozio);
        gestoreMerci.scaloQuantita(mp,quantita, negozio);
        this.prezzoCarrello = calcolaPrezzoTotale(prezzo,sconto);
        MerceVendita mv = new MerceVendita(prezzo - ((sconto/100)*prezzo),quantita,mp);
        merceVenditaRepository.save(mv);
        addMerceCarrello(mv);
        return this.prezzoCarrello;
    }

    /**
     * Il metodo serve a calcolare il resto in base a quanto è il prezzo del carrello e ai soldi
     * che il cliente ha consegnato all'addetto
     *
     * @param denaro consegnato dal cliente all'addetto
     * @return il resto
     */
    public double calcoraResto(double denaro) {
        double resto = denaro - getPrezzoCarrello();
        return resto;
    }

    /**
     * Il metodo serve per completare il checkout e svuotare cosi il carrello
     */
    public void checkoutCompletato() {
        svuotaCarrello();
    }

    public void checkoutCompletato(long cc, Negozio negozio){
        registraAcquistoCliente(cc, negozio);
    }

    public void reinserimentoQuantita(Negozio negozio){
        gestoreMerci.reinserimentoQuantita(negozio, getMerciCarrello());
    }

    /**
     * Il metodo serve ad annullare il checkout.Quando l'addetto scegliarò di annullare il checkout
     * vera eliminato l'acquisto del cliente e verrà reinserita la quantita della merce di cui si stava
     * facendo il checkout
     *
     * @param negozio in cui si sta lavorando
     */
    public void annullaCheckout(Negozio negozio) {
        reinserimentoQuantita(negozio);
        Vendita v = venditaRepository.findTopByOrderByIdDesc();
        venditaRepository.delete(v);
        svuotaCarrello();
    }

    /**
     * Il metodo serve per andare a svuotare il carrello
     *
     */
    public void svuotaCarrello() {
        merciCarrello.clear();
        prezzoCarrello = 0;
    }

    public boolean verificaCodiceCarta(long cc, Negozio negozio){
        return gestoreCarte.verificaCodiceCarta(cc, negozio);
    }

    public long searchCodiceCartaFromEmail(String email, Negozio negozio){
        return gestoreCarte.searchCodiceCartaFromEmail(email, negozio);
    }

    /**
     * Il metodo serve per andare a vedere i corrieri che sono disponibili per andare ad effettuare consegne.
     *
     * @param negozio in cui si sta lavorando
     * @return lista dei corriere disponibili
     */
    public List<Corriere> getCorrieriDisponibili(Negozio negozio) {
        List<Corriere> corrieriDisponibiliList = new ArrayList<>();
        if(!negozio.getCorrieri().isEmpty()){
            Iterator<Corriere> corriereIterator = negozio.getCorrieri().iterator();
            while(corriereIterator.hasNext()){
                Corriere corriereDisponibile = corriereIterator.next();
                if(corriereDisponibile.isDisponibilitaRitiro()){
                    corrieriDisponibiliList.add(corriereDisponibile);
                }
            }
        }
        return corrieriDisponibiliList;
    }

    /**
     * Il metodo serve per andare a vedere i negozio che sono disponibili per essere scelti come punti di ritiro dell'acquisto appena
     * fatto dal cliente.
     *
     * @param negozio in cui si sta lavorando
     * @return lista dei negozio disponibili
     */
    public List<Negozio> getNegoziDisponibili(Negozio negozio) {
        List<Negozio> puntiDiRitiriDisponibiliList = new ArrayList<>();
        Iterator<Negozio> negozioIterator = negozioRepository.findAll().iterator();
        while (negozioIterator.hasNext()){
            Negozio negozioIterato = negozioIterator.next();
            if(negozioIterato.getDisponibilitaRitiro() && negozio.getId()!=negozioIterato.getId()){
                puntiDiRitiriDisponibiliList.add(negozioIterato);
            }
        }
        return puntiDiRitiriDisponibiliList;
    }

    /**
     * Il medoto serve per andare a registrare un vendita spedita che verrà messa negli acquisti del cliente.
     * Se la vendita che viene ricevuta possiede un indirizzo di domicilio la vendita sarà una vendita a domicilio
     * e quindi non verrà inserita in nessun negozio di ritiro. Se invece l'indirizzo non verrà recuperato allora
     * vuol dire che sarà una vendita da ritirare in un negozio di ritiro.
     *
     * @param cc codice carta del cliente
     * @param pdr negozio di ritiro
     * @param indirizzo indirizzo di domicilio
     * @param cr corriere incaricato della spedizione
     * @param negozio negozio in cui si lavora
     */
    public void registraAcquistoCliente(long cc, Negozio pdr, String indirizzo, Corriere cr, Negozio negozio) {
//        Carta carta = searchCarta(cc, negozio);
//        VenditaSpedita vs;
//        if(indirizzo.isEmpty()){
//            vs = new VenditaSpedita(getPrezzoCarrello(), pdr.getIndirizzo(), getMerciCarrello());
//            venditaSpeditaRepository.save(vs);
//            pdr.addVenditaInNegozioRitiro(vs);
//            negozioRepository.save(pdr);
//        } else {
//            vs = new VenditaSpedita(getPrezzoCarrello(),getMerciCarrello(),indirizzo);
//            venditaSpeditaRepository.save(vs);
//        }
//        venditaSpeditaRepository.save(vs);
//        carta.getCliente().getAcquisti().add(vs);
//        clienteRepository.save(carta.getCliente());
//        negozio.addVendita(vs);
//        cr.addMerceDaSpedire(vs);
//        negozioRepository.save(negozio);
//        ruoloRepository.save(cr);
    }

    /**
     * Il metodo serve per registrare acquisto del cliente. In questo caso sarà una semplice vendita che il
     * cliente ritirerà in loco.
     *
     * @param cc codice carta
     * @param negozio in cui si lavora
     */
    public void registraAcquistoCliente(long cc, Negozio negozio) {
        Carta carta = searchCarta(cc, negozio);
        Vendita v = new Vendita(getPrezzoCarrello(), getMerciCarrello());
        venditaRepository.save(v);
        carta.getCliente().getAcquisti().add(v);
        clienteRepository.save(carta.getCliente());
        negozio.addVendita(v);
        negozioRepository.save(negozio);
    }

    public Carta searchCarta(long cc, Negozio negozio) {
        return gestoreCarte.searchCarta(cc, negozio);
    }

    public double applyScontoCarta(long cc, Negozio negozio) {
        if(cc!=0){
            this.prezzoCarrello = prezzoCarrello-((gestoreCarte.calcolaScontoCarta(cc, negozio)/100) * prezzoCarrello);
        }
        return prezzoCarrello;
    }

    /**
     * Il metodo serve per registrare una semplice vendita nel negozio. Questo vuol dire che il cliente non
     * ha voluto registrare l'acquisto e quindi la vendita sarà semplicemente registrata tra le vendite del negozio.
     *
     * @param negozio in cui si lavora
     */
    public void addVenditaInventario(Negozio negozio) {
        Vendita v = new Vendita(getPrezzoCarrello(), getMerciCarrello());
        negozio.addVendita(v);
        venditaRepository.save(v);
        negozioRepository.save(negozio);
    }
}
