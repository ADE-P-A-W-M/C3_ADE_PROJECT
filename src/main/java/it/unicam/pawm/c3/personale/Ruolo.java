package it.unicam.pawm.c3.personale;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ruolo{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private RuoloSistema ruolo;

    @OneToOne(mappedBy = "ruolo")
    private Cliente cliente;

    public Ruolo(RuoloSistema ruolo) {
        this.ruolo = ruolo;
    }

    public Ruolo() {
    }

    public long getId() {
        return id;
    }

    public RuoloSistema getRuoloSistema(){
        return ruolo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
