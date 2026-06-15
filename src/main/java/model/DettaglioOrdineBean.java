package model;

import java.io.Serializable;

public class DettaglioOrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int ordineId;
    private Integer codiceSet;
    private int quantita;
    private double prezzoAcquisto;
    private double iva;
    private String nomeSet;

    public DettaglioOrdineBean() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrdineId() { return ordineId; }
    public void setOrdineId(int ordineId) { this.ordineId = ordineId; }

    public Integer getCodiceSet() { return codiceSet; }
    public void setCodiceSet(Integer codiceSet) { this.codiceSet = codiceSet; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoAcquisto() { return prezzoAcquisto; }
    public void setPrezzoAcquisto(double prezzoAcquisto) { this.prezzoAcquisto = prezzoAcquisto; }

    public double getIva() { return iva; }
    public void setIva(double iva) { this.iva = iva; }
    
    public String getNomeSet() {
    	return nomeSet;
    }
    public void setNomeSet (String nomeSet) {
    	this.nomeSet = nomeSet;
    }
    public double getTotaleRiga() {
    	return this.prezzoAcquisto * this.quantita;
    }
}
