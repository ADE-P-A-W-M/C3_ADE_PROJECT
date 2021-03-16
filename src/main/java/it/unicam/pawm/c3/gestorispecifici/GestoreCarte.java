package it.unicam.pawm.c3.gestorispecifici;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.carta.Carta;
import it.unicam.pawm.c3.carta.TipoScontoCliente;
import it.unicam.pawm.c3.persistenza.CartaRepository;
import it.unicam.pawm.c3.personale.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;

@Service
@Transactional
public class GestoreCarte {

    @Autowired
    private CartaRepository cartaRepository;



    /**
     * Il metodo serve ad assegnare un nuova carta al cliente. La nuova carta viene create, viene assegnata al cliente
     * e ne viene assegnato il codice. La carta viene inoltre aggiunta al corrispettivo negozio.
     *
     * @param cliente a cui verrà assegnata la carta
     * @param tsc tipo di sconto corrispettivo del cliente
     * @param negozio in cui si sta lavorando
     * @return codice della carta
     */
    public long assegnaCarta(Cliente cliente, TipoScontoCliente tsc, Negozio negozio){
        Carta carta= new Carta(cliente,tsc);
        generateCodCarta(carta,negozio);
        cartaRepository.save(carta);
        negozio.addCarta(carta);
        return carta.getCodice();
    }

    /**
     * Il metodo serve a generare il codice di una carta che dovrà essere univoco all'interno del negozio in
     * cui andrà a finire la carta
     * @param carta sul quale si genera il codice
     * @param negozio sul quale si sta lavorando
     */
    public void generateCodCarta(Carta carta,Negozio negozio){
        long rand = carta.createCodice();
        Iterator<Carta> carte = negozio.getCarte().iterator();
        while(carte.hasNext()){
            if(carte.next().getCodice() == rand){
                generateCodCarta(carta,negozio);
            }
        }
        carta.setCodice(rand);
    }

    /**
     * Il metodo serve per verificare la validita del codice di una carta.
     *
     * @param cc codice carta da verificare
     * @param negozio in cui si sta lavorando
     * @return validita del codice
     */
    public boolean verificaCodiceCarta(long cc , Negozio negozio) {
        if(!negozio.getCarte().isEmpty()) {
            Iterator<Carta> it = negozio.getCarte().iterator();
            while (it.hasNext()) {
                Carta c = it.next();
                if (c.getCodice() == cc) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Il metodo serve per andare a recuperare il codice della carta di un cliente a partire
     * dall'email di un cliente iscritto alla piattaforma.
     *
     * @param email del cliente di cui verrà cercata la carta
     * @param negozio in cui si sta lavorando
     * @return il codice della carta del cliente o in mancanza di risultato positivo verà ritornato zero
     */
    public long searchCodiceCartaFromEmail(String email,Negozio negozio) {
//        if(!negozio.getCarte().isEmpty()) {
//            Iterator<Carta> it = negozio.getCarte().iterator();
//            while (it.hasNext()) {
//                Carta c = it.next();
//                if (c.getCliente().getEmail().equals(email)) {
//                    return c.getCodice();
//                }
//            }
//        }
        return 0;
    }

    /**
     * Il metodo serve per andare a cercare un carta tramite il codice
     * @param cc codice carta
     * @param negozio in cui si lavora
     * @return la carta corrispondente al codice
     */
    public Carta searchCarta(long cc,Negozio negozio) {
        if(!negozio.getCarte().isEmpty()){
            Iterator<Carta> carte = negozio.getCarte().iterator();
            while(carte.hasNext()){
                Carta carta = carte.next();
                if(carta.getCodice() == cc){
                    return carta;
                }
            }
        }
        return null;
    }

    /**
     * Il metodo serve per ottenere lo sconto di una carta che varia a seconda del tipo del cliente
     *
     * @param cc codice carta
     * @param negozio in cui si lavora
     * @return sconto della carta
     */
    public double calcolaScontoCarta(long cc,Negozio negozio) {
        if(!negozio.getCarte().isEmpty()){
            Iterator<Carta> carte = negozio.getCarte().iterator();
            while(carte.hasNext()){
                Carta carta = carte.next();
                if(carta.getCodice() == cc){
                    return carta.getSconto();
                }
            }
        }
        return 0;
    }
}
