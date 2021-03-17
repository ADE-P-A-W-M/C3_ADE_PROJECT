package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.Negozio;
import it.unicam.pawm.c3.gestori.GestoreClienti;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.Merce;
import it.unicam.pawm.c3.merce.MerceAlPubblico;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(path = "/cliente")
public class ICliente {

    @Autowired
    private GestoreAccessi gestoreAccessi;
    @Autowired
    private GestoreClienti gestoreClienti;

    @Autowired
    public ICliente() {
        this.gestoreClienti = new GestoreClienti();
        this.gestoreAccessi = new GestoreAccessi();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        gestoreClienti.setCliente(gestoreAccessi.homeCliente(email));
        return "homeCliente";
    }

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
//        Merce merce = new Merce("Ipad", Categoria.ABBIGLIAMENTO, "ipad terza generazione");
//        MerceAlPubblico merceAlPubblico = new MerceAlPubblico(999, merce);
//        merceAlPubblico.setPromozione(LocalDate.now(),LocalDate.now().plusDays(40),10,45);
//        MerceInventarioNegozio min = new MerceInventarioNegozio(20, merceAlPubblico);
//        MerceInventarioNegozio min1 = new MerceInventarioNegozio(36, merceAlPubblico);
//        List<MerceInventarioNegozio> minList=new ArrayList<>();
//        if(min.getMerceAlPubblico().getMerce().getCategoria()==categoria) {
//            minList.add(min);
//        }
//        if(min1.getMerceAlPubblico().getMerce().getCategoria()==categoria) {
//            minList.add(min1);
//        }
//        return minList;
    }

//    @FXML
    void confermaFiltroPromozioniButtonEvent() {
//        promozioniFiltrate.getItems().clear();
//        promozioniFiltrate.getItems().addAll(filtraPromozioniPerCategoria(categoriePromozioni.getValue()));
    }
    @GetMapping("promozione/{categoria}")
    public String filtraPromozioni(@PathVariable Categoria categoria, Model model){
        List<MerceInventarioNegozio> min=filtraPromozioniPerCategoria(categoria);
        model.addAttribute("min",min);
        return "promozioniPerCategoria";
    }
    /*****************Ricerca Prodotto******************/

    public List<Negozio> ricercaProdotto(String nome) {
//        Merce merce = new Merce("Ipad", Categoria.ABBIGLIAMENTO, "ipad terza generazione");
//        MerceAlPubblico merceAlPubblico = new MerceAlPubblico(999, merce);
//        merceAlPubblico.setPromozione(LocalDate.now(),LocalDate.now().plusDays(40),10,45);
//        MerceInventarioNegozio min = new MerceInventarioNegozio(20, merceAlPubblico);
//        Negozio negozio=new Negozio("capannina","via tampa bay","456789",List.of(Categoria.ABBIGLIAMENTO,Categoria.ALIMENTI));
//        negozio.addMerceInventarioNegozio(min);
//        List<Negozio> negList=new ArrayList<>();
//        negList.add(negozio);
//        return negList;
        return gestoreClienti.ricercaProdotto(nome);
    }
    @GetMapping("ricerca/{nomeRicerca}")
    public String filtraPromozioni(@PathVariable String nomeRicerca, Model model){
        List<Negozio> negList=ricercaProdotto(nomeRicerca);
        model.addAttribute("negList",negList);
        return "ricercaProdotto";
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
