package it.unicam.pawm.c3.gestori;

import it.unicam.pawm.c3.gestorispecifici.GestoreVendite;
import it.unicam.pawm.c3.persistenza.VenditaSpeditaRepository;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.vendita.LuogoDiRitiro;
import it.unicam.pawm.c3.vendita.StatoConsegna;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class GestoreCorrieri {

    private Corriere corriere;
    private GestoreVendite gestoreVendite;
    @Autowired
    private VenditaSpeditaRepository venditaSpeditarepository;

    public GestoreCorrieri() {
        this.gestoreVendite = new GestoreVendite();
    }

    public void setCorriere(Corriere corriere){
        this.corriere = corriere;
    }

    public Corriere getCorriere() {
        return corriere;
    }

    /************Consulta Inventario********************/
    public List<VenditaSpedita> getVenditeDaRitirare() {
        return gestoreVendite.getVenditeDaRitirare(getCorriere());
    }

    public List<VenditaSpedita> getVenditeRitirate() {
        return gestoreVendite.getVenditeRitirate(getCorriere());
    }

    public List<VenditaSpedita> getVenditeConsegnate() {
        return gestoreVendite.getVenditeConsegnate(getCorriere());
    }

    /************Consegna Vendita********************/

    /**
     * Il metodo serve per aggiornare lo stato della vendita da RITIRATO a CONSEGNATO_AL_NEGOZIO oppure CONSEGNATO_AL_CLIENTE
     *
     * @param list lista delle vendite alle quali verrà aggiornato lo stato
     */
    public void consegnaVendita(List<VenditaSpedita> list) {
        Iterator<VenditaSpedita> iterator = list.iterator();
        while (iterator.hasNext()) {
            VenditaSpedita vs = iterator.next();
            if (vs.getLuogoDiRitiro().equals(LuogoDiRitiro.NEGOZIO)) {
                gestoreVendite.aggiornaStatoVendita(list,StatoConsegna.CONSEGNATO_AL_NEGOZIO, getCorriere());
            } else if(vs.getLuogoDiRitiro().equals(LuogoDiRitiro.DOMICILIO)) {
                gestoreVendite.aggiornaStatoVendita(list,StatoConsegna.CONSEGNATO_AL_CLIENTE,getCorriere());
            }
            venditaSpeditarepository.save(vs);
        }
    }

    /************Preleva Vendita********************/

    /**
     * Il metodo serve per aggiornare lo stato della vendita da IN_ATTESA_DI_RITIRO a RITIRATO
     *
     * @param list lista delle merci che che verranno prelevate
     */
    public void prelevaVendita(List<VenditaSpedita> list) {
        gestoreVendite.aggiornaStatoVendita(list,StatoConsegna.RITIRATO, getCorriere());
        venditaSpeditarepository.saveAll(list);
    }


}
