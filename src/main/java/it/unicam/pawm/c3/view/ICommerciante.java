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

    public void getPromozioniAttive(){
//        listaPromozioni.getItems().clear();
//        listaPromozioni.getItems().addAll(gestoreCommercianti.getPromozioniAttive());
    }

    public void getMerciDoveApplicarePromozioni(){
//        listaPromozioniPossibili.getItems().clear();
//        listaPromozioniPossibili.getItems().addAll(gestoreCommercianti.getPromozioniPossibili());
    }

//    @FXML
    void aggiungiPromozioneButtonEvent() {
//        listaPromozioniPossibili.setVisible(true);
//        dataF.setVisible(true);
//        dataI.setVisible(true);
//        percentualePromozione.setVisible(true);
//        aggiungiPromozioneButton.setVisible(true);
//        aggiuntaPromozione.setVisible(true);
//        dataInizioPromozione.setVisible(true);
//        dataFinePromozione.setVisible(true);
//        scontoPromozione.setVisible(true);
//        getMerciDoveApplicarePromozioni();
    }
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


//    @FXML
    void addPromozioneButtonEvent(){
//        addPromozione(listaPromozioniPossibili.getSelectionModel().getSelectedItem(),dataI.getValue(), dataF.getValue(),Double.parseDouble(percentualePromozione.getText()));
//        getPromozioniAttive();
//        initFieldPromozioni();
    }
//    @FXML
    void rimuoviPromozioneButtonEvent() {
//        listaPromozioniPossibili.setVisible(false);
//        dataF.setVisible(false);
//        dataI.setVisible(false);
//        percentualePromozione.setVisible(false);
//        aggiuntaPromozione.setVisible(false);
//        rimuoviPromozione(listaPromozioni.getSelectionModel().getSelectedItems());
//        getPromozioniAttive();
    }

    public void initFieldPromozioni(){
//        listaPromozioni.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        listaPromozioniPossibili.setVisible(false);
//        dataF.setVisible(false);
//        dataI.setVisible(false);
//        percentualePromozione.setVisible(false);
//        aggiuntaPromozione.setVisible(false);
//        dataInizioPromozione.setVisible(false);
//        dataFinePromozione.setVisible(false);
//        scontoPromozione.setVisible(false);
//        getPromozioniAttive();
    }

    /******************Interfaccia Gestione Corriere***************/

    public void getCorrieri() {
//        corrieriDaAggiungere.getItems().clear();
//        corrieriDaAggiungere.getItems().addAll(gestoreCommercianti.getCorrieri());
    }
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
//    @FXML
    void confermAggiuntaCorriereButtonEvent() {
//        addCorrieri(corrieriDaAggiungere.getSelectionModel().getSelectedItems());
//        getCorrieri();
    }

    /******************Interfaccia assunzione addetto***************/

    public void getClienti(String email){
//        clientiFiltratiAA.getItems().clear();
//        clientiFiltratiAA.getItems().addAll(gestoreCommercianti.getCliente(email));
    }
    //METODO CHE SUL GESTORE SI CHIAMA GETCLIENTE(String email)

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
    public void assunzioneAddetto(Cliente cliente){
//        gestoreCommercianti.assunzioneAddetto(cliente);
    }

//    @FXML
    void cercaClienteAAButtonEvent() {
//        getClienti(emailAA.getText());
    }

//    @FXML
    void confermaAssunzioneAddettoButtonEvent() {
//        assunzioneAddetto(clientiFiltratiAA.getSelectionModel().getSelectedItem());
//        clientiFiltratiAA.getItems().clear();
    }

    /****************Gestione inventario**************/

    private void setVisibleAggiunta(boolean flag){
//        nomeMerceGI.clear();
//        descrizioneMerceGI.clear();
//        quantitaMerceGI.clear();
//        prezzoMerceGI.clear();
//        scontoMerceGI.clear();
//        categoriaMerceGI.getItems().clear();
//        categoriaMerceGI.getItems().addAll(Categoria.values());
//        nomeLabelGI.setVisible(flag);
//        nomeMerceGI.setVisible(flag);
//        quantitaLabelGI.setVisible(flag);
//        quantitaMerceGI.setVisible(flag);
//        prezzoLabelGI.setVisible(flag);
//        prezzoMerceGI.setVisible(flag);
//        scontoLabelGI.setVisible(flag);
//        scontoMerceGI.setVisible(flag);
//        confermaAggiuntaMerceButton.setVisible(flag);
    }

    private void setVisibileModifica(boolean flag){
//        quantitaMerceGI.clear();
//        prezzoMerceGI.clear();
//        scontoMerceGI.clear();
//        quantitaLabelGI.setVisible(flag);
//        quantitaMerceGI.setVisible(flag);
//        prezzoLabelGI.setVisible(flag);
//        prezzoMerceGI.setVisible(flag);
//        scontoLabelGI.setVisible(flag);
//        scontoMerceGI.setVisible(flag);
//        confermaModificaButton.setVisible(flag);
    }

    private void setVisibileRimozione(boolean flag){
//        quantitaMerceGI.clear();
//        quantitaLabelGI.setVisible(flag);
//        quantitaMerceGI.setVisible(flag);
//        confermaRimozioneMerceButton.setVisible(flag);
    }

    public void initGestioneInventario() {
//        getMerciInventario();
//        setVisibleAggiunta(false);
//        setVisibileRimozione(false);
//        setVisibileModifica(false);
//        idLabel.setVisible(false);
//        idMerceGI.setVisible(false);
//        descrizioneLabelGI.setVisible(false);
//        descrizioneMerceGI.setVisible(false);
//        categoriaLabelGI.setVisible(false);
//        categoriaMerceGI.setVisible(false);
//        verificaIdMerceButton.setVisible(false);
    }

    public void getMerciInventario() {
//        merciInventario.getItems().clear();
//        merciInventario.getItems().addAll(gestoreCommercianti.getInventario());
    }
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
    private void aggiungiMerce(Long id, String nome, String descrizione, Categoria categoria, double quantita, double prezzo, double sconto) {
        gestoreCommercianti.addMerce(id,nome,descrizione,categoria,quantita,prezzo,sconto);
    }
    private boolean verificaIdMerce(Long id){
        return gestoreCommercianti.verificaIdMerce(id);
    }


//    @FXML
    void verificaIdMerceButtonEvent(){
//        if(verificaIdMerce(Long.parseLong(idMerceGI.getText()))){
//            setVisibleAggiunta(true);
//            nomeLabelGI.setVisible(false);
//            nomeMerceGI.setVisible(false);
//            verificaIdMerceButton.setVisible(false);
//        } else {
//            setVisibleAggiunta(true);
//            verificaIdMerceButton.setVisible(false);
//            descrizioneLabelGI.setVisible(true);
//            descrizioneMerceGI.setVisible(true);
//            categoriaLabelGI.setVisible(true);
//            categoriaMerceGI.setVisible(true);
//        }
    }

//    @FXML
    void aggiungiButtonEvent() {
//        setVisibileRimozione(false);
//        setVisibileModifica(false);
//        idLabel.setVisible(true);
//        idMerceGI.setVisible(true);
//        verificaIdMerceButton.setVisible(true);
    }

//    @FXML
    void modificaMerceButtonEvent(){
        setVisibileRimozione(false);
        setVisibleAggiunta(false);
        setVisibileModifica(true);
    }

//    @FXML
    void rimuoviMerceButtonEvent() {
        setVisibleAggiunta(false);
        setVisibileModifica(false);
        setVisibileRimozione(true);
    }

//    @FXML
    void confermaAggiuntaMerceButtonEvent() {
//        aggiungiMerce(Long.parseLong(idMerceGI.getText()),nomeMerceGI.getText(), descrizioneMerceGI.getText(), categoriaMerceGI.getValue(), Double.parseDouble(quantitaMerceGI.getText()), Double.parseDouble(prezzoMerceGI.getText()), Double.parseDouble(scontoMerceGI.getText()));
//        initGestioneInventario();
//        setVisibleAggiunta(false);
//        idMerceGI.clear();
//        idLabel.setVisible(false);
//        verificaIdMerceButton.setVisible(false);
//        idMerceGI.setVisible(false);
//        descrizioneMerceGI.clear();
//        categoriaMerceGI.getItems().clear();
//        descrizioneLabelGI.setVisible(false);
//        descrizioneMerceGI.setVisible(false);
//        categoriaLabelGI.setVisible(false);
//        categoriaMerceGI.setVisible(false);
    }

//    @FXML
    void confermaRimozioneMerceButtonEvent() {
//        rimuoviMerce(merciInventario.getSelectionModel().getSelectedItems(),Double.parseDouble(quantitaMerceGI.getText()));
//        initGestioneInventario();
//        setVisibileRimozione(false);
    }

//    @FXML
    void confermaModificaButtonEvent(){
//        modificaMerce(merciInventario.getSelectionModel().getSelectedItem(),Double.parseDouble(prezzoMerceGI.getText()), Double.parseDouble(scontoMerceGI.getText()), Double.parseDouble(quantitaMerceGI.getText()));
//        initGestioneInventario();
//        setVisibileModifica(false);
    }

    public void setGestoreCommerciante(GestoreCommercianti gestoreCommercianti) {
        this.gestoreCommercianti = gestoreCommercianti;
    }
}
