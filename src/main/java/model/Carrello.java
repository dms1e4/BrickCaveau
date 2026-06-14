package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.SetLego.SetLegoBean;

public class Carrello implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class ElementoCarrello implements Serializable {
        private static final long serialVersionUID = 1L;
        private SetLegoBean prodotto;
        private int quantita;

        public ElementoCarrello(SetLegoBean prodotto, int quantita) {
            this.prodotto = prodotto;
            this.quantita = quantita;
        }

        public SetLegoBean getProdotto() { return prodotto; }
        public int getQuantita() { return quantita; }
        public void setQuantita(int quantita) { this.quantita = quantita; }
        public double getPrezzoTotaleElemento() { return prodotto.getPrezzo() * quantita; }
    }

    private Map<Integer, ElementoCarrello> elementi;

    public Carrello() {
        this.elementi = new HashMap<>();
    }

    // aggiunge un set o incrementa quantità (se è già presente)
    public void aggiungiProdotto(SetLegoBean prodotto, int qta) {
        int codice = prodotto.getCodiceSet();
        if (elementi.containsKey(codice)) {
            ElementoCarrello elem = elementi.get(codice);
            elem.setQuantita(elem.getQuantita() + qta);
        } else {
            elementi.put(codice, new ElementoCarrello(prodotto, qta));
        }
    }

    public void rimuoviProdotto(int codiceSet) {
        elementi.remove(codiceSet);
    }

 
    public void aggiornaQuantita(int codiceSet, int qta) {
        if (elementi.containsKey(codiceSet)) {
            if (qta <= 0) {
                rimuoviProdotto(codiceSet);
            } else {
                elementi.get(codiceSet).setQuantita(qta);
            }
        }
    }


    public Collection<ElementoCarrello> getElementi() {
        return elementi.values();
    }

    // calcolo totale
    public double getPrezzoTotaleComplessivo() {
        double totale = 0;
        for (ElementoCarrello elem : elementi.values()) {
            totale += elem.getPrezzoTotaleElemento();
        }
        return totale;
    }

    public void svuota() {
        elementi.clear();
    }
}
