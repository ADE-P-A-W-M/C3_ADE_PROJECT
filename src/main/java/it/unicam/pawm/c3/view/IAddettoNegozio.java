package it.unicam.pawm.c3.view;


import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.carta.TipoScontoCliente;
import it.unicam.pawm.c3.gestori.GestoreAddetti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.*;
import it.unicam.pawm.c3.vendita.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
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
        return "homeAddetto";
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



    void trovaPrezzoEScontoButtonEvent() {
//        try{
//            prezzoMerce.setText(String.valueOf(getPrezzo(Long.parseLong(idMerce.getText()), Double.parseDouble(quantitaMerce.getText()))));
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Inserire quantita", ButtonType.OK);
//            alert.show();
//        }
//        if(prezzoMerce.getText()=="0"){
//            Alert alert = new Alert(Alert.AlertType.ERROR,"Merce inserita non presente, inserire il prezzo manualmente", ButtonType.OK);
//            alert.show();
//        }
//        scontoMerce.setText(String.valueOf(getSconto(Long.parseLong(idMerce.getText()))));
//        if(scontoMerce.getText()=="0"){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Merce inserita non ha uno sconto, puoi inserire sconto manualmente", ButtonType.OK);
//            alert.show();
//        }
    }

//    public double aggiuntaMerceNelCarrello(double prezzo, double sconto, long id, double quantita){
//        return gestoreAddetti.aggiuntaMerceNelCarrello(prezzo,sconto,id,quantita);
//    }

    void inserisciButtonEvent() {
//        prezzoCarrello.setText(String.valueOf(aggiuntaMerceNelCarrello(Double.parseDouble(prezzoMerce.getText()),Double.parseDouble(scontoMerce.getText()),Integer.parseInt(idMerce.getText()),Double.parseDouble(quantitaMerce.getText()))));
//        clearCheckoutFields();
    }

    private void clearCheckoutFields() {
//        idMerce.clear();
//        quantitaMerce.clear();
//        prezzoMerce.clear();
//        scontoMerce.clear();
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

    /***********************Assegnazione Carta****************************/

    @GetMapping("/ricercaCliente")
    public String clienteAssegnazioneCartaForm(){
        return "formRicercaClienteAssegnazione";
    }

    @PostMapping("/getCliente")
    public String getClientiFiltered(String email, Model model){
        Optional<User> user = userRepository.findByEmail(email);
        // clienteRepository.save(cliente);
        List<User> userList = new ArrayList<>();
        userList.add(user.get());
        model.addAttribute("userList",userList);
        // clientiFiltratiAC.getItems().add(gestoreAddetti.getCliente(email));
        return "clienteAssegnazioneCarta";
    }

    @PostMapping("/getCliente/{id}")
    public String assegnaCarta(@PathVariable Long id, TipoScontoCliente tsc, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            gestoreAddetti.assegnaCarta((Cliente) user.get().getRuolo().get(0), tsc);

        }
return "";
    }
    public void initAssegnazioneCartaField(){
//        emailAC.clear();
//        clientiFiltratiAC.getItems().clear();
//        codiceCartaAC.clear();
//        inviaCodiceAlCheckoutButton.setVisible(false);
//        inviaCodiceAllaRegistrazioneButton.setVisible(false);
//        tscAC.getItems().clear();
//        tscAC.getItems().addAll(TipoScontoCliente.values());
    }

    public void getClientiFiltered(String email){
//        clientiFiltratiAC.getItems().clear();
//        clientiFiltratiAC.getItems().add(gestoreAddetti.getCliente(email));
    }

//    public long assegnaCarta(Cliente cliente, TipoScontoCliente tsc){
//        return gestoreAddetti.assegnaCarta(cliente,tsc);
//    }

    void cercaClienteACButtonEvent() {
//        try {
//            getClientiFiltered(emailAC.getText());
//        } catch (Exception e){
//            Alert alert = new Alert(Alert.AlertType.ERROR, "L'email inserita non corrisponde a nessun cliente nel sistema", ButtonType.OK);
//            alert.show();
//        }
    }

    void assegnaCartaButtonEvent() {
//        codiceCartaAC.setText(String.valueOf(assegnaCarta(clientiFiltratiAC.getSelectionModel().getSelectedItem(), tscAC.getValue())));
    }

    void inviaCodiceAlCheckoutButtonEvent() {
//        codiceCarta.setText(codiceCartaAC.getText());
//        tabPaneAddetto.getSelectionModel().select(tabCheckout);
//        initAssegnazioneCartaField();
    }

    void inviaCodiceAllaRegistrazioneButtonEvent(){
//        tabRegistraVendita.setDisable(false);
//        initRegistrazioneVenditaField();
//        codiceClienteInRegistrazione.setText(codiceCartaAC.getText());
//        tabPaneAddetto.getSelectionModel().select(tabRegistraVendita);
//        initAssegnazioneCartaField();
    }

    /******************Interfaccia Consulta Inventario*********************/

    @GetMapping("/consultaInventarioAddetto")
    public String getInventario(Model model){
        List<MerceInventarioNegozio> inventario = new ArrayList<>();
        for (MerceInventarioNegozio min : merceInventarioNegozioRepository.findAll()) {
            inventario.add(min);
        }
        model.addAttribute("inventario",inventario);
        return "consultaInventarioAddetto";
    }

    @GetMapping("/infoMerce/{id}")
    public String getInfoMerce(@PathVariable Long id, Model model){
        List<MerceInventarioNegozio> minList = new ArrayList<>();
        Optional<MerceInventarioNegozio> min = merceInventarioNegozioRepository.findById(id);
        minList.add(min.get());
        model.addAttribute("minList",minList);
        return "infoMerceSingola";
    }
    public void getInventario(){
//        listViewConsultaInventario.getItems().clear();
//        listViewConsultaInventario.getItems().addAll(gestoreAddetti.getInventario());
    }

    public void getInfoMerce(MerceInventarioNegozio min){
//        infoMerceConsultaInventario.clear();
//        infoMerceConsultaInventario.setText(gestoreAddetti.getInfoMerce(min));
    }

    void confermaConsultaInventarioEvent() {
//        getInfoMerce(listViewConsultaInventario.getSelectionModel().getSelectedItem());
    }

    /****************Interfaccia Consegna Vendita Assegnata**************/

    @GetMapping("/getVenditeAssegnate")
    public String venditeAssegnateForm(){
        return "formVenditeAssegnate";
    }

    @PostMapping("/getVenditeAssegnate")
    public String showCliente(String email,Model model){
        Optional<User> user = userRepository.findByEmail(email);
        // clienteRepository.save(cliente);
        List<User> userList = new ArrayList<>();
        userList.add(user.get());
        model.addAttribute("clienteList",userList);
        return "clienteVenditaAssegnata";
    }

    @GetMapping("/getVenditeAssegnate/{id}")
    public String getAcquistiClienteDaRitirare(@PathVariable Long id,Model model){
        Optional<User> user = userRepository.findById(id);
        Negozio negozio = negozioRepository.findAll().get(0);
        List<VenditaSpedita> venditaSpeditaList = new ArrayList<>();
        if(user.isPresent()){
            Cliente cliente = (Cliente) user.get().getRuolo().get(0);
            if(!cliente.getAcquisti().isEmpty()){
                Iterator<VenditaSpedita> venditeNegozio = negozio.getVenditeNegozioRitiro().iterator();
                while(venditeNegozio.hasNext()){
                    VenditaSpedita vs = venditeNegozio.next();
                    if(vs.getStatoConsegna().equals(StatoConsegna.CONSEGNATO_AL_NEGOZIO)){
                        Iterator<Vendita> venditaIterator = cliente.getAcquisti().iterator();
                        while(venditaIterator.hasNext()){
                            Vendita vendita = venditaIterator.next();
                            if(vendita.getId() == vs.getId()){
                                venditaSpeditaList.add(vs);
                            }
                        }
                    }
                }
            }
        }
        //  model.addAttribute("cliente",cliente.get());
        model.addAttribute("listaVendite",venditaSpeditaList);
        return "consegnaVenditaAssegnata";
    }



    @GetMapping("consegnaAlCliente/{id}")
    public String consegnaVenditaAlNegozio(@PathVariable Long id) {
        Optional<VenditaSpedita> VS = venditaSpeditaRepository.findById(id);
        if(VS.isPresent()) {
            VS.get().setStatoConsegna(StatoConsegna.CONSEGNATO_AL_CLIENTE);
        }
        venditaSpeditaRepository.save(VS.get());
        return "consegnaVenditaAssegnata";
    }

    public void getAcquistiClienteDaRitirare(String email){
//        listaVenditeDaConsegnare.getItems().clear();
//        listaVenditeDaConsegnare.getItems().addAll(gestoreAddetti.getAcquistiClienteDaRitirare(email));
    }

//    public void confermaConsegnaVenditaAssegnata(List<VenditaSpedita> vendite) {
//        gestoreAddetti.confermaConsegnaVenditaAssegnata(vendite);
//    }

    void confermaClienteConsegnaOrdineButton() {
//        getAcquistiClienteDaRitirare(emailConsegnaOrdine.getText());
    }

    void confermaConsegnaOrdineButton() {
//        confermaConsegnaVenditaAssegnata(listaVenditeDaConsegnare.getSelectionModel().getSelectedItems());
//        getAcquistiClienteDaRitirare(emailConsegnaOrdine.getText());
    }
//
//    public void setGestoreAddetto(GestoreAddetti gestoreAddetti) {
//        this.gestoreAddetti = gestoreAddetti;
//    }
}
