package model;

import java.io.Serializable;

public class SetLegoBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private int codiceSet;
  private String nome;
  private int annoUscita;
  private Integer annoRitiro;
  private int nPezzi;
  private String descrizione;
  private double prezzo;
  private double iva;
  private String tema;
  private int quantitaMagazzino;

  public SetLegoBean() {}

  public int getCodiceSet() { return codiceSet; }
  public void setCodiceSet(int codiceSet) { this.codiceSet = codiceSet; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public int getAnnoUscita() { return annoUscita; }
  public void setAnnoUscita(int annoUscita) { this.annoUscita = annoUscita; }

  public Integer getAnnoRitiro() { return annoRitiro; }
  public void setAnnoRitiro(Integer annoRitiro) { this.annoRitiro = annoRitiro; }

  public int getnPezzi() { return nPezzi; }
  public void setnPezzi(int nPezzi) { this.nPezzi = nPezzi; }

  public String getDescrizione() { return descrizione; }
  public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

  public double getPrezzo() { return prezzo; }
  public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

  public double getIva() { return iva; }
  public void setIva(double iva) { this.iva = iva; }
  
  public String getTema() { return tema; }
  public void setTema(String tema) { this.tema = tema; }

  public int getQuantitaMagazzino() { return quantitaMagazzino; }
  public void setQuantitaMagazzino(int quantitaMagazzino) { this.quantitaMagazzino = quantitaMagazzino; }
}
