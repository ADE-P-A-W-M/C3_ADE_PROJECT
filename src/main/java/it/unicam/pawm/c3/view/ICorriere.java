package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCorrieri;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/corriere")
public class ICorriere {

    @Autowired
    private GestoreAccessi gestoreAccessi;
    @Autowired
    private GestoreCorrieri gestoreCorrieri;

    @Autowired
    public ICorriere() {
        this.gestoreCorrieri = new GestoreCorrieri();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        gestoreCorrieri.setCorriere(gestoreAccessi.homeCorriere(email));
        return "homeCorriere";
    }

    /************Interfaccia Consulta Inventario********************/

    @GetMapping("/gestioneInventarioCorriere")
    public String gestioneInventario(Model model){
        model.addAttribute("daRitirare",gestoreCorrieri.getVenditeDaRitirare());
        model.addAttribute("ritirate",gestoreCorrieri.getVenditeRitirate());
        model.addAttribute("consegnate",gestoreCorrieri.getVenditeConsegnate());
    return "gestioneInventarioCorriere";
}

    /************Interfaccia Preleva Vendita********************/

    @GetMapping("/venditeDaRitirare")
    public String getVenditeDaRitirare(Model model){
        model.addAttribute("daRitirare",gestoreCorrieri.getVenditeDaRitirare());
        return "venditeDaRitirare";
    }

    @GetMapping("/venditeDaRitirare/ritiro/{id}")
    public String prelevaVendita(@PathVariable Long id, Model model) {
        gestoreCorrieri.prelevaVendita(id);
        model.addAttribute("daRitirare", gestoreCorrieri.getVenditeDaRitirare());
        return "venditeDaRitirare";
    }

    /************Interfaccia Consegna Vendita********************/

    @GetMapping("/consegnavendita")
    public String getVenditePreseInCarico(Model model) {
        model.addAttribute("ritirate",gestoreCorrieri.getVenditeRitirate());
        return "venditeDaConsegnare";
   }

    @GetMapping("/consegna/{id}")
    public String consegnaVendita(@PathVariable Long id, Model model) {
        gestoreCorrieri.consegnaVendita(id);
        model.addAttribute("ritirate", gestoreCorrieri.getVenditeRitirate());
        return "venditeDaConsegnare";
    }
}

