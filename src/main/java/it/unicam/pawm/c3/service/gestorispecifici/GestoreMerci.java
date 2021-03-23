package it.unicam.pawm.c3.service.gestorispecifici;

import it.unicam.pawm.c3.model.Negozio;
import it.unicam.pawm.c3.model.merce.*;
import it.unicam.pawm.c3.repository.MerceAlPubblicoRepository;
import it.unicam.pawm.c3.repository.MerceInventarioNegozioRepository;
import it.unicam.pawm.c3.repository.MerceRepository;
import it.unicam.pawm.c3.repository.NegozioRepository;
import it.unicam.pawm.c3.model.vendita.MerceVendita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GestoreMerci {

    @Autowired
    private MerceInventarioNegozioRepository merceInventarioNegozioRepository;
    @Autowired
    private MerceRepository merceRepository;
    @Autowired
    private MerceAlPubblicoRepository merceAlPubblicoRepository;
    @Autowired
    private NegozioRepository negozioRepository;

    @Autowired
    public GestoreMerci() {}

    /**
     * il metodo ricerca il prezzo dell'id merce inserito,se non viene trovata nessuna merce con quel id
     *
     * @param id l'id della merce da ricercare
     * @param negozio il negozio in cui cercare
     * @param quantita verifica che la quantita sia opportuna
     * @return il prezzo,zero altrimenti
     */
    public double searchPrezzo(long id, Negozio negozio, double quantita) {
        Iterator<MerceInventarioNegozio> min = negozio.getMerceInventarioNegozio().iterator();
        while(min.hasNext()){
            MerceInventarioNegozio m = min.next();
            if(m.getQuantita() >= quantita){
                MerceAlPubblico map = m.getMerceAlPubblico();
                Merce merce = map.getMerce();
                if(merce.getID() == id){
                    return map.getPrezzo();
                }
            }
        }
        return 0;
    }

    /**
     * il metodo serve per ottenere lo sconto di una merce con l'id indicato
     *
     * @param id della merce in questione
     * @param negozio dove si trova la merce
     * @return lo sconto,zero altrimenti
     */
    public double getSconto(long id, Negozio negozio) {
        Iterator<MerceInventarioNegozio> it = negozio.getMerceInventarioNegozio().iterator();
        while (it.hasNext()){
            MerceInventarioNegozio min = it.next();
            if(min.getMerceAlPubblico().getMerce().getID()==id){
                return min.getMerceAlPubblico().getSconto();
            }
        }
        return 0;
    }

    /**
     * il metodo serve per ridurre la quantita di un merce nell'inventario dell'ammontare che sta per essere venduto
     *
     * @param mp merce a cui scalare la quantita
     * @param quantita da scalare
     * @param negozio dove si trova la merce
     */
    public void scaloQuantita(MerceAlPubblico mp , double quantita, Negozio negozio){
        for(MerceInventarioNegozio min : negozio.getMerceInventarioNegozio()){
            if(min.getMerceAlPubblico().equals(mp)){
                min.setQuantita(min.getQuantita()-quantita);
                merceInventarioNegozioRepository.save(min);
            }
        }
    }

    /**
     * il metodo reinserisce la quantita che stava per essere venduta nell'inventario
     *
     * @param negozio dove stava avvenendo la vendita
     * @param merciCarrello le merci che necessitano del reinserimento nell'inventario
     */
    public void reinserimentoQuantita(Negozio negozio, List<MerceVendita> merciCarrello){
        for(MerceVendita mv : merciCarrello){
            for(MerceInventarioNegozio min : negozio.getMerceInventarioNegozio()){
                if(mv.getMerceAlPubblico().equals(min.getMerceAlPubblico())){
                    min.setQuantita(min.getQuantita()+mv.getQuantitaVenduta());
                    merceInventarioNegozioRepository.save(min);
                }
            }
        }
    }

    /**
     * il metodo restituisce le info di una merce
     *
     * @param min la merce di cui ottenere le info
     * @return le info dell merce
     */
    public String getInfoMerce(MerceInventarioNegozio min) {
        String promozione;
        if(min.getMerceAlPubblico().getPromozione().isDisponibile()){
            promozione = ", si trova in promozione";
        } else {
            promozione = ", non si trova in promozione";
        }
        return "Nome: " + min.getMerceAlPubblico().getMerce().getNome() + ", ID: " +
                min.getMerceAlPubblico().getMerce().getID() + ", categoria: " + min.getMerceAlPubblico().getMerce().getCategoria() +
                ", in quantita: " + min.getQuantita() + ", con uno sconto: " + min.getMerceAlPubblico().getSconto()
                + promozione;
    }

    /**
     * Il metodo serve per recupare la merce tramite il suo id
     *
     * @param id della merce
     * @return merce inventario negozio
     */
    public MerceInventarioNegozio getInfoMerce(Long id){
        Optional<MerceInventarioNegozio> min = merceInventarioNegozioRepository.findById(id);
        if(min.isPresent()){
            return min.get();
        }
        return null;
    }

    /**
     * Il metodo serve a verificare se una merce con l id selezionato è gia presente nel sistema
     *
     * @param id della merce da verificare
     * @return se la merce è presente o meno
     */
    public boolean verificaIdMerce(Long id) {
        Optional<Merce> merce = merceRepository.findById(id);
        if(merce.isPresent()){
            return true;
        }
        return false;
    }

    /**
     * il metodo serve ad aggiungere una nuova merce in un megozio
     *
     * @param nome della merce da inserire
     * @param descrizione della merce da inserire
     * @param categoria della merce da inserire
     * @param quantita della merce da inserire
     * @param prezzo della merce da inserire
     * @param sconto della merce da inserire
     * @param negozio dove inserire la merce
     */
    public void addMerce(Long id, String nome, String descrizione, Categoria categoria, double quantita, double prezzo, double sconto, Negozio negozio) {
        Merce m;
        Optional<Merce> merce = merceRepository.findById(id);
        if(!merce.isPresent()){
            m = new Merce(nome, categoria, descrizione);
        } else {
            m = merce.get();
        }
        merceRepository.save(m);
        MerceAlPubblico map = new MerceAlPubblico(prezzo, m, sconto);
        MerceInventarioNegozio min = new MerceInventarioNegozio(quantita, map);
        merceInventarioNegozioRepository.save(min);
        negozio.addMerceInventarioNegozio(min);
        negozioRepository.save(negozio);
    }

    /**
     * Il metodo serve per aggiungere e salvare la merce
     *
     * @param nome della merce da aggiungere
     * @param descrizione della merce da aggiungere
     * @param categoria della merce da aggiungere
     * @param quantita delle merce da aggiungere
     * @param prezzo della merce da aggiungere
     * @param sconto della merce da aggiungere
     * @param negozio della merce da aggiungere
     */
    public void addMerce(String nome, String descrizione, Categoria categoria, Double quantita, Double prezzo, Double sconto, Negozio negozio) {
        Merce m = new Merce(nome, categoria, descrizione);
        merceRepository.save(m);
        MerceAlPubblico map = new MerceAlPubblico(prezzo, m, sconto);
        MerceInventarioNegozio min = new MerceInventarioNegozio(quantita, map);
        merceInventarioNegozioRepository.save(min);
        negozio.addMerceInventarioNegozio(min);
        negozioRepository.save(negozio);
    }

    /**
     * Il metodo serve per modificare una merce nel prezzo,quantita, e sconto
     *
     * @param min merce da modificare
     * @param prezzo da modificare
     * @param sconto da modificare
     * @param quantita da modificare
     */
    public void modificaMerce(Negozio negozio,MerceInventarioNegozio min, double prezzo, double sconto, double quantita) {
        min.getMerceAlPubblico().setPrezzo(prezzo);
        min.getMerceAlPubblico().setSconto(sconto);
        min.setQuantita(quantita);
        merceInventarioNegozioRepository.save(min);
        negozioRepository.save(negozio);
    }

    /**
     * il metodo serve a rimuovere dall'inventario del negozio specificato una determinata quantita di una lista di merci
     *
     * @param min merce da rimuove
     * @param quantita da rimuovere
     * @param negozio dove si trovano le merci
     */
    public void removeMerce(MerceInventarioNegozio min, double quantita, Negozio negozio){
        double nuovaQuantita=min.getQuantita()-quantita;
        min.setQuantita(nuovaQuantita);
        merceInventarioNegozioRepository.save(min);
        negozioRepository.save(negozio);
    }

    /**
     * il metodo serve ad ottenere una merce con l'id specificato e se quest'ultima non viene trovata,viene creata
     *
     * @param id della merce desiderata
     * @param prezzo della merce
     * @param quantita della merce
     * @param negozio dove si trova la merce
     * @return la merce
     */
    public MerceAlPubblico getMerce(long id, double prezzo, double quantita, Negozio negozio) {
        Iterator<MerceInventarioNegozio> it = negozio.getMerceInventarioNegozio().iterator();
        while (it.hasNext()){
            MerceInventarioNegozio min = it.next();
            if(min.getMerceAlPubblico().getMerce().getID()==id){
                return min.getMerceAlPubblico();
            }
        }
        MerceAlPubblico ma;
        Optional<Merce> merceOptional = merceRepository.findById(id);
        if(merceOptional.isPresent()){
            ma = new MerceAlPubblico((prezzo/quantita),merceOptional.get());
        } else {
            Merce merce1 = new Merce();
            merceRepository.save(merce1);
            ma = new MerceAlPubblico((prezzo/quantita),merce1);
        }
        merceAlPubblicoRepository.save(ma);
        return ma;
    }

    /**
     * il metodo serve ad ottenere le promozioni attive nel negozio
     *
     * @param negozio dove ricercare le promozioni
     * @return lista merci con promozioni attive
     */
    public List<MerceInventarioNegozio> getPromozioniAttive(Negozio negozio) {
        List<MerceInventarioNegozio> list = new ArrayList<>();
        if(!negozio.getMerceInventarioNegozio().isEmpty()){
            Iterator<MerceInventarioNegozio> iterator = negozio.getMerceInventarioNegozio().iterator();
            while (iterator.hasNext()){
                MerceInventarioNegozio min = iterator.next();
                MerceAlPubblico map = min.getMerceAlPubblico();
                if(map.getPromozione().isDisponibile()){
                    list.add(min);
                }
            }
        }
        return list;
    }

    /**
     * il metodo serve ad ottenere le merci sulle quali è possibile applicare una promozione
     *
     * @param negozio delle merci dove applicare una promozione
     * @return merci
     */
    public List<MerceInventarioNegozio> getPromozioniPossibili(Negozio negozio) {
        List<MerceInventarioNegozio> list = new ArrayList<>();
        if(!negozio.getMerceInventarioNegozio().isEmpty()){
            Iterator<MerceInventarioNegozio> iterator = negozio.getMerceInventarioNegozio().iterator();
            while (iterator.hasNext()){
                MerceInventarioNegozio min = iterator.next();
                if(!min.getMerceAlPubblico().getPromozione().isDisponibile()){
                    list.add(min);
                }
            }
        }
        return list;
    }

    /**
     * il metodo serve per aggiungere una promozione alla merce specificata
     *
     * @param miv la merce dove applicare la promozione
     * @param di data di inizio della promozione
     * @param df data di fine della promozione
     * @param pp percentuale sconto della promozione
     */
    public void addPromozione(MerceInventarioNegozio miv, LocalDate di, LocalDate df, double pp) {
        MerceAlPubblico map = miv.getMerceAlPubblico();
        double prezzo = (miv.getMerceAlPubblico().getPrezzo() - ((pp/100)*map.getPrezzo()));
        map.setPromozione(di,df,pp, prezzo);
        merceAlPubblicoRepository.save(map);
    }

    /**
     * il metodo serve per disattivare la promozione sulle specificate
     *
     * @param min merce da cui rimuovere la promozione
     */
    public void rimuoviPromozione(MerceInventarioNegozio min) {
            min.getMerceAlPubblico().getPromozione().setDisponibile(false);
            merceAlPubblicoRepository.save(min.getMerceAlPubblico());
    }

    /**
     * il metodo serve per ottenere la lista delle promozioni attive filtrate per categoria
     *
     * @param categoria delle promozioni
     * @param promozioni merci su cui è attiva una promozione
     * @return merci su cui è attiva una promozione della categoria specificata
     */
    public List<MerceInventarioNegozio> filtraPromozioniPerCategoria(Categoria categoria, List<MerceInventarioNegozio> promozioni) {
        List<MerceInventarioNegozio> minList=new ArrayList<>();
        Iterator<MerceInventarioNegozio> minAll = promozioni.iterator();
        while(minAll.hasNext()) {
            MerceInventarioNegozio min= minAll.next();
            if(min.getMerceAlPubblico().getMerce().getCategoria().equals(categoria)){
                minList.add(min);
            }
        }
        return minList;
    }
    public Promozione getPromozione(Long id) {
        return merceInventarioNegozioRepository.findById(id).get().getMerceAlPubblico().getPromozione();
    }

}
