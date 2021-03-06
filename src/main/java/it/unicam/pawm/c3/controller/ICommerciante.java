package it.unicam.pawm.c3.controller;

import it.unicam.pawm.c3.service.gestori.GestoreCommercianti;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.model.merce.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
@RequestMapping("/commerciante")
public class ICommerciante {

    @Autowired
    private GestoreCommercianti gestoreCommercianti;
    @Autowired
    private GestoreAccessi gestoreAccessi;

    public ICommerciante() {
        this.gestoreCommercianti = new GestoreCommercianti();
        this.gestoreAccessi = new GestoreAccessi();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        gestoreCommercianti.setNegozio(gestoreAccessi.homeAddetto(email));
        return "home/homeCommerciante";
    }

    /******************Interfaccia GestionePromozioni***************/

    @GetMapping("merceInPromozione")
    public String merceInPromozione(Model model) {
        model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
        return "commerciante/showPromozioni";
    }

    @GetMapping("merceInPromozione/delete/{id}")
    public String removePromozione(@PathVariable Long id,Model model) {
        gestoreCommercianti.rimuoviPromozione(id);
        model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
        return "commerciante/showPromozioni";
    }

    @GetMapping("merceNonInPromozione")
    public String merceNonInPromozione(Model model) {
        model.addAttribute("minList",gestoreCommercianti.getPromozioniPossibili());
        return "commerciante/showMerceNonInPromozione";
    }

    @GetMapping("merceNonInPromozione/formAddPromozione/{id}")
    public String addPromozioneForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "commerciante/addPromozione";
    }

    @PostMapping("merceInPromozione/addPromozione/{id}")
    public String addPromozione(@PathVariable("id") Long id, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInizio,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFine, Double percentualeSconto, Model model) {
        try {
            gestoreCommercianti.addPromozione(id,dataInizio,dataFine,percentualeSconto);
            model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
            return "commerciante/showPromozioni";
        } catch(Exception e) {
            model.addAttribute("alertAddPromozione","campi non validi");
            return "commerciante/addPromozione";
        }
    }
    /******************Interfaccia Gestione Corriere***************/

    @GetMapping("showCorrieriDaAggiungere")
    public String showCorrieriDaAggiunguere(Model model) {
        model.addAttribute("corrieriList",gestoreCommercianti.getCorrieri());
        return "commerciante/showCorrieriDaAggiungere";
    }

    @GetMapping("showCorrieriDaAggiungere/add/{id}")
    public ModelAndView addCorriere(@PathVariable Long id, ModelMap model) {
        gestoreCommercianti.addCorriere(id);
        return new ModelAndView("redirect:/commerciante/");
    }

    /******************Interfaccia assunzione addetto***************/

    @GetMapping("assunzioneAddetto")
    public String assunzioneForm() {
        return "commerciante/assunzioneAddetto";
    }

    @PostMapping ("assunzioneAddetto")
    public String assumiCliente(String email,Model model) {
        try {
            model.addAttribute("userList",gestoreCommercianti.getCliente(email));
            return "commerciante/clienteDaAssumere";
        } catch(Exception e) {
            model.addAttribute("alertAssunzioneAddetto","campi non validi");
            return "commerciante/assunzioneAddetto";
        }
    }

    @GetMapping("assunzioneAddetto/{id}")
    public String assunzioneFinita(@PathVariable Long id) {
         gestoreCommercianti.assunzioneAddetto(id);
        return "commerciante/assunzioneAddetto";
    }

    /****************Gestione inventario**************/
    @GetMapping("showInventario")
    public String showInventario(Model model) {
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "commerciante/showInventario";
    }

    @GetMapping("showInventario/removeForm/{id}")
    public String removeForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "commerciante/rimuoviMerce";
    }

    @GetMapping("showInventario/addMerceIdForm")
    public String addMerceIdForm() {
        return "commerciante/addMerceIdForm";
    }

    @GetMapping("showInventario/modificaMerceForm/{id}")
    public String modifcaMerceForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "commerciante/modificaMerceForm";
    }

    @PostMapping("showInventario/remove/{id}")
    public String removeFromInventario(@PathVariable Long id,Double quantita,Model model) {
        try {
            gestoreCommercianti.removeMerce(id,quantita);
            model.addAttribute("minList",gestoreCommercianti.getInventario());
            return "commerciante/showInventario";
        } catch(Exception e) {
            model.addAttribute("alertEliminaMerce","campi non validi");
            return "commerciante/rimuoviMerce";
        }
    }

    @PostMapping("showInventario/addMerceId")
    public String checkIfMerceExists(Long id,Model model) {
        if(id == null) {
            return "commerciante/addNewMerce";
        } else if(gestoreCommercianti.verificaIdMerce(id)) {
            model.addAttribute("id",id);
            return "commerciante/addMerceAlreadyExisting";
        } else {
            return "commerciante/addNewMerce";
        }
    }

    @PostMapping("showInventario/addMerce/{id}")
    public String addMerce(@PathVariable Long id,String nome,String descrizione,Categoria categoria,Double quantita,Double prezzo,Double sconto,Model model) {
        gestoreCommercianti.addMerce(id,nome,descrizione,categoria,quantita,prezzo,sconto);
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "commerciante/showInventario";
    }
    @PostMapping("showInventario/addMerce")
    public String addMerceNew(String nome,String descrizione,Categoria categoria,Double quantita,Double prezzo,Double sconto,Model model) {
        try {
            gestoreCommercianti.addMerce(nome,descrizione,categoria,quantita,prezzo,sconto);
            model.addAttribute("minList",gestoreCommercianti.getInventario());
            return "commerciante/showInventario";
        } catch(Exception e) {
            model.addAttribute("alertAddNewMerce","campi non validi");
            return "commerciante/addNewMerce";
        }
    }

    @PostMapping("showInventario/modificaMerce/{id}")
    public String modificaMerce(@PathVariable Long id,Model model,Double quantita,Double prezzo,Double sconto) {
        try {
            gestoreCommercianti.modificaMerce(id,prezzo,sconto,quantita);
            model.addAttribute("minList",gestoreCommercianti.getInventario());
            return "commerciante/showInventario";
        } catch(Exception e) {
            model.addAttribute("alertModificaMerce","campi non validi");
            return "commerciante/modificaMerceForm";
        }
    }
}
