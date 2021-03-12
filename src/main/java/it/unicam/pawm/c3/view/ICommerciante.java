package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCommercianti;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Corriere;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ICommerciante {

    private GestoreCommercianti gestoreCommercianti;

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

    public void addPromozione(MerceInventarioNegozio miv, LocalDate di, LocalDate df, double pp){
        gestoreCommercianti.addPromozione(miv,di,df,pp);
    }

//    @FXML
    void addPromozioneButtonEvent(){
//        addPromozione(listaPromozioniPossibili.getSelectionModel().getSelectedItem(),dataI.getValue(), dataF.getValue(),Double.parseDouble(percentualePromozione.getText()));
//        getPromozioniAttive();
//        initFieldPromozioni();
    }

    public void rimuoviPromozione(List<MerceInventarioNegozio> lista){
        gestoreCommercianti.rimuoviPromozione(lista);
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

    public void addCorrieri(List<Corriere> corrieriDaAggiungere) {
        gestoreCommercianti.addCorrieri(corrieriDaAggiungere);
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

    public void assunzioneAddetto(Cliente cliente){
        gestoreCommercianti.assunzioneAddetto(cliente);
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
