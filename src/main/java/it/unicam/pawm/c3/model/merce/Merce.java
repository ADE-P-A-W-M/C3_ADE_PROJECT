package it.unicam.pawm.c3.model.merce;

import javax.persistence.*;

@Entity
public class Merce {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long ID;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private String descrizione;

    public Merce(String nome, Categoria categoria, String descrizione) {
        this.nome=nome;
        this.categoria=categoria;
        this.descrizione=descrizione;
    }

//    public Merce(long id, String nome, Categoria categoria, String descrizione) {
//        this.ID = id;
//        this.nome=nome;
//        this.categoria=categoria;
//        this.descrizione=descrizione;
//    }

    public Merce() {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /*@Override
    public String toString() {
        return  ID +
                ", " + nome  +
                ", " + categoria +
                ", " + descrizione;
    }*/

    @Override
    public String toString() {
        return "Merce{" +
                "ID=" + ID +
                ", nome='" + nome + '\'' +
                ", categoria=" + categoria +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
