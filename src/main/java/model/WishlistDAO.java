package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.SetLego.SetLegoBean;
import model.SetLego.SetLegoDAO;

// senza bean, poichè è relazione molti-a-molti

public class WishlistDAO {
    private DataSource ds;

    public WishlistDAO(DataSource ds) { this.ds = ds; }

    public boolean isPresent(int idUtente, int codiceSet) throws SQLException {
        String query = "SELECT 1 FROM Wishlist WHERE Utente_ID = ? AND Codice_Set = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, codiceSet);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void doSave(int idUtente, int codiceSet) throws SQLException {
        String query = "INSERT INTO Wishlist (Utente_ID, Codice_Set) VALUES (?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, codiceSet);
            ps.executeUpdate();
        }
    }

    public void doDelete(int idUtente, int codiceSet) throws SQLException {
        String query = "DELETE FROM Wishlist WHERE Utente_ID = ? AND Codice_Set = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            ps.setInt(2, codiceSet);
            ps.executeUpdate();
        }
    }

    public List<SetLegoBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<SetLegoBean> preferiti = new ArrayList<>();
        String query = "SELECT s.* FROM Set_Lego s JOIN Wishlist w ON s.Codice_Set = w.Codice_Set WHERE w.Utente_ID = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                SetLegoDAO setDao = new SetLegoDAO(ds);
                while (rs.next()) {
                    preferiti.add(setDao.mapResultSetToBean(rs)); 
                }
            }
        }
        return preferiti;
    }
}
