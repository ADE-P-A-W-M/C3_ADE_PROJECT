package it.unicam.pawm.c3.autenticazione.view;

import it.unicam.pawm.c3.autenticazione.gestori.GestoreIscrizione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IIscrizione {

    @Autowired
    private GestoreIscrizione ci;

    /******************Iscrizione cliente******************/

    public void iscrizione(String nome,String cognome,String email, String password){
        ci.iscrizione(nome,cognome,email,password);
    }

//    @FXML
    void iscrizioneButtonEvent() {
//        iscrizione(nomeIscrizione.getText(),cognomeIscrizione.getText(),emailIscrizione.getText(), passwordIscrizione.getText());
//        Stage stage1 = (Stage) iscrizioneButton.getScene().getWindow();
//        stage1.close();
    }

    public void setControllerIscrizione(GestoreIscrizione ci) {
        this.ci = ci;
    }

}
