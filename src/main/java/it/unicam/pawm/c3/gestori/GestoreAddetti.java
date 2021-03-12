package it.unicam.pawm.c3.gestori;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.carta.TipoScontoCliente;
import it.unicam.pawm.c3.gestorispecifici.GestoreCarte;
import it.unicam.pawm.c3.gestorispecifici.GestoreCheckout;
import it.unicam.pawm.c3.gestorispecifici.GestoreMerci;
import it.unicam.pawm.c3.gestorispecifici.GestoreVendite;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.vendita.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreAddetti {

    private Negozio negozio;
    private ClienteRepository clienteRepository;
    private GestoreCheckout gestoreCheckout;
    private GestoreCarte gestoreCarte;
    private GestoreVendite gestoreVendite;
    private GestoreMerci gestoreMerci;
    private NegozioRepository negozioRepository;

    public GestoreAddetti(ClienteRepository clienteRepository, GestoreCheckout gestoreCheckout, GestoreCarte gestoreCarte, GestoreVendite gestoreVendite, GestoreMerci gestoreMerci, NegozioRepository negozioRepository) {
        this.clienteRepository = clienteRepository;
        this.gestoreCheckout = gestoreCheckout;
        this.gestoreCarte = gestoreCarte;
        this.gestoreVendite = gestoreVendite;
        this.gestoreMerci = gestoreMerci;
        this.negozioRepository = negozioRepository;
    }

    public Negozio getNegozio(){
        return this.negozio;
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

    public void checkoutCompletato() {
        gestoreCheckout.checkoutCompletato();
    }

    public void annullaCheckout() {
        gestoreCheckout.annullaCheckout(getNegozio());
    }

    public double applyScontoCarta(long cc) {
        return gestoreCheckout.applyScontoCarta(cc, getNegozio());
    }

    public void addVenditaInventario() {
        gestoreCheckout.addVenditaInventario(getNegozio());
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

    public void registraAcquistoCliente(long cc, Negozio pdr, String indirizzo, Corriere cr) {
        gestoreCheckout.registraAcquistoCliente(cc, pdr, indirizzo, cr, getNegozio());
    }

    public void registraAcquistoCliente(long cc) {
        gestoreCheckout.registraAcquistoCliente(cc, getNegozio());
    }

    /*****************Assegnazione Carta***************/

    public Cliente getCliente(String email){
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if(cliente.isPresent()){
            return cliente.get();
        }
        throw new IllegalStateException("cliente non presente");
    }

    public long assegnaCarta(Cliente cliente, TipoScontoCliente tsc){
        long cc = gestoreCarte.assegnaCarta(cliente,tsc, getNegozio());
        negozioRepository.save(negozio);
        return cc;
    }

    /*********Consulta Inventario****************/

    public List<MerceInventarioNegozio> getInventario() {
        return getNegozio().getMerceInventarioNegozio();
    }

    public String getInfoMerce(MerceInventarioNegozio min) {
        return gestoreMerci.getInfoMerce(min);
    }

    /************Consegna Vendita Assegnata******************/

    public List<VenditaSpedita> getAcquistiClienteDaRitirare(String email) {
        return gestoreVendite.getAcquistiClienteDaRitirare(email, getNegozio());
    }

    public void confermaConsegnaVenditaAssegnata(List<VenditaSpedita> vendite) {
        gestoreVendite.confermaConsegnaVenditaAssegnata(vendite);
    }

    /************************ Fine Consegna Vendita Assegnata**********************/

    public void setNegozio(Negozio negozio){
        this.negozio = negozio;
    }

}
