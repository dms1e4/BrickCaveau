package model.Recensione;

import java.sql.Date;

public class RecensioneBean {
    private int utenteId;
    private int codiceSet;
    private int rating; // Da 1 a 5
    private String testo;
    private Date dataRecensione;
    
    // campo extra per JSP: nome dell'utente che ha scritto la recensione
    private String nomeUtente; 


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
}