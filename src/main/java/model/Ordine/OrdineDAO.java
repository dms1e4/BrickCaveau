package model.Ordine;

import java.sql.*;

import java.util.*;
import javax.sql.DataSource;

import model.Carrello;

public class OrdineDAO {

    private DataSource ds = null;

    public OrdineDAO(DataSource ds) {
        this.ds = ds;
    }


    public synchronized void doCheckout(OrdineBean ordine, Carrello carrello) throws SQLException, RuntimeException {
        String insertOrdineSQL = "INSERT INTO Ordine (Data_Acquisto, Totale, Utente_ID, Indirizzo_ID, MetodoPagamento_ID) VALUES (?, ?, ?, ?, ?)";
        String insertDettaglioSQL = "INSERT INTO Dettaglio_Ordine (Ordine_ID, Codice_Set, Quantita, Prezzo_Acquisto, IVA) VALUES (?, ?, ?, ?, ?)";
        String selectStockSQL = "SELECT Quantita_Magazzino FROM Set_Lego WHERE Codice_Set = ? FOR UPDATE";
        String updateStockSQL = "UPDATE Set_Lego SET Quantita_Magazzino = ? WHERE Codice_Set = ?";

        Connection con = null;
        try {
            con = ds.getConnection();
            con.setAutoCommit(false); // transazione manuale

            // 1. inserisco l'ordine e recupero l'ID
            int idOrdineGenerato = -1;
            try (PreparedStatement psOrdine = con.prepareStatement(insertOrdineSQL, Statement.RETURN_GENERATED_KEYS)) {
                psOrdine.setDate(1, ordine.getDataOrdine());
                psOrdine.setDouble(2, carrello.getPrezzoTotaleComplessivo());
                psOrdine.setInt(3, ordine.getUtenteId());
                psOrdine.setObject(4, ordine.getIndirizzoId());
                psOrdine.setObject(5, ordine.getMetodoPagamentoId());
                psOrdine.executeUpdate();

                try (ResultSet rsKeys = psOrdine.getGeneratedKeys()) {
                    if (rsKeys.next()) {
                        idOrdineGenerato = rsKeys.getInt(1);
                    }
                }
            }

            if (idOrdineGenerato == -1) {
                throw new SQLException("Errore nel recupero dell'ID dell'ordine.");
            }

            // ciclo sugli elementi del carrello, per controllo dettagli
            for (Carrello.ElementoCarrello elem : carrello.getElementi()) {
                int codiceSet = elem.getProdotto().getCodiceSet();
                int qtaRichiesta = elem.getQuantita();

                try (PreparedStatement psSelectStock = con.prepareStatement(selectStockSQL)) {
                    psSelectStock.setInt(1, codiceSet);
                    try (ResultSet rsStock = psSelectStock.executeQuery()) {
                        if (rsStock.next()) {
                            int stockAttuale = rsStock.getInt("Quantita_Magazzino");
                            if (stockAttuale < qtaRichiesta) {
                                throw new RuntimeException("Quantità insufficiente in magazzino per il set: " + elem.getProdotto().getNome());
                            }
                            
                            // aggiorno lo stock sul DB
                            try (PreparedStatement psUpdateStock = con.prepareStatement(updateStockSQL)) {
                                psUpdateStock.setInt(1, stockAttuale - qtaRichiesta);
                                psUpdateStock.setInt(2, codiceSet);
                                psUpdateStock.executeUpdate();
                            }
                        }
                    }
                }

                // inserisco nel dettaglio dell'ordine
                try (PreparedStatement psDettaglio = con.prepareStatement(insertDettaglioSQL)) {
                    psDettaglio.setInt(1, idOrdineGenerato);
                    psDettaglio.setInt(2, codiceSet);
                    psDettaglio.setInt(3, qtaRichiesta);
                    psDettaglio.setDouble(4, elem.getProdotto().getPrezzo());
                    psDettaglio.setDouble(5, elem.getProdotto().getIva());    
                    psDettaglio.executeUpdate();
                }
            }

            // ora salvo sul DB
            con.commit();

        } catch (SQLException | RuntimeException e) {
            if (con != null) {
                con.rollback(); // in caso di imprevisto, annullo tutto
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public List<OrdineBean> doRetrieveAll(String orderBy) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        
        
        String query = "SELECT * FROM Ordine";
        

        if (orderBy != null && !orderBy.trim().isEmpty()) {
            // regex base per evitare SQL injection
            if (orderBy.matches("^[a-zA-Z0-9_ ]+$")) {
                query += " ORDER BY " + orderBy;
            }
        } else {
            // ordinamento di default
            query += " ORDER BY Data_Acquisto DESC";
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ordini.add(mapResultSetToOrdineBean(rs));
            }
        }
        
        return ordini;
    }
    
    public List<OrdineBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM Ordine WHERE Utente_ID = ? ORDER BY Data_Acquisto DESC";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ordini.add(mapResultSetToOrdineBean(rs));
                }
            }
        }
        return ordini;
    }


    public List<OrdineBean> doRetrieveWithFiltersAdmin(String dataInizio, String dataFine, Integer idUtente) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Ordine WHERE 1=1");
        
        if (dataInizio != null && !dataInizio.isEmpty()) query.append(" AND Data_Acquisto >= ?");
        if (dataFine != null && !dataFine.isEmpty()) query.append(" AND Data_Acquisto <= ?");
        if (idUtente != null) query.append(" AND Utente_ID = ?");
        
        query.append(" ORDER BY Data_Acquisto DESC");

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query.toString())) {
            
            int paramIndex = 1;
            if (dataInizio != null && !dataInizio.isEmpty()) ps.setDate(paramIndex++, java.sql.Date.valueOf(dataInizio));
            if (dataFine != null && !dataFine.isEmpty()) ps.setDate(paramIndex++, java.sql.Date.valueOf(dataFine));
            if (idUtente != null) ps.setInt(paramIndex++, idUtente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ordini.add(mapResultSetToOrdineBean(rs));
                }
            }
        }
        return ordini;
    }

    private OrdineBean mapResultSetToOrdineBean(ResultSet rs) throws SQLException {
        OrdineBean bean = new OrdineBean();
        bean.setId(rs.getInt("ID"));
        bean.setDataOrdine(rs.getDate("Data_Acquisto"));
        bean.setTotale(rs.getDouble("Totale"));
        bean.setUtenteId(rs.getInt("Utente_ID"));
        bean.setIndirizzoId((Integer) rs.getObject("Indirizzo_ID"));
        bean.setMetodoPagamentoId((Integer) rs.getObject("MetodoPagamento_ID"));
        return bean;
    }
}
