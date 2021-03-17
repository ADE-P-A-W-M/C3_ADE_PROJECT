package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreClienti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/cliente")
public class ICliente {

    @Autowired
    private GestoreAccessi gestoreAccessi;
    @Autowired
    private GestoreClienti gestoreClienti;

    @Autowired
    public ICliente() {
        this.gestoreClienti = new GestoreClienti();
        this.gestoreAccessi = new GestoreAccessi();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        gestoreClienti.setCliente(gestoreAccessi.homeCliente(email));
        return "home/homeCliente";
    }
    /*****************Consulta Promozioni******************/
    @GetMapping("promozione")
    public String selezionaCategoria(Model model) {
        model.addAttribute("minList",gestoreClienti.getPromozioni());
        return "cliente/showCategoriePromozioni";
    }

    @PostMapping("promozioniFiltrate")
    public String filtraPromozioni(Categoria categoria, Model model){
        model.addAttribute("minList",gestoreClienti.filtraPromozioniPerCategoria(categoria));
        return "cliente/promozioniPerCategoria";
    }
    /*****************Ricerca Prodotto******************/
    @GetMapping("ricercaProdottoForm")
    public String ricercaProdottoForm() {
        return "cliente/ricercaProdottoForm";
    }

    @PostMapping("ricerca")
    public String filtraPromozioni(String nomeRicerca, Model model){
        model.addAttribute("negList",gestoreClienti.ricercaProdotto(nomeRicerca));
        return "cliente/ricercaProdotto";
    }
}
