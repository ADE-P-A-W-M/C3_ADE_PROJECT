package it.unicam.pawm.c3.controller;

import it.unicam.pawm.c3.service.gestori.GestoreAmministratori;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.model.merce.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

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
        try{
            model.addAttribute("userList",gestoreAmministratori.searchCliente(email));
            return "amministratore/addNegozioForm";
        } catch (Exception e) {
            model.addAttribute("alertUserPerNegozio", "utente non presente o incorretto");
            return "amministratore/searchUserByEmail";
        }
    }

    @PostMapping("/addNegozio/{id}")
    public String addNegozio(@PathVariable Long id,Categoria categoria,String nomeDitta, String piva, String indirizzoRegistrazione, Model model) {
        gestoreAmministratori.registraNegozio(Set.of(categoria),id,nomeDitta,piva,indirizzoRegistrazione);
        return "home/homeAmministratore";
    }

    @GetMapping("/registrazioneCorriere")
    public String searchUserBbyEmailCorriere() {
        return "amministratore/searchUserByEmailCorriere";
    }

    @PostMapping("/addCorriereForm")
    public String addCorriereForm(String email, Model model) {
        try{
            model.addAttribute("userList",gestoreAmministratori.searchCliente(email));
            return "amministratore/addCorriereForm";
        } catch (Exception e){
            model.addAttribute("alertUserPerCorriere", "utente non presente o incorretto");
            return "amministratore/searchUserByEmailCorriere";
        }
    }

    @PostMapping("/addCorriere/{id}")
    public String addCorriere(@PathVariable Long id,String nomeDitta, String piva, String indirizzo, Model model) {
        gestoreAmministratori.registraCorriere(id,nomeDitta,piva,indirizzo);
        return "home/homeAmministratore";
    }

}
