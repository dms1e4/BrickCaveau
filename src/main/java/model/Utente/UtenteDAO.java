package model.Utente;

import java.sql.*;

import java.util.*;
import javax.sql.DataSource;

import model.DAOInterface;

public class UtenteDAO implements DAOInterface<UtenteBean, Integer> {

    private DataSource ds = null;

    public UtenteDAO(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public UtenteBean doRetrieveByKey(Integer idUtente) throws SQLException {
        String query = "SELECT * FROM Utente WHERE ID_Utente = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBean(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {
        List<UtenteBean> utenti = new ArrayList<>();
        String query = "SELECT * FROM Utente";

        if (order != null && !order.trim().isEmpty() && order.matches("[a-zA-Z_]+")) {
            query += " ORDER BY " + order;
        }

        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                utenti.add(mapResultSetToBean(rs));
            }
        }
        return utenti;
    }

    @Override
    public void doSave(UtenteBean utente) throws SQLException {
        String query = "INSERT INTO Utente (ID_Utente, Nome, Cognome, Email, Password, Telefono, is_Admin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
        	ps.setInt(1, utente.getIdUtente());
            ps.setString(2, utente.getNome());
            ps.setString(3, utente.getCognome());
            ps.setString(4, utente.getEmail());
            ps.setString(5, utente.getPassword()); // Stringa hash cifrata
            ps.setString(6, utente.getTelefono());
            ps.setBoolean(7, utente.is_Admin());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void doUpdate(UtenteBean utente) throws SQLException {
        String query = "UPDATE Utente SET Nome = ?, Cognome = ?, Email = ?, Password = ?, Telefono = ?, is_Admin = ? WHERE ID_Utente = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setString(3, utente.getEmail());
            ps.setString(4, utente.getPassword());
            ps.setString(5, utente.getTelefono());
            ps.setBoolean(6, utente.is_Admin());
            ps.setInt(7, utente.getIdUtente());
            
            ps.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Integer idUtente) throws SQLException {
        String query = "DELETE FROM Utente WHERE ID_Utente = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, idUtente);
            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;
        }
    }


    public UtenteBean doRetrieveByLogin(String email, String passwordHash) throws SQLException {
        String query = "SELECT * FROM Utente WHERE Email = ? AND Password = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBean(rs);
                }
            }
        }
        return null;
    }


    public UtenteBean doRetrieveByEmail(String email) throws SQLException {
        String query = "SELECT * FROM Utente WHERE Email = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBean(rs);
                }
            }
        }
        return null;
    }

    private UtenteBean mapResultSetToBean(ResultSet rs) throws SQLException {
        UtenteBean bean = new UtenteBean();
        bean.setIdUtente(rs.getInt("ID_Utente"));
        bean.setNome(rs.getString("Nome"));
        bean.setCognome(rs.getString("Cognome"));
        bean.setEmail(rs.getString("Email"));
        bean.setPassword(rs.getString("Password"));
        bean.setTelefono(rs.getString("Telefono"));
        bean.set_Admin(rs.getBoolean("is_Admin"));
        return bean;
    }
}
