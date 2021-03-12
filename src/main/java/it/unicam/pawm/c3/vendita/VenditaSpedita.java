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
    private String indirizzoDiDomicilio;
    private String indirizzoNegozioDiRitiro;

    public VenditaSpedita(double prezzo, String indirizzoNegozioDiRitiro,  List<MerceVendita> listaMerceVendita) {
        super(prezzo, listaMerceVendita);
        this.statoConsegna = StatoConsegna.IN_ATTESA_DI_RITIRO;
        this.indirizzoNegozioDiRitiro = indirizzoNegozioDiRitiro;
        this.luogoDiRitiro = LuogoDiRitiro.NEGOZIO;
    }

    public VenditaSpedita(double prezzo, List<MerceVendita> listMerceVendita, String indirizzoDiDomicilio){
        super(prezzo,listMerceVendita);
        this.indirizzoDiDomicilio = indirizzoDiDomicilio;
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

    public String getIndirizzoDiDomicilio() {
        return indirizzoDiDomicilio;
    }

    public String getIndirizzoNegozioDiRitiro() {
        return indirizzoNegozioDiRitiro;
    }

    public void setIndirizzoNegozioDiRitiro(String indirizzoNegozioDiRitiro) {
        this.indirizzoNegozioDiRitiro = indirizzoNegozioDiRitiro;
    }

    public void setIndirizzoDiDomicilio(String indirizzoDiDomicilio) {
        this.indirizzoDiDomicilio = indirizzoDiDomicilio;
    }



}
