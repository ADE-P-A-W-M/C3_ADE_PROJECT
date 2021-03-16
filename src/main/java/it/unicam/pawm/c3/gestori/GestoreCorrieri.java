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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreCorrieri {

    private Corriere corriere;
    @Autowired
    private GestoreVendite gestoreVendite;
    @Autowired
    private VenditaSpeditaRepository venditaSpeditarepository;

    @Autowired
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

    public List<VenditaSpedita> getVenditeDaRitirare() { return gestoreVendite.getVenditeDaRitirare(getCorriere()); }

    public List<VenditaSpedita> getVenditeRitirate() {
        return gestoreVendite.getVenditeRitirate(getCorriere());
    }

    public List<VenditaSpedita> getVenditeConsegnate() {
        return gestoreVendite.getVenditeConsegnate(getCorriere());
    }

    /************Consegna Vendita********************/

    public void consegnaVendita(Long id){
        Optional<VenditaSpedita> vs = venditaSpeditarepository.findById(id);
        if(vs.get().getLuogoDiRitiro().equals(LuogoDiRitiro.NEGOZIO)){
            gestoreVendite.aggiornaStatoVendita(vs.get(), StatoConsegna.CONSEGNATO_AL_NEGOZIO, getCorriere());
        } else if(vs.get().getLuogoDiRitiro().equals(LuogoDiRitiro.DOMICILIO)) {
            gestoreVendite.aggiornaStatoVendita(vs.get(),StatoConsegna.CONSEGNATO_AL_CLIENTE,getCorriere());
        }
    }

    /************Preleva Vendita********************/

    public void prelevaVendita(Long id) {
         Optional<VenditaSpedita> vs = venditaSpeditarepository.findById(id);
         gestoreVendite.aggiornaStatoVendita(vs.get(),StatoConsegna.RITIRATO, getCorriere());
    }


}
