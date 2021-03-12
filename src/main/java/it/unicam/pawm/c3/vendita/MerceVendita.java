package it.unicam.pawm.c3.vendita;

import it.unicam.pawm.c3.merce.MerceAlPubblico;

import javax.persistence.*;

@Entity
public class MerceVendita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double prezzoTotaleVendita;
    private double quantitaVenduta;

    @OneToOne()
    @JoinColumn(name = "merceAlPubblico_fk", referencedColumnName = "id")
    private MerceAlPubblico merceAlPubblico;

    public MerceVendita(double prezzoTotaleVendita, double quantitaVenduta, MerceAlPubblico merceAlPubblico) {
        this.prezzoTotaleVendita = prezzoTotaleVendita;
        this.quantitaVenduta = quantitaVenduta;
        this.merceAlPubblico = merceAlPubblico;
    }

    public MerceVendita() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMerceAlPubblico(MerceAlPubblico merceAlPubblico) {
        this.merceAlPubblico = merceAlPubblico;
    }

    public double getPrezzoTotaleVendita() {
        return prezzoTotaleVendita;
    }

    public void setPrezzoTotaleVendita(double prezzoTotaleVendita) {
        this.prezzoTotaleVendita = prezzoTotaleVendita;
    }

    public double getQuantitaVenduta() {
        return quantitaVenduta;
    }

    public void setQuantitaVenduta(double quantitaVenduta) {
        this.quantitaVenduta = quantitaVenduta;
    }

    public MerceAlPubblico getMerceAlPubblico() {
        return merceAlPubblico;
    }
    @Override
    public String toString() {
        return prezzoTotaleVendita + ", " + quantitaVenduta + ", " + merceAlPubblico;
    }
}
