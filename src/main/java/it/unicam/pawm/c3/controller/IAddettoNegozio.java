package it.unicam.pawm.c3.controller;


import it.unicam.pawm.c3.model.carta.TipoScontoCliente;
import it.unicam.pawm.c3.service.gestori.GestoreAddetti;
import it.unicam.pawm.c3.service.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.repository.UserRepository;
import it.unicam.pawm.c3.model.personale.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(path = "/addettonegozio")
public class IAddettoNegozio {

    @Autowired
    private GestoreAddetti gestoreAddetti;
    @Autowired
    private GestoreAccessi gestoreAccessi;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public IAddettoNegozio() {
        this.gestoreAddetti = new GestoreAddetti();
        this.gestoreAccessi = new GestoreAccessi();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        gestoreAddetti.setNegozio(gestoreAccessi.homeAddetto(email));
        return "home/homeAddetto";
    }

    @GetMapping("/checkout")
    public String blankCheckoutForm(Model model) {
        gestoreAddetti.startCarrello();
        model.addAttribute("message", "hello");
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout",params="action=TrovaMerce")
    public String getCheckoutForm(Model model,Long id,Double quantita,Double prezzoCarrello) {
        try{
            double prezzo=gestoreAddetti.getPrezzo(id,quantita);
            double sconto=gestoreAddetti.getSconto(id);
            model.addAttribute("prezzo",prezzo);
            model.addAttribute("sconto",sconto);
        } catch (Exception e){
            model.addAttribute("alertMerceOQuantitaMancante", "merce o quantita non inserita");
        }
        model.addAttribute("id",id);
        model.addAttribute("quantita",quantita);
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout",params="action=AggiungiAlCarrello")
    public String getCheckout(Model model,Long id,Double quantita,Double prezzo,Double sconto) {
        try {
            model.addAttribute("prezzoCarrello",gestoreAddetti.aggiuntaMerceNelCarrello(prezzo,sconto,id,quantita));
        } catch (Exception e) {
            model.addAttribute("alertCarrello", "inserie merce, quantita e poi aggiungere");
        }
        return "addetto/checkout";
    }

    @PostMapping(value = "/checkout", params = "action=CalcolaResto")
    public String calcolaResto(Model model, Double denaroRicevuto, Double prezzoCarrello, Long codiceCarta){
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        model.addAttribute("denaroRicevuto", denaroRicevuto);
        model.addAttribute("codiceCarta", codiceCarta);
        try{
            model.addAttribute("resto", gestoreAddetti.calcoraResto(denaroRicevuto));
        } catch (Exception e ) {
            model.addAttribute("alertCalcolaResto", "denaro non inserito o inserito incorrettamente");
        }
        return "addetto/checkout";
    }

    @PostMapping(value = "/checkout", params = "action=CompletaCheckout")
    public String completaCheckout(Long codiceCarta,@RequestParam(value = "checkboxName", required = false) String checkboxValue, Model model){
        try{
            gestoreAddetti.checkoutCompletato(codiceCarta);
        } catch (Exception e) { }
        if(checkboxValue != null)
        {
            Long id = gestoreAddetti.getClienteFromCodiceCarta(codiceCarta);
            return "redirect:"+id;
        }
        else
        {
            return "home/homeAddetto";
        }
    }

    @PostMapping(value = "/checkout", params = "action=AnnullaCheckout")
    public ModelAndView annullaCheckout(){
        gestoreAddetti.annullaCheckout();
        return new ModelAndView("redirect:/addettonegozio/");
    }
    /***********************Interfaccia registrazione vendita************************/

    @GetMapping("/checkout/{id}")
    public String askRegistraVendita(@PathVariable Long id, Model model) {
        model.addAttribute("id",id);
        model.addAttribute("corrieriList",gestoreAddetti.getCorrieriDisponibili());
        return "addetto/askForRegistraVendita";
    }
    @PostMapping(value="/checkout/{id}",params = "action=sceltaCorriere")
    public String askForRegistraVendita(@PathVariable Long id,Long idCorriere,@RequestParam(value = "checkboxShop", required = false) String checkboxShop, @RequestParam(value = "checkboxDomicile", required = false) String checkboxDomicile, Model model) {
        if(checkboxShop != null) {
            model.addAttribute("idCliente",id);
            model.addAttribute("idCorriere",idCorriere);
            model.addAttribute("negoziList",gestoreAddetti.getNegoziDisponibili());
            return "addetto/registraVenditaNegozio";
        } else if(checkboxDomicile != null){
            model.addAttribute("idCliente",id);
            model.addAttribute("idCorriere",idCorriere);
            return "addetto/askForIndirizzoDomicilio";
        }
        return "home/homeAddetto";
    }

    @PostMapping(value="/checkout/{idCliente}",params = "action=sceltaNegozio")
    public ModelAndView askForRegistraVenditaNegozio(@PathVariable Long idCliente,Long idCorriere,Long idNegozio,ModelMap model) {
        gestoreAddetti.registraAcquistoCliente(idCliente,idCorriere,idNegozio);
        return new ModelAndView("redirect:/addettonegozio/");
    }

    @PostMapping(value="/checkout/{idCliente}",params = "action=confermaIndirizzo")
    public ModelAndView askForRegistraVenditaDomicilio(@PathVariable Long idCliente,Long idCorriere,String indirizzoDomicilio,Model model) {
        gestoreAddetti.registraAcquistoCliente(idCliente,idCorriere,indirizzoDomicilio);
        return new ModelAndView("redirect:/addettonegozio/");
    }

    /*************************Richiesta Carta*****************************/

    @PostMapping(value="/checkout", params ="action=CercaUserPerAssegnaCarta")
    public String getClienti(String email,Double prezzoCarrello, Model model){
        try{
            model.addAttribute("userList",gestoreAddetti.getCliente(email));
        } catch (Exception e) {
            model.addAttribute("alertUserCartaErrato", "utente non presente o incorretto");
        }
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params="action=GeneraCartaInCheckout")
    public String assegnaCartaInCheckout(String email1, TipoScontoCliente tipoScontoCliente,Double prezzoCarrello,Model model) {
        try{
            model.addAttribute("userList", gestoreAddetti.getCliente(email1));
            Optional<User> user = userRepository.findByEmail(email1);
            model.addAttribute("codiceCarta", gestoreAddetti.assegnaCarta(user.get().getId(), tipoScontoCliente));
            model.addAttribute("prezzoCarrello",prezzoCarrello);
        } catch (Exception e) {
            model.addAttribute("alertGenerazioneCartaErrata", "utente non selezionato o incorretto");
        }
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params = "action=RecuperaCodiceCartaDaEmail")
    public String recuperaCodiceCartaByEmail(String email2, Model model,Double prezzoCarrello ){
        try {
            model.addAttribute("codiceCarta", gestoreAddetti.searchCodiceCartaFromEmail(email2));
        } catch (Exception e) {
            model.addAttribute("alertRecuperoCC", "utente errato o carta non associata");
        }
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params = "action=ApplicaCarta")
    public String applicaScontoCarta(Long codiceCarta, Model model, Double prezzoCarrello){
        if(codiceCarta!=null){
            if(gestoreAddetti.verificaCodiceCarta(codiceCarta)){
                model.addAttribute("prezzoCarrello", gestoreAddetti.applyScontoCarta(codiceCarta));
                model.addAttribute("codiceCarta", codiceCarta);
                model.addAttribute("alertCodiceValido", "codice corretto");
            } else {
                model.addAttribute("alertCodiceErrato", "codice non valido");
                model.addAttribute("codiceCarta", 0);
                model.addAttribute("prezzoCarrello", prezzoCarrello);
            }
        } else {
            model.addAttribute("codiceCarta", 0);
            model.addAttribute("prezzoCarrello", prezzoCarrello);
        }
        return "addetto/checkout";

    }

    @PostMapping(value = "/checkout", params = "action=ProcediSenzaCarta")
    public String procediSenzaCodice(Double prezzoCarrello, Model model){
        model.addAttribute("prezzoCarrello", prezzoCarrello);
        model.addAttribute("codiceCarta", 0);
        return "addetto/checkout";
    }

    /***********************Assegnazione Carta****************************/

    @GetMapping("/ricercaCliente")
    public String clienteAssegnazioneCartaForm(){
        return "addetto/formRicercaClienteAssegnazione";
    }

    @PostMapping("/getCliente")
    public String getClientiFiltered(String email, Model model){
        try{
            model.addAttribute("userList",gestoreAddetti.getCliente(email));
            return "addetto/clienteAssegnazioneCarta";
        } catch (Exception e) {
            model.addAttribute("alertUtente", "utente inserito non valido");
            return "addetto/formRicercaClienteAssegnazione";
        }
    }

    @PostMapping("/getCliente/{id}")
    public String assegnaCarta(@PathVariable Long id, TipoScontoCliente tipoScontoCliente, Model model) {
        model.addAttribute("userList", gestoreAddetti.getCliente(id));
        model.addAttribute("codiceCarta", gestoreAddetti.assegnaCarta(id, tipoScontoCliente));
        return "home/homeAddetto";
    }

    /******************Interfaccia Consulta Inventario*********************/

    @GetMapping("/consultaInventarioAddetto")
    public String getInventario(Model model){
        model.addAttribute("inventario",gestoreAddetti.getInventario());
        return "addetto/consultaInventarioAddetto";
    }

    @GetMapping("/infoMerce/{id}")
    public String getInfoMerce(@PathVariable Long id, Model model){
        model.addAttribute("minList", gestoreAddetti.getInfoMerce(id));
        return "addetto/infoMerceSingola";
    }

    /****************Interfaccia Consegna Vendita Assegnata**************/

    @GetMapping("/getVenditeAssegnate")
    public String venditeAssegnateForm(){
        return "addetto/formVenditeAssegnate";
    }

    @PostMapping("/getVenditeAssegnate")
    public String showCliente(String email,Model model){
        try{
            model.addAttribute("clienteList", gestoreAddetti.getCliente(email));
            return "addetto/clienteVenditaAssegnata";
        } catch (Exception e) {
            model.addAttribute("alertUtente", "utente inserito non valido");
            return "addetto/formVenditeAssegnate";
        }
    }

    @GetMapping("/getVenditeAssegnate/{id}")
    public String getAcquistiClienteDaRitirare(@PathVariable Long id,Model model){
        model.addAttribute("listaVendite", gestoreAddetti.getAcquistiClienteDaRitare(id));
        return "addetto/consegnaVenditaAssegnata";
    }

    @GetMapping("/consegnaAlCliente/{id}")
    public ModelAndView consegnaVenditaAlNegozio(@PathVariable Long id, ModelMap model) {
        gestoreAddetti.confermaConsegnaVenditaAssegnata(id);
        return new ModelAndView("redirect:/addettonegozio/", model);
    }
}
