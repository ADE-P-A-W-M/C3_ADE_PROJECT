package it.unicam.pawm.c3.view;


import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.carta.TipoScontoCliente;
import it.unicam.pawm.c3.gestori.GestoreAddetti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.personale.User;
import it.unicam.pawm.c3.vendita.LuogoDiRitiro;
import it.unicam.pawm.c3.vendita.TipoDiRitiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/addettonegozio")
public class IAddettoNegozio {

    @Autowired
    private GestoreAddetti gestoreAddetti;
    @Autowired
    private GestoreAccessi gestoreAccessi;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MerceVenditaRepository merceVenditaRepository;
    @Autowired
    private VenditaSpeditaRepository venditaSpeditaRepository;
    @Autowired
    private MerceInventarioNegozioRepository merceInventarioNegozioRepository;

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
    public String askForRegistraVenditaDomicilio(@PathVariable Long idCliente,Long idCorriere,String indirizzoDomicilio,Model model) {
        return "home/homeAddetto";
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
            model.addAttribute("prezzoCarrello", gestoreAddetti.applyScontoCarta(codiceCarta));
            model.addAttribute("codiceCarta", codiceCarta);
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


    private void registraVendita(boolean flag,long cc){
        if(flag && cc==0){
            openAssegnazioneCarta();
            changeVisibilityFieldRegistraVendita();
        } else {
            if(flag){
                openRegistraVendita();
                changeVisibilityFieldRegistraVendita();
            } else {
                changeVisibilityFieldRegistraVendita();
//                addVenditaInventario();
            }
        }
    }

    void registraVenditaButtonEvent() {
//        boolean flag = true;
//        if(siRegistraVendita.isSelected()){
//            flag = true;
//        } else {
//            if (noRegistraVendita.isSelected()){
//                flag = false;
//            }
//        }
//        registraVendita(flag,Long.parseLong(codiceCarta.getText()));
    }

    private void openAssegnazioneCarta(){
//        inviaCodiceAllaRegistrazioneButton.setVisible(true);
//        tabPaneAddetto.getSelectionModel().select(assegnaCartaTab);
    }

    private void openRegistraVendita(){
//        tabRegistraVendita.setDisable(false);
//        initRegistrazioneVenditaField();
//        codiceClienteInRegistrazione.setText(codiceCarta.getText());
//        tabPaneAddetto.getSelectionModel().select(tabRegistraVendita);
    }

//    public void addVenditaInventario(){
//        gestoreAddetti.addVenditaInventario();
//    }

//    public double calcolaResto(double denaro){
//        return gestoreAddetti.calcoraResto(denaro);
//    }

    void calcolaRestoButtonEvent() {
//        resto.setText(String.valueOf(calcolaResto(Double.parseDouble(denaroRicevuto.getText()))));
    }

//    public void checkoutCompletato(){
//        gestoreAddetti.checkoutCompletato();
//    }

    void checkoutCompletedButtonEvent() {
//        checkoutCompletato();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vendita andata a buon fine",ButtonType.FINISH);
//        alert.show();
//        initRichiestaCartField();
    }

//    public void annullaCheckout(){
//        gestoreAddetti.annullaCheckout();
//    }

    void annullaCheckoutButtonEvent() {
//        annullaCheckout();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vendita non andata a buon fine",ButtonType.FINISH);
//        alert.show();
    }

    public void changeVisibilityFieldRegistraVendita(){
//        denaroRicevutoLabel.setVisible(true);
//        denaroRicevuto.setVisible(true);
//        calcolaRestoButton.setVisible(true);
//        resto.setVisible(true);
//        restoLabel.setVisible(true);
//        checkoutCompletedButton.setVisible(true);
//        annullaCheckoutButton.setVisible(true);
    }

    /***********************Interfaccia registrazione vendita************************/

    @GetMapping("/registrazioneVendita")
    public String loadFormRegistrazione(){
        return "addetto/formRegistraVendita";
    }

//    @PostMapping("/getRitiro")
//    public String getRitiro(String ritiro,Model model){
//        switch(ritiro){
//            case "IN_LOCO":
//                break;
//            case "CORRIERE_DOMICILIO":
//
//                break;
//            case "CORRIERE_NEGOZIO":
//                break;
//        }
//        return "";
//    }

    @GetMapping("/getAll")
    public String getAll(Model model){
//        List<Corriere> corrieriList;
//        corrieriList = gestoreAddetti.getCorrieriDisponibili();
//        List<Negozio> negoziList;
//        negoziList = gestoreAddetti.getNegoziDisponibili();
//        model.addAttribute("corrieriList",corrieriList);
//        model.addAttribute("negoziList",negoziList);

        return "addetto/formRegistraVendita";
    }

    @PostMapping(value="/getAll",params = "action=iniziaRegistraVendita")
    public String fillTables(Model model){

        model.addAttribute("corrieriList",gestoreAddetti.getCorrieriDisponibili());

        model.addAttribute("negoziList",gestoreAddetti.getNegoziDisponibili());


        return "addetto/formRegistraVendita";
    }

    @PostMapping("/getCorrieriDisponibili")
    public String getCorrieriDisponibili(TipoDiRitiro tipoDiRitiro,Model model){
        List<Corriere> corrieriList = new ArrayList<>();
        if(tipoDiRitiro.equals(TipoDiRitiro.CORRIERE)){
            corrieriList = gestoreAddetti.getCorrieriDisponibili();
        }
        model.addAttribute("corrieriList",corrieriList);
        return "addetto/formRegistraVendita";
    }

    @PostMapping("/getNegoziDisponibili")
    public String getNegoziDisponibili(LuogoDiRitiro luogoDiRitiro,Model model){
        List<Negozio> negoziList = new ArrayList<>();
        if(luogoDiRitiro.equals(LuogoDiRitiro.NEGOZIO)){
            negoziList = gestoreAddetti.getNegoziDisponibili();
        }
        model.addAttribute("negoziList",negoziList);
        return "addetto/formRegistraVendita";

    }




    private void initRegistrazioneVenditaField() {
//        corrieriDisponibili.getItems().clear();
//        puntiDiRitiroDisponibili.getItems().clear();
//        luogoDiRitiro.getItems().clear();
//        tipoRitiro.getItems().clear();
//        indirizzoRitiro.clear();
//        tipoRitiro.getItems().addAll(TipoDiRitiro.values());
//        luogoDiRitiro.getItems().addAll(LuogoDiRitiro.values());
//        prLabel.setVisible(false);
//        corrieriDisponibiliLabel.setVisible(false);
//        corrieriDisponibili.setVisible(false);
//        trcLabel.setVisible(false);
//        luogoDiRitiro.setVisible(false);
//        confermaLuogoDiRitiroButton.setVisible(false);
//        indirizzoLabel.setVisible(false);
//        indirizzoRitiro.setVisible(false);
//        puntiDiRitiroDisponibili.setVisible(false);
//        codiceClienteInRegistrazioneLabel.setVisible(false);
//        codiceClienteInRegistrazione.setVisible(false);
//        venditaButton.setVisible(false);
    }

    private void selectTipoRitiroVendita(TipoDiRitiro tr){
//        if(tr.equals(TipoDiRitiro.CORRIERE)){
//            corrieriDisponibiliLabel.setVisible(true);
//            corrieriDisponibili.getItems().addAll(getCorrieriDisponibili());
//            corrieriDisponibili.setVisible(true);
//            trcLabel.setVisible(true);
//            luogoDiRitiro.setVisible(true);
//            confermaLuogoDiRitiroButton.setVisible(true);
//        } else {
//            codiceClienteInRegistrazioneLabel.setVisible(true);
//            codiceClienteInRegistrazione.setVisible(true);
//            venditaButton.setVisible(true);
//        }
    }

//    private List<Corriere> getCorrieriDisponibili(){
//        return gestoreAddetti.getCorrieriDisponibili();
//    }

    private void getNegoziDisponibili(){
//        puntiDiRitiroDisponibili.getItems().clear();
//        puntiDiRitiroDisponibili.getItems().addAll(gestoreAddetti.getNegoziDisponibili());
    }

    void confermaTipoRitiroButtonEvent() {
//        selectTipoRitiroVendita(tipoRitiro.getSelectionModel().getSelectedItem());
    }

    private void selectLuogoDiRitiro(LuogoDiRitiro ldr){
//        puntiDiRitiroDisponibili.setVisible(false);
//        prLabel.setVisible(false);
//        indirizzoLabel.setVisible(false);
//        indirizzoRitiro.setVisible(false);
//        if(ldr.equals(LuogoDiRitiro.NEGOZIO)){
//            getNegoziDisponibili();
//            puntiDiRitiroDisponibili.setVisible(true);
//            prLabel.setVisible(true);
//        } else {
//            indirizzoLabel.setVisible(true);
//            indirizzoRitiro.setVisible(true);
//        }
//        codiceClienteInRegistrazioneLabel.setVisible(true);
//        codiceClienteInRegistrazione.setVisible(true);
//        venditaButton.setVisible(true);
    }

//    void confermaLuogoDiRitiroButtonEvent() {
//        selectLuogoDiRitiro(luogoDiRitiro.getSelectionModel().getSelectedItem());
//    }

    private void registraAcquistoCliente(long cc, Negozio pdr, String indirizzo , Corriere corriere){
//        gestoreAddetti.registraAcquistoCliente(cc,pdr,indirizzo, corriere);
    }

//    private void registraAcquistoCliente(long cc){
//        gestoreAddetti.registraAcquistoCliente(cc);
//    }

    void venditaButtonEvent() {
//        try{
//            if(tipoRitiro.getSelectionModel().getSelectedItem().equals(TipoDiRitiro.CORRIERE)){
//                registraAcquistoCliente(Long.parseLong(codiceClienteInRegistrazione.getText()),puntiDiRitiroDisponibili.getSelectionModel().getSelectedItem(),indirizzoRitiro.getText(),corrieriDisponibili.getSelectionModel().getSelectedItem());
//            } else {
//                registraAcquistoCliente(Long.parseLong(codiceClienteInRegistrazione.getText()));
//            }
//            codiceCarta.setText(codiceClienteInRegistrazione.getText());
//            initRegistrazioneVenditaField();
//            tabPaneAddetto.getSelectionModel().select(tabCheckout);
//            tabRegistraVendita.setDisable(true);
//        } catch (Exception e){
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Campi mancanti", ButtonType.OK);
//            alert.show();
//        }
    }
//
//    @GetMapping("/checkout")
//    public String homecheckout(){
//        return "addetto/checkout/checkout";
//    }

//    @PostMapping("/checkout")
//    public String getCheckoutForm(Model model, Long codiceCarta) {
//        model.addAttribute("codiceCarta", codiceCarta);
//        return "addetto/checkout/checkout";
//    }

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
