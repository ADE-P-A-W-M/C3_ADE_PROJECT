package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCorrieri;
import org.springframework.stereotype.Component;


@Component
public class ICorriere {

    private GestoreCorrieri gestore;

//    public void init() {
//        getVenditeNonRitirateInventario();
//        getVenditeRitirateInventario();
//        getVenditeConsegnateInventario();
//        getVenditeDaRitirare();
//        getVenditePreseInCarico();
//    }

    /************Interfaccia Consulta Inventario********************/

//    public void getVenditeNonRitirateInventario() {
//        listaDaRitirare.getItems().clear();
//        listaDaRitirare.getItems().addAll(gestore.getVenditeDaRitirare());
//    }
//    public void getVenditeRitirateInventario() {
//        listaRitirate.getItems().clear();
//        listaRitirate.getItems().addAll(gestore.getVenditeRitirate());
//    }
//
//    public void getVenditeConsegnateInventario() {
//        listaConsegnate.getItems().clear();
//        listaConsegnate.getItems().addAll(gestore.getVenditeConsegnate());
//    }

    /************Interfaccia Preleva Vendita********************/

//    public void getVenditeDaRitirare(){
//        listaVenditeDaPrelevare.getItems().clear();
//        listaVenditeDaPrelevare.getItems().addAll(gestore.getVenditeDaRitirare());
//    }
//
//    public void prelevaVendita(List<VenditaSpedita> list) {
//        gestore.prelevaVendita(list);
//    }
//
//    @FXML
//    void prelevaVenditaButtonEvent(ActionEvent event) {
//        prelevaVendita(listaVenditeDaPrelevare.getSelectionModel().getSelectedItems());
//        //getVenditeDaRitirare();
//        init();
//    }

    /************Interfaccia Consegna Vendita********************/

//    public void getVenditePreseInCarico() {
//        listaVenditeDaConsegnare.getItems().clear();
//        listaVenditeDaConsegnare.getItems().addAll(gestore.getVenditeRitirate());
//    }
//
//    public void consegnaVendita(List<VenditaSpedita> list) {
//        gestore.consegnaVendita(list);
//    }
//
//    @FXML
//    void consegnaVenditaButtonEvent(ActionEvent event) {
//        consegnaVendita(listaVenditeDaConsegnare.getSelectionModel().getSelectedItems());
//        init();
//    }

    public void setGestoreCorriere(GestoreCorrieri gestoreCorrieri) {
        this.gestore = gestoreCorrieri;
    }
}

