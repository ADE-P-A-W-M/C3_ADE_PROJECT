package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreAmministratori;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.personale.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/registrazioneNegozio")
    public String searchUserBbyEmailNegozio() {
        return "amministratore/searchUserByEmail";
    }
    @PostMapping("/addNegozioForm")
    public String addNegozioForm(String email, Model model) {
        List<User> userList=new ArrayList<>();
        userList.add(gestoreAmministratori.ricercaCliente(email));
        model.addAttribute("userList",userList);
        return "amministratore/addNegozioForm";
    }
    @PostMapping("/addNegozio/{id}")
    public String addNegozio(@PathVariable Long id,Categoria categoria,String nomeDitta, String piva, String indirizzoRegistrazione, Model model) {
        gestoreAmministratori.registraNegozio(List.of(categoria),id,nomeDitta,piva,indirizzoRegistrazione);
        return "home/homeAmministratore";
    }
    @GetMapping("/registrazioneCorriere")
    public String searchUserBbyEmailCorriere() {
        return "amministratore/searchUserByEmailCorriere";
    }
    @PostMapping("/addCorriereForm")
    public String addCorriereForm(String email, Model model) {
        List<User> userList=new ArrayList<>();
        userList.add(gestoreAmministratori.ricercaCliente(email));
        model.addAttribute("userList",userList);
        return "amministratore/addCorriereForm";
    }
    @PostMapping("/addCorriere/{id}")
    public String addCorriere(@PathVariable Long id,String nomeDitta, String piva, String indirizzo, Model model) {
        gestoreAmministratori.registraCorriere(id,nomeDitta,piva,indirizzo);
        return "home/homeAmministratore";
    }

}
