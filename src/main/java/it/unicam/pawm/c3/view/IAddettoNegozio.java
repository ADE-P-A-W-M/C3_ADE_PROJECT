package it.unicam.pawm.c3.view;


import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.carta.TipoScontoCliente;
import it.unicam.pawm.c3.gestori.GestoreAddetti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.Corriere;
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

    //    public void startCarrello(){
//        gestoreAddetti.startCarrello();
//    }

//    public double getPrezzo(long id, double quantita){
//        return gestoreAddetti.getPrezzo(id, quantita);
//    }

//    public double getSconto(long id) {
//        return gestoreAddetti.getSconto(id);
//    }
    @GetMapping("/loadCheckout")
    public String blankCheckoutForm() {
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
        //model.addAttribute("prezzoCarrello",gestoreAddetti.aggiuntaMerceNelCarrello(prezzo,sconto,id,quantita));
        return "addetto/checkout/checkoutForm";
    }
    @PostMapping(value="/checkout",params="action=AggiungiAlCarrello")
    public String getCheckout(Model model,Long id,Double quantita,Double prezzo,Double sconto) {
        model.addAttribute("prezzoCarrello",gestoreAddetti.aggiuntaMerceNelCarrello(prezzo,sconto,id,quantita));
        return "addetto/checkout/checkoutForm";
    }

    @PostMapping(value="/checkout", params = "action=PassaggioCarta")
    public String getCheckoutPassaggioCarta(Model model, Long codiceCarta){
        model.addAttribute("codiceCarta", codiceCarta);
        return "addetto/checkout/checkoutForm";
    }

    /********************Richiesta Carta******************/

    public void initRichiestaCartField() {
//        tabRegistraVendita.setDisable(true);
//        siCartaDisponibile.setVisible(false);
//        noCartaDisponibile.setVisible(false);
//        cdLabel.setVisible(false);
//        cartaDisponibileButton.setVisible(false);
//        codiceCarta.setVisible(false);
//        codiceFiscale.setVisible(false);
//        confermaCF.setVisible(false);
//        verificaCodice.setVisible(false);
//        iscrizioneClienteCheckout.setVisible(false);
//        codiceCartaLabel.setVisible(false);
//        codiceFiscaleLabel.setVisible(false);
//        applyScontoCartaButton.setVisible(false);
//        answerRegistraVenditaLabel.setVisible(false);
//        siRegistraVendita.setVisible(false);
//        noRegistraVendita.setVisible(false);
//        registraVenditaButton.setVisible(false);
//        noScontoCartaButton.setVisible(false);
//        denaroRicevutoLabel.setVisible(false);
//        denaroRicevuto.setVisible(false);
//        calcolaRestoButton.setVisible(false);
//        resto.setVisible(false);
//        restoLabel.setVisible(false);
//        checkoutCompletedButton.setVisible(false);
//        annullaCheckoutButton.setVisible(false);
//        idMerce.clear();
//        quantitaMerce.clear();
//        prezzoMerce.clear();
//        prezzoCarrello.clear();
//        prezzoTotale.clear();
//        codiceFiscale.clear();
//        codiceCarta.clear();
//        denaroRicevuto.clear();
//        resto.clear();
    }

    /********************Richiesta Carta******************/

    private void possessoCarta(boolean flag){
//        if(flag){
//            siCartaDisponibile.setVisible(true);
//            noCartaDisponibile.setVisible(true);
//            cartaDisponibileButton.setVisible(true);
//            cdLabel.setVisible(true);
//        } else {
//            codiceCarta.setText("0");
//            iscrizioneClienteCheckout.setVisible(true);
//            codiceCarta.setVisible(true);
//            codiceCartaLabel.setVisible(true);
//            noScontoCartaButton.setVisible(true);
//        }
    }

    void possessoCartaButtonEvent() {
//        if(siCarta.isSelected()){
//            possessoCarta(true);
//        } else {
//            if(noCarta.isSelected()){
//                possessoCarta(false);
//            }
//        }
    }

    private void disponibilitaCarta(boolean disponibilita){
//        if(disponibilita){
//            codiceCarta.setVisible(true);
//            verificaCodice.setVisible(true);
//            codiceCartaLabel.setVisible(true);
//        } else {
//            codiceFiscale.setVisible(true);
//            confermaCF.setVisible(true);
//            codiceFiscaleLabel.setVisible(true);
//        }
    }

    void cartaDisponibileButton() {
//        if(siCartaDisponibile.isSelected()) {
//            disponibilitaCarta(true);
//        } else {
//            if(noCartaDisponibile.isSelected()) {
//                disponibilitaCarta(false);
//            }
//        }
    }

//    public boolean verificaCodiceCarta(long cc) {
//        return gestoreAddetti.verificaCodiceCarta(cc);
//    }

//    public long searchCodiceCartaByEmail(String email) {
//        return gestoreAddetti.searchCodiceCartaFromEmail(email);
//    }

    void verificaCodiceCartaButton() {
//        if(verificaCodiceCarta(Long.parseLong(codiceCarta.getText()))) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Codice Carta valido!", ButtonType.OK);
//            alert.show();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR,"Codice Carta non valido!", ButtonType.OK);
//            alert.show();
//        }
//        applyScontoCartaButton.setVisible(true);
    }

    void confermaCFButton() {
//        codiceCarta.setVisible(true);
//        codiceCartaLabel.setVisible(true);
//        codiceCarta.setText(String.valueOf(searchCodiceCartaByEmail(codiceFiscale.getText())));
//        if(codiceFiscale.getText()=="0") {
//            Alert alert = new Alert(Alert.AlertType.ERROR,"Codice Carta non trovato!", ButtonType.OK);
//            alert.show();
//        }
//        applyScontoCartaButton.setVisible(true);
    }

    private void richiestaAssegnazioneCarta(boolean flag){
//        if(flag){
//            applyScontoCartaButton.setVisible(true);
//            noScontoCartaButton.setVisible(false);
//            inviaCodiceAlCheckoutButton.setVisible(true);
//            tabPaneAddetto.getSelectionModel().select(assegnaCartaTab);
//        } else {
//            codiceCarta.setText("0");
//        }
    }

    void iscrizioneClienteCheckoutButtonEvent() {
        richiestaAssegnazioneCarta(true);
    }

    /********************Fine Richiesta Carta*******************/

//    public double applyScontoCarta(long cc){
//        return gestoreAddetti.applyScontoCarta(cc);
//    }

    void applyScontoCartaButtonEvent() {
//        prezzoTotale.setText(String.valueOf(applyScontoCarta(Long.parseLong(codiceCarta.getText()))));
//        answerRegistraVenditaLabel.setVisible(true);
//        siRegistraVendita.setVisible(true);
//        noRegistraVendita.setVisible(true);
//        registraVenditaButton.setVisible(true);
    }

    void noScontoCartaButtonEvent() {
//        prezzoTotale.setText(String.valueOf(applyScontoCarta(Long.parseLong(codiceCarta.getText()))));
//        answerRegistraVenditaLabel.setVisible(true);
//        siRegistraVendita.setVisible(true);
//        noRegistraVendita.setVisible(true);
//        registraVenditaButton.setVisible(true);
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
//        gestoreAddetti.assegnaCarta(id, tipoScontoCliente);
        model.addAttribute("userList", gestoreAddetti.getCliente(id));
        model.addAttribute("codiceCarta", gestoreAddetti.assegnaCarta(id, tipoScontoCliente));
        return "addetto/clienteAssegnazioneCarta";
//        return new ModelAndView("redirect:/addettoNegozio/getCliente/"+id, model);
    }

//    void inviaCodiceAlCheckoutButtonEvent() {
//    }

//    void inviaCodiceAllaRegistrazioneButtonEvent(){
//    }

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
