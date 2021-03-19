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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.management.modelmbean.ModelMBeanOperationInfo;
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
    public String blankCheckoutForm() {
        gestoreAddetti.startCarrello();
        return "addetto/checkout/checkoutForm";
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
        return "addetto/checkout/checkoutForm";
    }

    @PostMapping(value="/checkout",params="action=AggiungiAlCarrello")
    public String getCheckout(Model model,Long id,Double quantita,Double prezzo,Double sconto) {
        model.addAttribute("prezzoCarrello",gestoreAddetti.aggiuntaMerceNelCarrello(prezzo,sconto,id,quantita));
        return "addetto/checkout/checkoutForm";
    }

    /*************************Richiesta Carta*****************************/

    @PostMapping(value="/checkout", params ="action=CercaUserPerAssegnaCarta")
    public String getClienti(String email,Double prezzoCarrello, Model model){
        model.addAttribute("userList",gestoreAddetti.getCliente(email));
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout/checkoutForm";
    }

    @PostMapping(value="/checkout", params="action=GeneraCartaInCheckout")
    public String assegnaCartaInCheckout(String email1, TipoScontoCliente tipoScontoCliente,Double prezzoCarrello,Model model) {
        model.addAttribute("userList", gestoreAddetti.getCliente(email1));
        Optional<User> user = userRepository.findByEmail(email1);
        model.addAttribute("codiceCarta", gestoreAddetti.assegnaCarta(user.get().getId(), tipoScontoCliente));
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout/checkoutForm";
    }

    @PostMapping(value="/checkout", params = "action=RecuperaCodiceCartaDaEmail")
    public String recuperaCodiceCartaByEmail(String email2, Model model,Double prezzoCarrello ){
        model.addAttribute("codiceCarta", gestoreAddetti.searchCodiceCartaFromEmail(email2));
        model.addAttribute("prezzoCarrello",prezzoCarrello);
        return "addetto/checkout/checkoutForm";
    }

    @PostMapping(value="/checkout", params = "action=VerificaCodice")
    public String verificaCodice(Long codiceCarta, Model model, Double prezzoCarrello){
        if(gestoreAddetti.verificaCodiceCarta(codiceCarta)){
            model.addAttribute("prezzoCarrello", gestoreAddetti.applyScontoCarta(codiceCarta));
            model.addAttribute("resultVerifica", "codice corretto");
            model.addAttribute("codiceCarta", codiceCarta);
        } else {
            model.addAttribute("prezzoCarrello",prezzoCarrello);
            model.addAttribute("resultVerifica", "codice non valido");
        }
        return "addetto/checkout/checkoutForm";
    }

    @PostMapping(value = "/checkout", params = "action=ProcediSenzaCarta")
    public String procediSenzaCodice(Double prezzoCarrello, Model model){
        model.addAttribute("prezzoCarrello", prezzoCarrello);
        model.addAttribute("codiceCarta", 0);
        return "addetto/checkout/checkoutForm";
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

    void confermaLuogoDiRitiroButtonEvent() {
//        selectLuogoDiRitiro(luogoDiRitiro.getSelectionModel().getSelectedItem());
    }

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
