package it.unicam.pawm.c3.merce;

import javax.persistence.*;

@Entity
public class MerceInventarioNegozio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double quantita;

    //TODO : forse c'è da cmabiare in ALL
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "merceAlPubblico_fk", referencedColumnName = "id")
    private MerceAlPubblico merceAlPubblico;

    public MerceInventarioNegozio(double quantita, MerceAlPubblico merceAlPubblico) {
        this.quantita=quantita;
        this.merceAlPubblico=merceAlPubblico;
    }

    public MerceInventarioNegozio() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public MerceAlPubblico getMerceAlPubblico() {
        return merceAlPubblico;
    }

    public void setMerceAlPubblico(MerceAlPubblico merceAlPubblico) {
        this.merceAlPubblico = merceAlPubblico;
    }

    /*@Override
    public String toString() {
        return merceAlPubblico.getMerce().getNome() +
                ", " + merceAlPubblico.getMerce().getDescrizione() +
                ", " + merceAlPubblico.getPrezzo() +
                ", " +quantita;
    }*/

    @Override
    public String toString() {
        return "MerceInventarioNegozio{" +
                "id=" + id +
                ", quantita=" + quantita +
                ", merceAlPubblico=" + merceAlPubblico +
                '}';
    }
}

