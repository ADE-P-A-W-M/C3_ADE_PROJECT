package it.unicam.pawm.c3.personale;

import javax.persistence.Entity;

@Entity
public class Commerciante extends AddettoNegozio{

    public Commerciante(RuoloSistema ruolo) {
        super(ruolo);
    }

    public Commerciante(){}
}
