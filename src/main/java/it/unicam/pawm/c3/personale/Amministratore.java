package it.unicam.pawm.c3.personale;

import javax.persistence.Entity;

@Entity
public class Amministratore extends Ruolo{

    public Amministratore(RuoloSistema ruolo) {
        super(ruolo);
    }

    public Amministratore(){

    }

}
