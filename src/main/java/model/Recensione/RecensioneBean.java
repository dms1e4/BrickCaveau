package model.Recensione;

import java.io.Serializable;
import java.sql.Date;

public class RecensioneBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int utenteId;
    private int codiceSet;
    private int rating;
    private String testo;
    private Date dataRecensione;

    // Campi per front-end
    private String nomeUtente;
    private String cognomeUtente;

    public RecensioneBean() {}

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public int getCodiceSet() { return codiceSet; }
    public void setCodiceSet(int codiceSet) { this.codiceSet = codiceSet; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }

    public Date getDataRecensione() { return dataRecensione; }
    public void setDataRecensione(Date dataRecensione) { this.dataRecensione = dataRecensione; }

    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    public String getCognomeUtente() { return cognomeUtente; }
    public void setCognomeUtente(String cognomeUtente) { this.cognomeUtente = cognomeUtente; }
}
