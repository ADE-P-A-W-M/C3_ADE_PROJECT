package it.unicam.pawm.c3.carta;

import it.unicam.pawm.c3.personale.Cliente;

import javax.persistence.*;

@Entity
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cliente_fk", referencedColumnName = "id")
    private Cliente cliente;

    @Enumerated(value = EnumType.STRING)
    private TipoScontoCliente tipoSconto;
    private long codice;
    private double sconto;

    public Carta(Cliente cliente, TipoScontoCliente tipoSconto) {
        this.cliente = cliente;
        this.tipoSconto = tipoSconto;
    }

    public Carta(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoScontoCliente getTipoSconto() {
        return tipoSconto;
    }

    public void setTipoSconto(TipoScontoCliente tipoSconto) {
        this.tipoSconto = tipoSconto;
    }

    public long getCodice() {
        return codice;
    }

    public void setCodice(long codice) {
        this.codice = codice;
    }

    public double getSconto(){
        switch (tipoSconto){
            case STUDENTE:
                sconto =  7;
                break;
            case LAVORATORE:
                sconto = 5;
                break;
        }
        return sconto;
    }

    public long createCodice() {
        int max = 100000000;
        int min = 1;
        int range = max - min + 1;
        long rand = (long)(Math.random() * range) + min;
        return rand;
        //this.setCodice(rand);
    }

    @Override
    public String toString() {
        return ", " + tipoSconto +
                ", " + codice
                ;
    }

}
