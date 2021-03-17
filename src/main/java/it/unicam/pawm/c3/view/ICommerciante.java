package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCommercianti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.Promozione;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.User;
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
import java.util.ArrayList;
import java.util.List;

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
        return "homeCommerciante";
    }

    /******************Interfaccia GestionePromozioni***************/

    @GetMapping("merceInPromozione")
    public String merceInPromozione(Model model) {
        model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
        return "showPromozioni";
    }

    @GetMapping("merceInPromozione/delete/{id}")
    public String removePromozione(@PathVariable Long id,Model model) {
        gestoreCommercianti.rimuoviPromozione(id);
        model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
        return "showPromozioni";
    }

    @GetMapping("merceNonInPromozione")
    public String merceNonInPromozione(Model model) {
        model.addAttribute("minList",gestoreCommercianti.getPromozioniPossibili());
        return "showMerceNonInPromozione";
    }

    @GetMapping("merceNonInPromozione/formAddPromozione/{id}")
    public String addPromozioneForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "addPromozione";
    }

    @PostMapping("merceInPromozione/addPromozione/{id}")
    public String addPromozione(@PathVariable("id") Long id, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInizio,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFine, Double percentualeSconto, Model model) {
        gestoreCommercianti.addPromozione(id,dataInizio,dataFine,percentualeSconto);
        model.addAttribute("minList",gestoreCommercianti.getPromozioniAttive());
        return "showPromozioni";
    }
    /******************Interfaccia Gestione Corriere***************/

    @GetMapping("showCorrieriDaAggiungere")
    public String showCorrieriDaAggiunguere(Model model)
    {
        model.addAttribute("corrieriList",gestoreCommercianti.getCorrieri());
        return "showCorrieriDaAggiungere";
    }
    @GetMapping("showCorrieriDaAggiungere/add/{id}")
    public String addCorriere(@PathVariable Long id,Model model) {
        gestoreCommercianti.addCorriere(id);
        model.addAttribute("corrieriList",gestoreCommercianti.getCorrieri());
        return "showCorrieriDaAggiungere";
    }

    /******************Interfaccia assunzione addetto***************/

    @GetMapping("assunzioneAddetto")
    public String assunzioneForm() {
        return "assunzioneAddetto";
    }

    @PostMapping ("assunzioneAddetto")
    public String assumiCliente(String email,Model model) {
        User user = gestoreCommercianti.getCliente(email);
        List<User> userList =new ArrayList<>();
        userList.add(user);
        model.addAttribute("userList",userList);
        return "clienteDaAssumere";
    }
    @GetMapping("assunzioneAddetto/{id}")
    public String assunzioneFinita(@PathVariable Long id) {
         gestoreCommercianti.assunzioneAddetto(id);
        return "assunzioneAddetto";
    }
    /****************Gestione inventario**************/
    @GetMapping("showInventario")
    public String showInventario(Model model) {
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "showInventario";
    }
    @GetMapping("showInventario/removeForm/{id}")
    public String removeForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "rimuoviMerce";
    }
    @GetMapping("showInventario/addMerceIdForm")
    public String addMerceIdForm() {
        return "addMerceIdForm";
    }

    @GetMapping("showInventario/modificaMerceForm/{id}")
    public String modifcaMerceForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "modificaMerceForm";
    }
    @PostMapping("showInventario/remove/{id}")
    public String removeFromInventario(@PathVariable Long id,Double quantita,Model model) {
        gestoreCommercianti.removeMerce(id,quantita);
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "showInventario";
    }
    @PostMapping("showInventario/addMerceId")
    public String checkIfMerceExists(Long id,Model model) {
        model.addAttribute("id",id);
        if(gestoreCommercianti.verificaIdMerce(id)) {
            return "addMerceAlreadyExisting";
        } else {
            return "addNewMerce";
        }
    }
    @PostMapping("showInventario/addMerce/{id}")
    public String addMerce(@PathVariable Long id,String nome,String descrizione,Categoria categoria,Double quantita,Double prezzo,Double sconto,Model model) {
        gestoreCommercianti.addMerce(id,nome,descrizione,categoria,quantita,prezzo,sconto);
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "showInventario";
    }
    @PostMapping("showInventario/modificaMerce/{id}")
    public String modificaMerce(@PathVariable Long id,Model model,Double quantita,Double prezzo,Double sconto) {
        gestoreCommercianti.modificaMerce(id,prezzo,sconto,quantita);
        model.addAttribute("minList",gestoreCommercianti.getInventario());
        return "showInventario";
    }
}
