package it.unicam.pawm.c3.personale;

import it.unicam.pawm.c3.vendita.Vendita;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name =  "user"/*, uniqueConstraints = @UniqueConstraint(columnNames = "email")*/)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome")
    private String cognome;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Ruolo> ruolo;

//    @OneToMany
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @JoinColumn(name = "cliente_fk", referencedColumnName = "id")
//    private List<Vendita> acquisti;

    public User(String nome, String cognome, String email,String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
//        this.acquisti = new ArrayList<>();
        this.ruolo = new ArrayList<>();
    }

    public User() {
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

//    public Ruolo getRuolo() {
//        return ruolo;
//    }
//
//    public void setRuolo(Ruolo ruolo) {
//        this.ruolo = ruolo;
//    }


    public List<Ruolo> getRuolo() {
        return ruolo;
    }

    public void setRuolo(List<Ruolo> ruolo) {
        this.ruolo = ruolo;
    }

    public void setRuolo(Ruolo ruolo){
        this.ruolo.add(ruolo);
    }

//    public List<Vendita> getAcquisti() {
//        return acquisti;
//    }
//
//    public  void addAcquisto(Vendita vendita){
//        this.acquisti.add(vendita);
//    }

    @Override
    public String toString() {
        return id +
                ", " + nome +
                ", " + cognome +
                ", " + email;
    }
}