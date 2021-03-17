package it.unicam.pawm.c3.merce;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MerceAlPubblico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private double prezzo;

    //TODO forse c'è da cambiare in ALL
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "merce_fk", referencedColumnName = "id")
    private Merce merce;
    private double sconto;

    //TODO forse c'è da cambiare in ALL
//    @OneToOne
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "promozione_fk", referencedColumnName = "id")
    private Promozione promozione;

    public MerceAlPubblico(double prezzo, Merce merce) {
        this.prezzo = prezzo;
        this.merce = merce;
        this.promozione = new Promozione(false);
    }

    public MerceAlPubblico(double prezzo, Merce merce, double sconto) {
        this.prezzo = prezzo;
        this.merce = merce;
        this.sconto = sconto;
        this.promozione = new Promozione(false);
    }

    public MerceAlPubblico() { }

    public Promozione getPromozione() {
        return promozione;
    }

    public void setPromozione(LocalDate di, LocalDate df, double pp, double prezzo){
        this.promozione.setDisponibile(true);
        this.promozione.setDataInizio(di);
        this.promozione.setDataFine(df);
        this.promozione.setPercentualeSconto(pp);
        this.promozione.setPrezzoPromozione(prezzo);
    }

    public double getPrezzo() {
        /*if(getPromozione().isDisponibile()){
            if(LocalDate.now().isAfter(getPromozione().getDataInizio()) && LocalDate.now().isBefore(getPromozione().getDataFine()))
                return promozione.getPrezzoPromozione();
        }*/
        return prezzo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public Merce getMerce() {
        return merce;
    }

    public void setMerce(Merce merce) {
        this.merce = merce;
    }

    public double getSconto() {
        return sconto;
    }

    public void setSconto(double sconto) {
        this.sconto = sconto;
    }

    @Override
    public String toString() {
        return "MerceAlPubblico{" +
                "id=" + id +
                ", prezzo=" + prezzo +
                ", merce=" + merce +
                ", sconto=" + sconto +
                ", promozione=" + promozione +
                '}';
    }
}
