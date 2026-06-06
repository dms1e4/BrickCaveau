package model.Utente;

import java.io.Serializable;

public class UtenteBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private boolean isAdmin;

    public UtenteBean() {}

    
    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public boolean is_Admin() { return isAdmin; }
    public void set_Admin(boolean isAdmin) { this.isAdmin = isAdmin; }

    @Override
    public String toString() {
        return "UtenteBean{" +
                "idUtente=" + idUtente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
