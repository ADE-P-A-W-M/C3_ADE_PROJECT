package it.unicam.pawm.c3.personale;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AddettoNegozio extends Ruolo {

    public AddettoNegozio(RuoloSistema ruolo) {
        super(ruolo);
    }

    public AddettoNegozio(){}

}
