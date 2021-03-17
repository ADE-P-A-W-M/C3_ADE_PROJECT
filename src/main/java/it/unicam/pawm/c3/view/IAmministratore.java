package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreAmministratori;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.persistenza.AmministratoreRepository;
import it.unicam.pawm.c3.persistenza.UserRepository;
import it.unicam.pawm.c3.personale.Amministratore;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Ruolo;
import it.unicam.pawm.c3.personale.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/amministratore")
public class IAmministratore {

    @Autowired
    private GestoreAmministratori gestoreAmministratori;
    @Autowired
    private GestoreAccessi gestoreAccessi;

    @Autowired
    public IAmministratore() {
        this.gestoreAmministratori = new GestoreAmministratori();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        gestoreAmministratori.setAmministratore(gestoreAccessi.homeAmministratore(email));
        return "home/homeAmministratore";
    }

    public void init() {
//        settoriList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        settoriList.getItems().addAll(Categoria.values());
    }
    @GetMapping("/registrazioneNegozio/searchUserByEmail")
    public String searchUserBbyEmailNegozio() {
        return "searchUserByEmail";
    }
    @PostMapping("/addNegozioForm")
    public String addNegozioForm(String email, Model model) {
        List<User> userList=new ArrayList<>();
        userList.add(gestoreAmministratori.ricercaCliente(email));
        model.addAttribute("userList",userList);
        return "addNegozioForm";
    }
    @PostMapping("/addNegozio/{id}")
    public String addNegozio(@PathVariable Long id,Categoria categoria,String nomeDitta, String piva, String indirizzoRegistrazione, Model model) {
        gestoreAmministratori.registraNegozio(List.of(categoria),id,nomeDitta,piva,indirizzoRegistrazione);
        return "home/homeAmministratore";
    }
    @GetMapping("/registrazioneCorriere/searchUserByEmail")
    public String searchUserBbyEmailCorriere() {
        return "searchUserByEmailCorriere";
    }
    @PostMapping("/addCorriereForm")
    public String addCorriereForm(String email, Model model) {
        List<User> userList=new ArrayList<>();
        userList.add(gestoreAmministratori.ricercaCliente(email));
        model.addAttribute("userList",userList);
        return "addCorriereForm";
    }
    @PostMapping("/addCorriere/{id}")
    public String addCorriere(@PathVariable Long id,String nomeDitta, String piva, String indirizzo, Model model) {
        gestoreAmministratori.registraCorriere(id,nomeDitta,piva,indirizzo);
        return "home/homeAmministratore";
    }
//    public Cliente ricercaCliente(String email) {
//        return gestoreAmministratori.ricercaCliente(email);
//    }
  //  public void registraNegozio(List<Categoria> categorie, Cliente cliente, String nomeDitta, String piva, String indirizzoRegistrazione) {
   //     gestoreAmministratori.registraNegozio(categorie,cliente,nomeDitta,piva,indirizzoRegistrazione);
  //  }
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
