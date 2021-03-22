package it.unicam.pawm.c3.model.personale;

import javax.persistence.Entity;

@Entity
public class Amministratore extends Ruolo{

    public Amministratore(RuoloSistema ruolo) {
        super(ruolo);
    }

    public Amministratore(){

    }

}
