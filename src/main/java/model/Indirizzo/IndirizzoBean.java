package model.Indirizzo;

import java.io.Serializable;

public class IndirizzoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int utenteId;
    private int Id;
    private String nCivico;
    private String via;
    private String citta;
    private String cap;
    private String provincia;
    private String nazione;

    public IndirizzoBean() {}

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public int getId() { return Id; }
    public void setId(int Id) { this.Id = Id; }
    
    public String getnCivico() { return nCivico; }
    public void setnCivico(String nCivico) { this.nCivico = nCivico; }
    
    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }
    
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    
    public String getCap() { return cap; }
    public void setCap(String CAP) { this.cap = CAP; }
    
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    
    public String getNazione() { return nazione; }
    public void setNazione(String nazione) { this.nazione = nazione; }
    
    
    
    
    


}

