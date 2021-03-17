package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCorrieri;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


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
        return "home/homeCorriere";
    }

    /************Interfaccia Consulta Inventario********************/

    @GetMapping("/gestioneInventarioCorriere")
    public String gestioneInventario(Model model){
        model.addAttribute("daRitirare",gestoreCorrieri.getVenditeDaRitirare());
        model.addAttribute("ritirate",gestoreCorrieri.getVenditeRitirate());
        model.addAttribute("consegnate",gestoreCorrieri.getVenditeConsegnate());
        return "corriere/gestioneInventarioCorriere";
    }

    /************Interfaccia Preleva Vendita********************/

    @GetMapping("/venditeDaRitirare/ritiro/{id}")
    public ModelAndView prelevaVendita(@PathVariable Long id, ModelMap model) {
        gestoreCorrieri.prelevaVendita(id);
        return new ModelAndView("redirect:/corriere/gestioneInventarioCorriere", model);
    }

    /************Interfaccia Consegna Vendita********************/

    @GetMapping("/consegna/{id}")
    public ModelAndView consegnaVendita(@PathVariable Long id, ModelMap model) {
        gestoreCorrieri.consegnaVendita(id);
        return new ModelAndView("redirect:/corriere/gestioneInventarioCorriere", model);
    }
}

