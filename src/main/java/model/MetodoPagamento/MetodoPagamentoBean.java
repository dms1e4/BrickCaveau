package model.MetodoPagamento;

import java.sql.Date;
import java.io.Serializable;

public class MetodoPagamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Date scadenza;
    private String tipo;
    private String ultime4Cifre;
    private int utenteId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getScadenza() { return scadenza; }
    public void setScadenza(Date scadenza) { this.scadenza = scadenza; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getUltime4Cifre() { return ultime4Cifre; }
    public void setUltime4Cifre(String ultime4Cifre) { this.ultime4Cifre = ultime4Cifre; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }
}