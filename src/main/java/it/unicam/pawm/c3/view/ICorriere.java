package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCorrieri;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.Merce;
import it.unicam.pawm.c3.merce.MerceAlPubblico;
import it.unicam.pawm.c3.persistenza.MerceAlPubblicoRepository;
import it.unicam.pawm.c3.persistenza.MerceRepository;
import it.unicam.pawm.c3.persistenza.MerceVenditaRepository;
import it.unicam.pawm.c3.persistenza.VenditaSpeditaRepository;
import it.unicam.pawm.c3.vendita.MerceVendita;
import it.unicam.pawm.c3.vendita.StatoConsegna;
import it.unicam.pawm.c3.vendita.Vendita;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/corriere")
public class ICorriere {

    @Autowired
    private VenditaSpeditaRepository venditaSpeditaRepository;
    @Autowired
    private MerceRepository merceRepository;
    @Autowired
    private MerceAlPubblicoRepository merceAlPubblicoRepository;
    @Autowired
    private MerceVenditaRepository merceVenditaRepository;


    private GestoreCorrieri gestore;

//   @GetMapping("/")
//    public void init() {
//
//    }

    /************Interfaccia Consulta Inventario********************/

    @GetMapping("/gestioneInventarioCorriere")
    public String gestioneInventario(Model model){
     //   List<VenditaSpedita> venditeDaRitirare = gestore.getVenditeDaRitirare();
     //   List<VenditaSpedita> venditeRitirate = gestore.getVenditeRitirate();
     //   List<VenditaSpedita> venditeConsegnate = gestore.getVenditeConsegnate();
        List<VenditaSpedita> venditeDaRitirare = new ArrayList<>();
        for (VenditaSpedita VS : venditaSpeditaRepository.findAll()) {
            if(VS.getStatoConsegna().equals(StatoConsegna.IN_ATTESA_DI_RITIRO)){
                venditeDaRitirare.add(VS);
            }
        }
        List<VenditaSpedita> venditeRitirate = new ArrayList<>();
        for (VenditaSpedita VS : venditaSpeditaRepository.findAll()) {
            if(VS.getStatoConsegna().equals(StatoConsegna.RITIRATO)){
                venditeRitirate.add(VS);
            }
        }

        List<VenditaSpedita> venditeConsegnate = new ArrayList<>();
        for (VenditaSpedita VS : venditaSpeditaRepository.findAll()) {
            if(VS.getStatoConsegna().equals(StatoConsegna.CONSEGNATO_AL_NEGOZIO)){
                venditeConsegnate.add(VS);
            }
        }

        model.addAttribute("daRitirare",venditeDaRitirare);
        model.addAttribute("ritirate",venditeRitirate);
        model.addAttribute("consegnate",venditeConsegnate);

    return "gestioneInventarioCorriere";
}
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

    @GetMapping("/venditeDaRitirare")
    public String getVenditeDaRitirare(Model model){
        List<VenditaSpedita> venditeDaRitirare = new ArrayList<>();
        for (VenditaSpedita VS : venditaSpeditaRepository.findAll()) {
            if(VS.getStatoConsegna().equals(StatoConsegna.IN_ATTESA_DI_RITIRO)){
                venditeDaRitirare.add(VS);
            }
        }
        model.addAttribute("daRitirare",venditeDaRitirare);

        return "venditeDaRitirare";

    }
    @GetMapping("/ritiro/{id}")
    public String prelevaVendita(@PathVariable Long id) {
        Optional<VenditaSpedita> VS = venditaSpeditaRepository.findById(id);
        VS.get().setStatoConsegna(StatoConsegna.RITIRATO);
        venditaSpeditaRepository.save(VS.get());
        return "venditeDaRitirare";
    }
//
//    @FXML
//    void prelevaVenditaButtonEvent(ActionEvent event) {
//        prelevaVendita(listaVenditeDaPrelevare.getSelectionModel().getSelectedItems());
//        //getVenditeDaRitirare();
//        init();
//    }

    /************Interfaccia Consegna Vendita********************/

    @GetMapping("/consegnavendita")
    public String getVenditePreseInCarico(Model model) {
        List<VenditaSpedita> venditeRitirate = new ArrayList<>();
        for (VenditaSpedita VS : venditaSpeditaRepository.findAll()) {
            if(VS.getStatoConsegna().equals(StatoConsegna.RITIRATO)){
                venditeRitirate.add(VS);
            }
        }
        model.addAttribute("ritirate",venditeRitirate);

        return "venditeDaConsegnare";
   }

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

