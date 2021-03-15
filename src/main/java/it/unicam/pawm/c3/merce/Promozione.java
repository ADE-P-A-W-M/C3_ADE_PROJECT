package it.unicam.pawm.c3.merce;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Promozione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInizio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFine;
    private double percentualeSconto;
    private boolean disponibile;
    private double prezzoPromozione;

    public Promozione(LocalDate dataInizio, LocalDate dataFine, double percentualeSconto) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.percentualeSconto = percentualeSconto;
        this.disponibile = true;
    }

    public Promozione(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public Promozione() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public double getPercentualeSconto() {
        return percentualeSconto;
    }

    public void setPercentualeSconto(double percentualeSconto) {
        this.percentualeSconto = percentualeSconto;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibilita) {
        this.disponibile = disponibilita;
    }

    public double getPrezzoPromozione() {
        return prezzoPromozione;
    }

    public void setPrezzoPromozione(double prezzoPromozione) {
        this.prezzoPromozione = prezzoPromozione;
    }

    @Override
    public String toString() {
        return "Promozione{" +
                "id=" + id +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", percentualeSconto=" + percentualeSconto +
                ", disponibile=" + disponibile +
                ", prezzoPromozione=" + prezzoPromozione +
                '}';
    }
}
