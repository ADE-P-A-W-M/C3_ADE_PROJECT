package it.unicam.pawm.c3.vendita;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Vendita {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private LocalDate data;
    private double prezzo;

    @OneToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="vendita_fk", referencedColumnName = "id")
    private List<MerceVendita> listaMerceVendita;

    public Vendita(double prezzo, List<MerceVendita> listaMerceVendita) {
        this.data = LocalDate.now();
        this.prezzo = prezzo;
        this.listaMerceVendita=listaMerceVendita;
    }

    public Vendita() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public List<MerceVendita> getListaMerceVendita() {
        return listaMerceVendita;
    }

    public void setListaMerceVendita(List<MerceVendita> listaMerceVendita) {
        this.listaMerceVendita = listaMerceVendita;
    }

    @Override
    public String toString() {
        return id +
                ", " + data +
                ", " + prezzo;
    }
}