package it.unicam.pawm.c3.view;

import it.unicam.pawm.c3.gestori.GestoreCorrieri;
import it.unicam.pawm.c3.gestorispecifici.GestoreAccessi;
import it.unicam.pawm.c3.persistenza.*;
import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.personale.Ruolo;
import it.unicam.pawm.c3.personale.User;
import it.unicam.pawm.c3.vendita.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CorriereRepository corriereRepository;
    @Autowired
    private GestoreAccessi gestoreAccessi;
    @Autowired
    private GestoreCorrieri gestoreCorrieri;

    @Autowired
    public ICorriere() {
        this.gestoreCorrieri = new GestoreCorrieri();
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails){
//        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
//        if(user.isPresent()){
//            Iterator<Corriere> corriereIterator = corriereRepository.findAll().iterator();
//            while (corriereIterator.hasNext()){
//                Corriere corriere = corriereIterator.next();
//                for(Ruolo ruolo : user.get().getRuolo()){
//                    if(ruolo.getId() == corriere.getId()) {
//                        gestoreCorrieri.setCorriere(corriere);
//                    }
//                }
//            }
//        }
        String email = userDetails.getUsername();
        gestoreCorrieri.setCorriere(gestoreAccessi.homeCorriere(email));
        System.out.println(gestoreCorrieri.getCorriere());
        return "homeCorriere";
    }




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

    @GetMapping("/consegna/{id}")
    public String consegnaVendita(@PathVariable Long id) {
        Optional<VenditaSpedita> VS = venditaSpeditaRepository.findById(id);
        if(VS.get().getLuogoDiRitiro().equals(LuogoDiRitiro.NEGOZIO)) {
            VS.get().setStatoConsegna(StatoConsegna.CONSEGNATO_AL_NEGOZIO);

        }else if(VS.get().getLuogoDiRitiro().equals(LuogoDiRitiro.DOMICILIO)) {
            VS.get().setStatoConsegna(StatoConsegna.CONSEGNATO_AL_NEGOZIO);
        }
        venditaSpeditaRepository.save(VS.get());
        return "venditeDaConsegnare";
    }
//
//    @FXML
//    void consegnaVenditaButtonEvent(ActionEvent event) {
//        consegnaVendita(listaVenditeDaConsegnare.getSelectionModel().getSelectedItems());
//        init();
//    }

    public void setGestoreCorriere(GestoreCorrieri gestoreCorrieri) {
        this.gestoreCorrieri = gestoreCorrieri;
    }
}

