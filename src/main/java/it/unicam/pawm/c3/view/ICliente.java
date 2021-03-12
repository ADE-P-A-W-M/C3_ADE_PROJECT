package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.gestori.GestoreClienti;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ICliente {

    private GestoreClienti gestoreClienti;

    public void init() {
        getPromozioni();
//        categoriePromozioni.getItems().addAll(Categoria.values());
    }
    /*****************Consulta Promozioni******************/

    private void getPromozioni(){
//        listViewPromozioni.getItems().clear();
//        listViewPromozioni.getItems().addAll(gestoreClienti.getPromozioni());
//        categoriePromozioni.getItems().addAll(Categoria.values());
    }
    public List<MerceInventarioNegozio> filtraPromozioniPerCategoria(Categoria categoria){
        return gestoreClienti.filtraPromozioniPerCategoria(categoria);
    }

//    @FXML
    void confermaFiltroPromozioniButtonEvent() {
//        promozioniFiltrate.getItems().clear();
//        promozioniFiltrate.getItems().addAll(filtraPromozioniPerCategoria(categoriePromozioni.getValue()));
    }
    /*****************Ricerca Prodotto******************/
    public List<Negozio> ricercaProdotto(String nome) {
        return gestoreClienti.ricercaProdotto(nome);
    }

//    @FXML
    void confermaRicerca() {
//        listaNegoziContenentiProdotto.getItems().clear();
//        listaNegoziContenentiProdotto.getItems().addAll(ricercaProdotto(nomeProdottoRicerca.getText()));
    }

    public void setGestoreClienti(GestoreClienti gestoreClienti) {
        this.gestoreClienti = gestoreClienti;
    }
}
