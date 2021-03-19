package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCommercianti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        gestoreCommercianti.addPromozione(id,dataInizio,dataFine,percentualeSconto);
        model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
        return "commerciante/showPromozioni";
    }
    /******************Interfaccia Gestione Corriere***************/

    @GetMapping("showCorrieriDaAggiungere")
    public String showCorrieriDaAggiunguere(Model model) {
        model.addAttribute("corrieriList",gestoreCommercianti.getCorrieri());
        return "commerciante/showCorrieriDaAggiungere";
    }

    @GetMapping("showCorrieriDaAggiungere/add/{id}")
    public String addCorriere(@PathVariable Long id,Model model) {
        System.out.println(id);
        gestoreCommercianti.addCorriere(id);
        System.out.println(gestoreCommercianti.getCorrieri());
        model.addAttribute("corrieriList",gestoreCommercianti.getCorrieri());
        return "commerciante/showCorrieriDaAggiungere";
    }

    /******************Interfaccia assunzione addetto***************/

    @GetMapping("assunzioneAddetto")
    public String assunzioneForm() {
        return "commerciante/assunzioneAddetto";
    }

    @PostMapping ("assunzioneAddetto")
    public String assumiCliente(String email,Model model) {
        model.addAttribute("userList",gestoreCommercianti.getCliente(email));
        return "commerciante/clienteDaAssumere";
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
        gestoreCommercianti.removeMerce(id,quantita);
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "commerciante/showInventario";
    }

    @PostMapping("showInventario/addMerceId")
    public String checkIfMerceExists(Long id,Model model) {
        model.addAttribute("id",id);
        if(gestoreCommercianti.verificaIdMerce(id)) {
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

    @PostMapping("showInventario/modificaMerce/{id}")
    public String modificaMerce(@PathVariable Long id,Model model,Double quantita,Double prezzo,Double sconto) {
        gestoreCommercianti.modificaMerce(id,prezzo,sconto,quantita);
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "commerciante/showInventario";
    }
}
