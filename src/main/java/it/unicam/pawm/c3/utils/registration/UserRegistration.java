package it.unicam.pawm.c3.utils.registration;

/**
 * Classe che ha la responsabilita di rappresentare
 * uno user durante la registrazione
 */
public class UserRegistration {
    private String nome;
    private String cognome;
    private String email;
    private String password;

    public UserRegistration() {

    }

    public UserRegistration(String nome, String cognome, String email, String password) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
