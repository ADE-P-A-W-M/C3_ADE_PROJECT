package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreAmministratori;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.personale.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IAmministratore {


    private GestoreAmministratori gestoreAmministratori;

    public void init() {
//        settoriList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        settoriList.getItems().addAll(Categoria.values());
    }
    public Cliente ricercaCliente(String email) {
        return gestoreAmministratori.ricercaCliente(email);
    }

    public void registraCorriere(Cliente cliente,String nomeDitta,String piva,String indirizzoRegistrazione) {
        gestoreAmministratori.registraCorriere(cliente,nomeDitta,piva,indirizzoRegistrazione);
    }
    public void registraNegozio(List<Categoria> categorie, Cliente cliente, String nomeDitta, String piva, String indirizzoRegistrazione) {
        gestoreAmministratori.registraNegozio(categorie,cliente,nomeDitta,piva,indirizzoRegistrazione);
    }
//    @FXML
    void confermaRegistraCorriereEvent() {
//        registraCorriere(listClientiRegistrazioneCorriere.getSelectionModel().getSelectedItem(),nomeDittaRegistrazioneCorriere.getText(),pivaRegistrazioneCorriere.getText(),indirizzoRegistrazioneCorriere.getText());
    }

//    @FXML
    void confermaRegistrazioneNegozioButtonEvent() {
//        registraNegozio(settoriList.getSelectionModel().getSelectedItems(),clientiList.getSelectionModel().getSelectedItem(),nomeNegozio.getText(),partitaIVA.getText(),indirizzoNegozio.getText());
    }

//    @FXML
    void ricercaEmailClienteButtonEvent() {
//        clientiList.getItems().clear();
//        try{
//            clientiList.getItems().add(ricercaCliente(emailClienteRicerca.getText()));
//        } catch (IllegalStateException e){
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Email non corrisponde a nessun cliente nel sistema", ButtonType.OK);
//            alert.show();
//        }
    }

//    @FXML
    void ricercaRegistrazioneCorriereEvent() {
//        listClientiRegistrazioneCorriere.getItems().add(ricercaCliente(emailRegistrazioneCorriere.getText()));
    }

    public void setGestoreAmministratore(GestoreAmministratori gestoreAmministratori) {
        this.gestoreAmministratori = gestoreAmministratori;
    }

}
