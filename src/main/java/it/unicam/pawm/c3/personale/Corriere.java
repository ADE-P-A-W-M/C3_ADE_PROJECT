package it.unicam.pawm.c3.personale;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.vendita.StatoConsegna;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Corriere extends Ruolo{

    private String nomeDitta;
    private String indirizzo;
    private String p_iva;
    private boolean disponibilitaRitiro;
    private boolean disponibilitaAssociazione;

    @OneToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "corriere_fk", referencedColumnName = "id")
    private List<VenditaSpedita> vendite;

    public Corriere(RuoloSistema ruolo, String nomeDitta, String indirizzo, String p_iva) {
        super(ruolo);
        this.nomeDitta = nomeDitta;
        this.indirizzo = indirizzo;
        this.p_iva = p_iva;
        this.vendite = new ArrayList<>();
        this.disponibilitaRitiro = true;
        disponibilitaAssociazione = true;
    }

    public Corriere (){}

    public String getNomeDitta() {
        return nomeDitta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getP_iva(){
        return p_iva;
    }

    public boolean isDisponibilitaRitiro() {
        return disponibilitaRitiro;
    }

    public boolean isDisponibilitaAssociazione() {
        return disponibilitaAssociazione;
    }

    public void setDisponibilitaAssociazione(boolean disponibilitaAssociazione) {
        this.disponibilitaAssociazione = disponibilitaAssociazione;
    }

    public List<VenditaSpedita> getVendite() {
        return vendite;
    }

    public void addMerceDaSpedire(VenditaSpedita vs){
        this.vendite.add(vs);
    }

    public List<VenditaSpedita> getVenditePerStato(StatoConsegna sc) {
        List<VenditaSpedita> venditePerTipo = new ArrayList<>();
        Iterator<VenditaSpedita> iterator = getVendite().iterator();
        while (iterator.hasNext()){
            VenditaSpedita vs = iterator.next();
            if(vs.getStatoConsegna().equals(sc)){
                venditePerTipo.add(vs);
            }
        }
        return venditePerTipo;
    }

    public void setDisponibilitaRitiro(boolean disponiblita){
        this.disponibilitaRitiro = disponiblita;
    }

    @Override
    public String toString() {
        return nomeDitta + '\'' +
                ", " + indirizzo + '\'' +
                ", " + p_iva;
    }
}
