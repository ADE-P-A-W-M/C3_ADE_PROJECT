package it.unicam.pawm.c3;

import it.unicam.pawm.c3.carta.Carta;
import it.unicam.pawm.c3.merce.Categoria;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import it.unicam.pawm.c3.personale.AddettoNegozio;
import it.unicam.pawm.c3.personale.Corriere;
import it.unicam.pawm.c3.vendita.Vendita;
import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "negozio")
public class Negozio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nome;
    private String indirizzo;
    private String p_iva;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "categorie_negozi", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private List<Categoria> settori;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "negozio_fk", referencedColumnName = "id")
    private List<AddettoNegozio> addettiNegozio;

    @ManyToMany(cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "negozio_corriere", joinColumns = @JoinColumn(name = "negozio_id"),inverseJoinColumns = @JoinColumn(name = "corriere_id"))
    private List<Corriere> corrieri;

    @OneToMany(targetEntity = Carta.class, cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "negozio_fk", referencedColumnName = "id")
    private List<Carta> cartaList;

    @OneToMany(targetEntity = VenditaSpedita.class, cascade = CascadeType.MERGE )
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "negozio_ritiro_fk", referencedColumnName = "id")
    private List<VenditaSpedita> venditePuntiDiRitiro;

    @OneToMany(targetEntity = MerceInventarioNegozio.class, cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="negozio_fk",referencedColumnName = "id")
    private List<MerceInventarioNegozio> merceInventarioNegozioList;

    @OneToMany(targetEntity = Vendita.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "negozio_fk", referencedColumnName = "id")
    private List<Vendita> vendite;

    private boolean disponibilitaRitiro;

    public Negozio(String nome, String indirizzo, String p_iva, List<Categoria> categorie){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.p_iva = p_iva;
        this.settori = categorie;
        this.addettiNegozio = new ArrayList<>();
        this.corrieri = new ArrayList<>();
        this.cartaList = new ArrayList<>();
        this.merceInventarioNegozioList = new ArrayList<>();
        this.vendite = new ArrayList<>();
        this.venditePuntiDiRitiro = new ArrayList<>();
        this.disponibilitaRitiro = true;
    }

    public Negozio() {
    }

    public List<MerceInventarioNegozio> getMerceInventarioNegozio() {
        return this.merceInventarioNegozioList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getP_iva() {
        return p_iva;
    }

    public void setP_iva(String p_iva) {
        this.p_iva = p_iva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public String getP_Iva() {
        return p_iva;
    }

    public List<Categoria> getCategorie() {
        return this.settori;
    }

    public List<Carta> getCarte() {
        return this.cartaList;
    }

    public void addCarta(Carta carta) {
        getCarte().add(carta);
    }

    public List<Corriere> getCorrieri() {
        return corrieri;
    }

    public void addCorriere(Corriere corriere) {
        corrieri.add(corriere);
    }

    public void addMerceInventarioNegozio(MerceInventarioNegozio merceInventarioNegozio) {
        this.merceInventarioNegozioList.add(merceInventarioNegozio);
    }

    public void removeMerciInventarioNegozio(List<MerceInventarioNegozio> merci) {
        this.merceInventarioNegozioList.removeAll(merci);
    }

    public List<Vendita> getVendite() {
        return this.vendite;
    }

    public void addVendita(Vendita v) {
        this.vendite.add(v);
    }

    public List<VenditaSpedita> getVenditeNegozioRitiro() {
        return venditePuntiDiRitiro;
    }

    public void addVenditaInNegozioRitiro(VenditaSpedita vs) {
        this.venditePuntiDiRitiro.add(vs);
    }

    public void removeVenditeInNegozioRitiro(List<VenditaSpedita> list) {
        this.venditePuntiDiRitiro.removeAll(list);
    }

    public boolean getDisponibilitaRitiro() {
        return disponibilitaRitiro;
    }

    public void setDisponibilitaRitiro(boolean disponibilita) {
        this.disponibilitaRitiro = disponibilita;
    }

    public List<AddettoNegozio> getAddetti() {
        return addettiNegozio;
    }

    public void addAddettoNegozio(AddettoNegozio an) {
        this.addettiNegozio.add(an);
    }

    public void removeAddettoNegozio(AddettoNegozio an) {
        this.addettiNegozio.remove(an);
    }

    @Override
    public String toString() {
        return nome + ", " + indirizzo;
    }

}
