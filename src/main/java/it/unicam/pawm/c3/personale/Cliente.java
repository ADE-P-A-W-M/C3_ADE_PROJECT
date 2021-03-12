package it.unicam.pawm.c3.personale;


import it.unicam.pawm.c3.vendita.Vendita;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ruolo_fk", referencedColumnName = "id")
    private Ruolo ruolo;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "cliente_fk", referencedColumnName = "id")
    private List<Vendita> acquisti;

    public Cliente(String nome, String cognome, String email,String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.acquisti = new ArrayList<>();
    }

    public Cliente() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public List<Vendita> getAcquisti() {
        return acquisti;
    }

    public  void addAcquisto(Vendita vendita){
        this.acquisti.add(vendita);
    }

    @Override
    public String toString() {
        return id +
                ", " + nome +
                ", " + cognome +
                ", " + email;
    }
}
