package model.Indirizzo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class IndirizzoDAO {
    private DataSource ds;

    public IndirizzoDAO(DataSource ds) { this.ds = ds; }

    // salva indirizzo
    public void doSave(IndirizzoBean indirizzo) throws SQLException {
        String query = "INSERT INTO Indirizzo (N_Civico, Via, Citta, CAP, Provincia, Nazione, Utente_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, indirizzo.getnCivico());
            ps.setString(2, indirizzo.getVia());
            ps.setString(3, indirizzo.getCitta());
            ps.setString(4, indirizzo.getCap());
            ps.setString(5, indirizzo.getProvincia());
            ps.setString(6, indirizzo.getNazione());
            ps.setInt(7, indirizzo.getUtenteId());
            ps.executeUpdate();
        }
    }

    // recupera indirizzi dell'utente
    public List<IndirizzoBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<IndirizzoBean> indirizzi = new ArrayList<>();
        String query = "SELECT * FROM Indirizzo WHERE Utente_ID = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    IndirizzoBean bean = new IndirizzoBean();
                    bean.setId(rs.getInt("ID"));
                    bean.setnCivico(rs.getString("N_Civico"));
                    bean.setVia(rs.getString("Via"));
                    bean.setCitta(rs.getString("Citta"));
                    bean.setCap(rs.getString("CAP"));
                    bean.setProvincia(rs.getString("Provincia"));
                    bean.setNazione(rs.getString("Nazione"));
                    bean.setUtenteId(rs.getInt("Utente_ID"));
                    indirizzi.add(bean);
                }
            }
        }
        return indirizzi;
    }

    // elimina indirizzo salvato
    public void doDelete(int idIndirizzo, int idUtente) throws SQLException {
        String query = "DELETE FROM Indirizzo WHERE ID = ? AND Utente_ID = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idIndirizzo);
            ps.setInt(2, idUtente);
            ps.executeUpdate();
        }
    }
}