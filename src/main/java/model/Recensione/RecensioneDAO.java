package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class RecensioneDAO {

    private DataSource ds = null;

    public RecensioneDAO(DataSource ds) {
        this.ds = ds;
    }

    public List<RecensioneBean> doRetrieveBySetLego(int codiceSet) throws SQLException {
        List<RecensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT r.*, u.Nome, u.Cognome FROM Recensione r " +
                       "INNER JOIN Utente u ON r.Utente_ID = u.ID_Utente " +
                       "WHERE r.Codice_Set = ? ORDER BY r.Data_Recensione DESC";

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, codiceSet);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RecensioneBean bean = new RecensioneBean();
                    bean.setUtenteId(rs.getInt("Utente_ID"));
                    bean.setCodiceSet(rs.getInt("Codice_Set"));
                    bean.setRating(rs.getInt("Rating"));
                    bean.setTesto(rs.getString("Testo"));
                    bean.setDataRecensione(rs.getDate("Data_Recensione"));
                    
                    // Dati presi dalla JOIN con la tabella Utente
                    bean.setNomeUtente(rs.getString("Nome"));
                    bean.setCognomeUtente(rs.getString("Cognome"));
                    
                    recensioni.add(bean);
                }
            }
        }
        return recensioni;
    }


    public void doSave(RecensioneBean recensione) throws SQLException {
        String query = "INSERT INTO Recensione (Utente_ID, Codice_Set, Rating, Testo, Data_Recensione) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, recensione.getUtenteId());
            ps.setInt(2, recensione.getCodiceSet());
            ps.setInt(3, recensione.getRating());
            ps.setString(4, recensione.getTesto());
            ps.setDate(5, recensione.getDataRecensione());
            
            ps.executeUpdate();
        }
    }


    public boolean doDelete(int idUtente, int codiceSet) throws SQLException {
        String query = "DELETE FROM Recensione WHERE Utente_ID = ? AND Codice_Set = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            ps.setInt(2, codiceSet);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }


    public boolean hasUserReviewedProduct(int idUtente, int codiceSet) throws SQLException {
        String query = "SELECT COUNT(*) FROM Recensione WHERE Utente_ID = ? AND Codice_Set = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            ps.setInt(2, codiceSet);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
