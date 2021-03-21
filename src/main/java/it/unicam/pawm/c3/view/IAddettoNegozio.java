package it.unicam.pawm.c3.view;


import it.unicam.pawm.c3.carta.TipoScontoCliente;
import it.unicam.pawm.c3.gestori.GestoreAddetti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.persistenza.UserRepository;
import it.unicam.pawm.c3.personale.User;
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
        double prezzo=gestoreAddetti.getPrezzo(id,quantita);
        double sconto=gestoreAddetti.getSconto(id);
        model.addAttribute("id",id);
        model.addAttribute("quantita",quantita);
        model.addAttribute("prezzo",prezzo);
        model.addAttribute("sconto",sconto);
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout",params="action=AggiungiAlCarrello")
    public String getCheckout(Model model,Long id,Double quantita,Double prezzo,Double sconto) {
        model.addAttribute("prezzoCarrello",gestoreAddetti.aggiuntaMerceNelCarrello(prezzo,sconto,id,quantita));
        return "addetto/checkout";
    }

    @PostMapping(value = "/checkout", params = "action=CalcolaResto")
    public String calcolaResto(Model model, Double denaroRicevuto, Double prezzoCarrello, Long codiceCarta){
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        model.addAttribute("denaroRicevuto", denaroRicevuto);
        model.addAttribute("codiceCarta", codiceCarta);
        model.addAttribute("resto", gestoreAddetti.calcoraResto(denaroRicevuto));
        return "addetto/checkout";
    }

    @PostMapping(value = "/checkout", params = "action=CompletaCheckout")
    public String completaCheckout(Long codiceCarta,@RequestParam(value = "checkboxName", required = false) String checkboxValue){
        gestoreAddetti.checkoutCompletato(codiceCarta);
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
    public String annullaCheckout(){
        gestoreAddetti.annullaCheckout();
        return "home/homeAddetto";
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
        model.addAttribute("userList",gestoreAddetti.getCliente(email));
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params="action=GeneraCartaInCheckout")
    public String assegnaCartaInCheckout(String email1, TipoScontoCliente tipoScontoCliente,Double prezzoCarrello,Model model) {
        model.addAttribute("userList", gestoreAddetti.getCliente(email1));
        Optional<User> user = userRepository.findByEmail(email1);
        model.addAttribute("codiceCarta", gestoreAddetti.assegnaCarta(user.get().getId(), tipoScontoCliente));
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params = "action=RecuperaCodiceCartaDaEmail")
    public String recuperaCodiceCartaByEmail(String email2, Model model,Double prezzoCarrello ){
        model.addAttribute("codiceCarta", gestoreAddetti.searchCodiceCartaFromEmail(email2));
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params = "action=VerificaCodice")
    public String verificaCodice(Long codiceCarta, Model model, Double prezzoCarrello){
        if(gestoreAddetti.verificaCodiceCarta(codiceCarta)){
            model.addAttribute("resultVerifica", "codice corretto");
            model.addAttribute("codiceCarta", codiceCarta);
        } else {
            model.addAttribute("resultVerifica", "codice non valido");
        }
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout";
    }

    @PostMapping(value="/checkout", params = "action=ApplicaScontoCarta")
    public String applicaScontoCarta(Long codiceCarta, Model model, Double prezzoCarrello){
        if(codiceCarta!=null){
            if(gestoreAddetti.verificaCodiceCarta(codiceCarta)){
                model.addAttribute("prezzoCarrello", gestoreAddetti.applyScontoCarta(codiceCarta));
                model.addAttribute("codiceCarta", codiceCarta);
                model.addAttribute("resultVerifica", "codice corretto");
            } else {
                model.addAttribute("resultVerifica", "codice non valido");
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
        model.addAttribute("userList",gestoreAddetti.getCliente(email));
        return "addetto/clienteAssegnazioneCarta";
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
        model.addAttribute("clienteList", gestoreAddetti.getCliente(email));
        return "addetto/clienteVenditaAssegnata";
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
