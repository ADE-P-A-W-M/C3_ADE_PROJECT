package it.unicam.pawm.c3.model.personale;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "role")
public abstract class Ruolo{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private RuoloSistema ruolo;

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

}
