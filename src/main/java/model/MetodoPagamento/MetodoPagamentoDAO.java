package model.MetodoPagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class MetodoPagamentoDAO {
    private DataSource ds;

    public MetodoPagamentoDAO(DataSource ds) {
        this.ds = ds;
    }

    public void doSave(MetodoPagamentoBean metodo) throws SQLException { //salvataggio metodo di pagamento
        String query = "INSERT INTO MetodoPagamento (Scadenza, Tipo, Ultime4Cifre, Utente_ID) VALUES (?, ?, ?, ?)";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDate(1, metodo.getScadenza());
            ps.setString(2, metodo.getTipo());
            ps.setString(3, metodo.getUltime4Cifre());
            ps.setInt(4, metodo.getUtenteId());
            ps.executeUpdate();
        }
    }

    public List<MetodoPagamentoBean> doRetrieveByUtente(int idUtente) throws SQLException {
        List<MetodoPagamentoBean> metodi = new ArrayList<>();
        String query = "SELECT * FROM MetodoPagamento WHERE Utente_ID = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MetodoPagamentoBean bean = new MetodoPagamentoBean();
                    bean.setId(rs.getInt("ID"));
                    bean.setScadenza(rs.getDate("Scadenza"));
                    bean.setTipo(rs.getString("Tipo"));
                    bean.setUltime4Cifre(rs.getString("Ultime4Cifre"));
                    bean.setUtenteId(rs.getInt("Utente_ID"));
                    metodi.add(bean);
                }
            }
        }
        return metodi;
    }
    
    public boolean isDuplicato(int idUtente, String ultime4Cifre) throws SQLException {
        String query = "SELECT COUNT(*) FROM MetodoPagamento WHERE Utente_ID = ? AND Ultime4Cifre = ?";
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idUtente);
            ps.setString(2, ultime4Cifre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}