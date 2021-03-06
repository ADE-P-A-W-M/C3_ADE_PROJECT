package it.unicam.pawm.c3.service.gestori;

import it.unicam.pawm.c3.model.Negozio;
import it.unicam.pawm.c3.model.carta.TipoScontoCliente;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreCarte;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreCheckout;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreMerci;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreVendite;
import it.unicam.pawm.c3.model.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.repository.*;
import it.unicam.pawm.c3.model.personale.*;
import it.unicam.pawm.c3.model.vendita.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreAddetti {


    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GestoreCheckout gestoreCheckout;
    @Autowired
    private GestoreCarte gestoreCarte;
    @Autowired
    private GestoreVendite gestoreVendite;
    @Autowired
    private GestoreMerci gestoreMerci;

    private Negozio negozio;

    @Autowired
    public GestoreAddetti() {
        this.gestoreCheckout = new GestoreCheckout();
        this.gestoreCarte = new GestoreCarte();
        this.gestoreVendite = new GestoreVendite();
        this.gestoreMerci = new GestoreMerci();
    }

    public Negozio getNegozio(){
        return this.negozio;
    }

    public void setNegozio(Negozio negozio){
        this.negozio = negozio;
    }


    /********** Checkout Merce *********/

    public void startCarrello(){
        gestoreCheckout.startCarrello();
    }

    public double getPrezzo(long id, double quantita) {
        return gestoreCheckout.getPrezzo(id,quantita,getNegozio());
    }

    public double getSconto(long id) {
        return gestoreCheckout.getSconto(id,getNegozio());
    }

    public double aggiuntaMerceNelCarrello(double prezzo, double sconto, long id, double quantita) {
        return gestoreCheckout.aggiuntaMerceNelCarrello(prezzo, sconto , id, quantita , getNegozio());
    }

    public double calcoraResto(double denaro) {
        return gestoreCheckout.calcoraResto(denaro);
    }

    public void checkoutCompletato(long cc) {
        gestoreCheckout.checkoutCompletato(cc, getNegozio());
    }

    public void annullaCheckout() {
        gestoreCheckout.annullaCheckout(getNegozio());
    }

    public double applyScontoCarta(long cc) {
        return gestoreCheckout.applyScontoCarta(cc, getNegozio());
    }

    /****************Richiesta Carta*******************/

    public boolean verificaCodiceCarta(long cc) {
        return gestoreCheckout.verificaCodiceCarta(cc,getNegozio());
    }

    public long searchCodiceCartaFromEmail(String email) {
        return gestoreCheckout.searchCodiceCartaFromEmail(email, getNegozio());
    }


    /*****************Registra Vendita********************/

    public List<Corriere> getCorrieriDisponibili() {
        return gestoreCheckout.getCorrieriDisponibili(getNegozio());
    }

    public List<Negozio> getNegoziDisponibili() {
        return gestoreCheckout.getNegoziDisponibili(getNegozio());
    }

    public void registraAcquistoCliente(Long idCliente, Long idCorriere, Long idNegozioRitiro) {
        gestoreCheckout.registraAcquistoCliente(idCliente,idCorriere,idNegozioRitiro,getNegozio());
    }
    public void registraAcquistoCliente(Long idCliente, Long idCorriere, String indirizzoDomicilio) {
        gestoreCheckout.registraAcquistoCliente(idCliente,idCorriere,indirizzoDomicilio,getNegozio());
    }
    /*****************Assegnazione Carta***************/

    /**
     * Il metodo serve per cercare un user tramite la sua email
     *
     * @param email dello user da cercare
     * @return lista di user con quella email
     */
    public List<User> getCliente(String email){
        List<User> userList = new ArrayList<>();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            userList.add(user.get());
            return userList;
        }
        negozioRepository.save(getNegozio());
        throw new IllegalStateException("cliente non presente");
    }

    /**
     * Il metodo serve per cercare un user tramite il suo id
     *
     * @param id dello user da cercare
     * @return lista di user con quel id
     */
    public List<User> getCliente(Long id){
        List<User> userList = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userList.add(user.get());
            return userList;
        }
        negozioRepository.save(getNegozio());
        throw new IllegalStateException("cliente non presente");
    }

    /**
     * Il metodo serve per assegnare la carta a un determinato user
     *
     * @param id dello user
     * @param tsc della carta
     * @return codice della carta assegnata
     */
    public long assegnaCarta(Long id, TipoScontoCliente tsc){
        long cc=0L;
        Iterator<User> userList=userRepository.findAll().iterator();
        while(userList.hasNext()) {
            User user= userList.next();
            if(user.getId()==id) {
                Iterator<Ruolo> ruoloList=user.getRuolo().iterator();
                while(ruoloList.hasNext()) {
                    Ruolo ruolo= ruoloList.next();
                    if(ruolo.getRuoloSistema()== RuoloSistema.CLIENTE) {
                        Optional<Cliente> cliente=clienteRepository.findById(ruolo.getId());
                        cc = gestoreCarte.assegnaCarta(cliente.get(),tsc, getNegozio());
                    }
                }
            }
        }
        negozioRepository.save(negozio);
        return cc;
    }

    /*********Consulta Inventario****************/

    public List<MerceInventarioNegozio> getInventario() {
        return getNegozio().getMerceInventarioNegozio();
    }

    /**
     * Il metodo serve per selezionare una merce in base al suo id
     *
     * @param id della merce da cercare
     * @return lista di merce con quel id
     */
    public List<MerceInventarioNegozio> getInfoMerce(Long id) {
        MerceInventarioNegozio min = gestoreMerci.getInfoMerce(id);
        List<MerceInventarioNegozio> minList = new ArrayList<>();
        minList.add(min);
        return minList;
    }

    /************Consegna Vendita Assegnata******************/

    public List<VenditaSpedita> getAcquistiClienteDaRitare(Long id) {
        return gestoreVendite.getAcquistiClienteDaRitirare(id,getNegozio());
    }

    public void confermaConsegnaVenditaAssegnata(Long id){
        gestoreVendite.confermaConsegnaVenditaAssegnata(id, getNegozio());
    }


    public Long getClienteFromCodiceCarta(Long codiceCarta) {
        return gestoreCarte.getClienteFromCodiceCarta(codiceCarta, getNegozio());
    }

}
