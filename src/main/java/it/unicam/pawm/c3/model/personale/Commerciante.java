package it.unicam.pawm.c3.model.personale;

import javax.persistence.Entity;

@Entity
public class Commerciante extends AddettoNegozio{

    public Commerciante(RuoloSistema ruolo) {
        super(ruolo);
    }

    public Commerciante(){}
}
