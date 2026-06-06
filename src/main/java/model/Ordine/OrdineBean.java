package model.Ordine;

import java.io.Serializable;
import java.sql.Date;

public class OrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Date dataOrdine;
    private double totale;
    private int utenteId;
    private Integer indirizzoId;
    private Integer metodoPagamentoId;

    public OrdineBean() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Date dataOrdine) { this.dataOrdine = dataOrdine; }

    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public Integer getIndirizzoId() { return indirizzoId; }
    public void setIndirizzoId(Integer indirizzoId) { this.indirizzoId = indirizzoId; }

    public Integer getMetodoPagamentoId() { return metodoPagamentoId; }
    public void setMetodoPagamentoId(Integer metodoPagamentoId) { this.metodoPagamentoId = metodoPagamentoId; }
}
