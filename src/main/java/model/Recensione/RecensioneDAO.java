package model.Recensione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class RecensioneDAO {
    private DataSource ds;

    public RecensioneDAO(DataSource ds) { this.ds = ds; }

    // recupera le recensioni per uno specifico set (in base al codice)
    
    public List<RecensioneBean> doRetrieveBySetLego(int codiceSet) throws SQLException {
        List<RecensioneBean> recensioni = new ArrayList<>();
        // JOIN per recuperare anche nome dell'utente
        String query = "SELECT r.*, u.Nome FROM Recensione r JOIN Utente u ON r.Utente_ID = u.ID_Utente WHERE r.Codice_Set = ? ORDER BY r.Data_Recensione DESC";

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
                    bean.setNomeUtente(rs.getString("Nome")); 
                    
                    recensioni.add(bean);
                }
            }
        }
        return recensioni;
    }

    // media dei voti del set
    public double getMediaVoti(int codiceSet) throws SQLException {
        String query = "SELECT AVG(Rating) as Media FROM Recensione WHERE Codice_Set = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, codiceSet);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Math.round(rs.getDouble("Media") * 10.0) / 10.0;
                }
            }
        }
        return 0.0;
    }

    // salva recensione
    public void doSave(RecensioneBean recensione) throws SQLException {
        // REPLACE: se utente recensisce un set già recensito, viene sostituita la vecchia recensione
        String query = "REPLACE INTO Recensione (Utente_ID, Codice_Set, Rating, Testo, Data_Recensione) VALUES (?, ?, ?, ?, CURRENT_DATE())";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, recensione.getUtenteId());
            ps.setInt(2, recensione.getCodiceSet());
            ps.setInt(3, recensione.getRating());
            ps.setString(4, recensione.getTesto());
            
            ps.executeUpdate();
        }
    }
    
    // verificare che l'utente abbia acquistato il set prima di recensirlo 
    public boolean checkAcquisto(int idUtente, int codiceSet) throws SQLException {
        String query = "SELECT 1 FROM Ordine o JOIN Dettaglio_Ordine d ON o.ID = d.Ordine_ID " +
                       "WHERE o.Utente_ID = ? AND d.Codice_Set = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            ps.setInt(2, codiceSet);
            
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}