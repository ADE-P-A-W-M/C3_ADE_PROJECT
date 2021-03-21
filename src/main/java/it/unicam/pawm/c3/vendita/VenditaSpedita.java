package it.unicam.pawm.c3.vendita;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Entity
public class VenditaSpedita extends Vendita{

    @Enumerated(value = EnumType.STRING)
    private StatoConsegna statoConsegna;
    @Enumerated(value = EnumType.STRING)
    private LuogoDiRitiro luogoDiRitiro;
    //private String indirizzoDiDomicilio;
    private String indirizzoDiConsegna;
    private String indirizzoDiRitiro;

    public VenditaSpedita(double prezzo,String indirizzoDiRitiro, String indirizzoDiConsegna, List<MerceVendita> listaMerceVendita) {
        super(prezzo, listaMerceVendita);
        this.indirizzoDiRitiro=indirizzoDiRitiro;
        this.statoConsegna = StatoConsegna.IN_ATTESA_DI_RITIRO;
        this.indirizzoDiConsegna = indirizzoDiConsegna;
        this.luogoDiRitiro = LuogoDiRitiro.NEGOZIO;
    }

    public VenditaSpedita(double prezzo,String indirizzoDiRitiro, List<MerceVendita> listMerceVendita, String indirizzoDiConsegna){
        super(prezzo,listMerceVendita);
        this.indirizzoDiRitiro=indirizzoDiRitiro;
        this.indirizzoDiConsegna = indirizzoDiConsegna;
        this.statoConsegna = StatoConsegna.IN_ATTESA_DI_RITIRO;
        this.luogoDiRitiro = LuogoDiRitiro.DOMICILIO;
    }

    public VenditaSpedita() {

    }

    public StatoConsegna getStatoConsegna() {
        return statoConsegna;
    }

    public void setStatoConsegna(StatoConsegna statoConsegna) {
        this.statoConsegna = statoConsegna;
    }

    public LuogoDiRitiro getLuogoDiRitiro() {
        return luogoDiRitiro;
    }

    public void setLuogoDiRitiro(LuogoDiRitiro luogoDiRitiro) {
        this.luogoDiRitiro = luogoDiRitiro;
    }

    public String getIndirizzoDiConsegna() {
        return indirizzoDiConsegna;
    }

    public void setIndirizzoDiConsegna(String indirizzoNegozioDiRitiro) {
        this.indirizzoDiConsegna = indirizzoNegozioDiRitiro;
    }

    public String getIndirizzoDiRitiro() {
        return indirizzoDiRitiro;
    }

    public void setIndirizzoDiRitiro(String indirizzoDiRitiro) {
        this.indirizzoDiRitiro = indirizzoDiRitiro;
    }
}
