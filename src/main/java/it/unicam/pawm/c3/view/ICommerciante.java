package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.gestori.GestoreCommercianti;
import it.unicam.pawm.c3.merce.*;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/commerciante")
public class ICommerciante {

    private GestoreCommercianti gestoreCommercianti;
    @Autowired
    private NegozioRepository negozioRepository;
    @Autowired
    private MerceInventarioNegozioRepository merceInventarioNegozioRepository;
    @Autowired
    private MerceAlPubblicoRepository merceAlPubblicoRepository;
    @Autowired
    private PromozioneRepository promozioneRepository;
    @Autowired
    private MerceRepository merceRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private CorriereRepository corriereRepository;
    @Autowired
    private UserRepository userRepository;

    public ICommerciante() {
        this.gestoreCommercianti = new GestoreCommercianti();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if(user.isPresent()){
            Iterator<Negozio> negozioIterator = negozioRepository.findAll().iterator();
            while(negozioIterator.hasNext()){
                Negozio negozio = negozioIterator.next();
                Iterator<AddettoNegozio> addettoNegozioIterator = negozio.getAddetti().iterator();
                while (addettoNegozioIterator.hasNext()){
                    AddettoNegozio addettoNegozio = addettoNegozioIterator.next();
                    for(Ruolo ruolo : user.get().getRuolo()){
                        if(ruolo.getId()== addettoNegozio.getId()) {
                            gestoreCommercianti.setNegozio(negozio);
                        }
                    }
                }
            }
        }
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
    public List<MerceInventarioNegozio> getPromozioniA() {
        List<MerceInventarioNegozio> minList=new ArrayList<>();
        for (MerceInventarioNegozio min : merceInventarioNegozioRepository.findAll())
        {
            if(min.getMerceAlPubblico().getPromozione().isDisponibile()) {
                minList.add(min);
            }
        }
        return minList;
    }
    public List<MerceInventarioNegozio> getMerciNonInPromozione() {
        List<MerceInventarioNegozio> minList=new ArrayList<>();
        for (MerceInventarioNegozio min : merceInventarioNegozioRepository.findAll())
        {
            if(!min.getMerceAlPubblico().getPromozione().isDisponibile()) {
                minList.add(min);
            }
        }
        return minList;
    }
    @GetMapping("merceInPromozione")
    public String merceInPromozione(Model model)
    {
        model.addAttribute("minList",getPromozioniA());
        return "showPromozioni";
    }
    @GetMapping("merceInPromozione/delete/{id}")
    public String removePromozione(@PathVariable Long id,Model model) {
        MerceInventarioNegozio min = merceInventarioNegozioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promozione :" + id));
        min.getMerceAlPubblico().getPromozione().setDisponibile(false);
        model.addAttribute("minList",getPromozioniA());
        return "showPromozioni";
    }
    @GetMapping("merceNonInPromozione")
    public String merceNonInPromozione(Model model) {
        model.addAttribute("minList",getMerciNonInPromozione());
        return "showMerceNonInPromozione";
    }
    @GetMapping("merceNonInPromozione/formAddPromozione/{id}")
    public String addPromozioneForm(@PathVariable Long id,Model model) {
        Promozione promozione=merceInventarioNegozioRepository.findById(id).get().getMerceAlPubblico().getPromozione();
        model.addAttribute("promozione",promozione);
        return "addPromozione";
    }
    @PostMapping("merceInPromozione/addPromozione/{id}")
    public String addPromozione(@PathVariable("id") Long id, Promozione promozione,Model model) {
        promozione.setDisponibile(true);
        promozioneRepository.save(promozione);
        model.addAttribute("minList",getPromozioniA());
        return "showPromozioni";
    }

    public void addPromozione(MerceInventarioNegozio miv, LocalDate di, LocalDate df, double pp){
        gestoreCommercianti.addPromozione(miv,di,df,pp);
    }

//    @FXML
    void addPromozioneButtonEvent(){
//        addPromozione(listaPromozioniPossibili.getSelectionModel().getSelectedItem(),dataI.getValue(), dataF.getValue(),Double.parseDouble(percentualePromozione.getText()));
//        getPromozioniAttive();
//        initFieldPromozioni();
    }

    public void rimuoviPromozione(MerceInventarioNegozio min){
        gestoreCommercianti.rimuoviPromozione(min);
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
    public Negozio getNegozio() {
        return negozioRepository.findAll().get(0);
    }
    public List<Corriere> getCorrieriDaAggiungere() {
        List<Corriere> corrieri=corriereRepository.findAll();
        corrieri.removeAll(getNegozio().getCorrieri());
        return corrieri;
    }
    public void addCorrieri(Corriere corriereDaAggiungere) {
        gestoreCommercianti.addCorrieri(corriereDaAggiungere);
    }
    @GetMapping("showCorrieriDaAggiungere")
    public String showCorrieriDaAggiunguere(Model model)
    {
        model.addAttribute("corrieriList",getCorrieriDaAggiungere());
        return "showCorrieriDaAggiungere";
    }
    @GetMapping("showCorrieriDaAggiungere/add/{id}")
    public String addCorriere(@PathVariable Long id,Model model) {
        Corriere corriere = corriereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        getNegozio().getCorrieri().add(corriere);
        negozioRepository.save(getNegozio());
        model.addAttribute("corrieriList",getCorrieriDaAggiungere());
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
    public User getClienteByEmail(String email) {
        System.out.println(email);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get();
        }
        throw new IllegalStateException("cliente non presente");
    }
    //METODO CHE STAVA SU GESTORE
    public void assunzioneAddettoGestore(User user){
        AddettoNegozio addettoNegozio = new AddettoNegozio(RuoloSistema.ADDETTONEGOZIO);
        user.setRuolo(addettoNegozio);
        ruoloRepository.save(addettoNegozio);
        getNegozio().addAddettoNegozio(addettoNegozio);
        negozioRepository.save(getNegozio());
    }
    @GetMapping("assunzioneAddetto")
    public String assunzioneForm() {
        return "assunzioneAddetto";
    }

    @PostMapping ("assunzioneAddetto")
    public String assumiCliente(String email,Model model) {
        User user = getClienteByEmail(email);
        userRepository.save(user);
        List<User> userList =new ArrayList<>();
        userList.add(user);
        model.addAttribute("userList",userList);
        return "clienteDaAssumere";
    }
    @GetMapping("assunzioneAddetto/{id}")
    public String assunzioneFinita(@PathVariable Long id) {
         User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user:" + id));
         assunzioneAddettoGestore(user);
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
    //METODO DEL GESTORE
    public List<MerceInventarioNegozio> getInventario() {
        Negozio negozio=negozioRepository.findAll().get(0);
        return negozio.getMerceInventarioNegozio();
    }
    @GetMapping("showInventario")
    public String showInventario(Model model) {
        model.addAttribute("minList",getInventario());
        return "showInventario";
    }
    @GetMapping("showInventario/removeForm/{id}")
    public String removeForm(@PathVariable Long id,Model model) {
        model.addAttribute("id",id);
        return "rimuoviMerce";
    }
    @GetMapping("showInventario/addForm")
    public String addForm() {
        return "addMerce";
    }
    @PostMapping("showInventario/remove/{id}")
    public String removeFromInventario(@PathVariable Long id,Double quantita,Model model) {
        MerceInventarioNegozio min = merceInventarioNegozioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid merce :" + id));
        min.setQuantita(min.getQuantita()-quantita);
        merceInventarioNegozioRepository.save(min);
        model.addAttribute("minList",getInventario());
        return "showInventario";
    }
    public void rimuoviMerce(List<MerceInventarioNegozio> min,double quantita) {
        gestoreCommercianti.removeMerce(min,quantita);
    }
    private void aggiungiMerce(Long id, String nome, String descrizione, Categoria categoria, double quantita, double prezzo, double sconto) {
        gestoreCommercianti.addMerce(id,nome,descrizione,categoria,quantita,prezzo,sconto);
    }
    private void modificaMerce(MerceInventarioNegozio min,double prezzo, double sconto, double quantita){
        gestoreCommercianti.modificaMerce(min,prezzo, sconto, quantita);
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
