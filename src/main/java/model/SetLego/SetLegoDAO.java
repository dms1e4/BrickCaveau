package model.SetLego;

import java.sql.*;


import java.util.*;
import javax.sql.DataSource;

import model.DAOInterface;

public class SetLegoDAO implements DAOInterface<SetLegoBean, Integer> {

  private DataSource ds = null;

  public SetLegoDAO(DataSource ds) {
    this.ds = ds;
  }

  @Override
  public SetLegoBean doRetrieveByKey(Integer codiceSet) throws SQLException {
      String query = "SELECT * FROM Set_Lego WHERE Codice_Set = ?";
        
      try (Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement(query)) {
            
          ps.setInt(1, codiceSet);
          try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
              return mapResultSetToBean(rs);
            }
          }
      }
      return null;
  }

  @Override
  public Collection<SetLegoBean> doRetrieveAll(String order) throws SQLException {
    List<SetLegoBean> list = new ArrayList<>();
    String query = "SELECT * FROM Set_Lego";
        
    // controllo sull'input
    if (order != null && !order.trim().isEmpty() && order.matches("[a-zA-Z_]+")) {
      query += " ORDER BY " + order;
    }

    try (Connection con = ds.getConnection();
    PreparedStatement ps = con.prepareStatement(query);
    ResultSet rs = ps.executeQuery()) {
            
      while (rs.next()) {
        list.add(mapResultSetToBean(rs));
      }
    }
      return list;
  }

  @Override
  public void doSave(SetLegoBean bean) throws SQLException {
    String query = "INSERT INTO Set_Lego (Nome, Anno_Uscita, Anno_Ritiro, N_Pezzi, Descrizione, Prezzo, IVA, Tema, Quantita_Magazzino) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
    try (Connection con = ds.getConnection();
    PreparedStatement ps = con.prepareStatement(query)) {
            
      ps.setString(1, bean.getNome());
      ps.setInt(2, bean.getAnnoUscita());
      ps.setObject(3, bean.getAnnoRitiro());
      ps.setInt(4, bean.getnPezzi());
      ps.setString(5, bean.getDescrizione());
      ps.setDouble(6, bean.getPrezzo());
      ps.setDouble(7, bean.getIva());
      ps.setString(8, bean.getTema());
      ps.setInt(9, bean.getQuantitaMagazzino());
            
      ps.executeUpdate();
    }
  }

    @Override
    public boolean doDelete(Integer codiceSet) throws SQLException {
        String query = "DELETE FROM Set_Lego WHERE Codice_Set = ?";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setInt(1, codiceSet);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

  
    public List<SetLegoBean> doRetrieveByNameAjax(String name) throws SQLException {
        List<SetLegoBean> list = new ArrayList<>();
        String query = "SELECT * FROM Set_Lego WHERE LOWER(Nome) LIKE LOWER(?) LIMIT 5";
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBean(rs));
                }
            }
        }
        return list;
    }


    public void doUpdateQuantities(Map<Integer, Integer> carrello) throws SQLException, RuntimeException {
        String selectQuery = "SELECT Quantita_Magazzino FROM Set_Lego WHERE Codice_Set = ? FOR UPDATE";
        String updateQuery = "UPDATE Set_Lego SET Quantita_Magazzino = ? WHERE Codice_Set = ?";
        
        // gestione manuale della transazione
        Connection con = null;
        try {
            con = ds.getConnection();
            con.setAutoCommit(false); 
            
            for (Map.Entry<Integer, Integer> item : carrello.entrySet()) {
                int codiceSet = item.getKey();
                int qtaRichiesta = item.getValue();
                
                // controllo disponibilità del set
                try (PreparedStatement psSelect = con.prepareStatement(selectQuery)) {
                    psSelect.setInt(1, codiceSet);
                    try (ResultSet rs = psSelect.executeQuery()) {
                        if (rs.next()) {
                            int qtaDisponibile = rs.getInt("Quantita_Magazzino");
                            if (qtaDisponibile < qtaRichiesta) {
                                throw new RuntimeException("Errore: Quantità insufficiente per il set ID " + codiceSet);
                            }
                            
                            // aggiorno con nuovo stock
                            try (PreparedStatement psUpdate = con.prepareStatement(updateQuery)) {
                                psUpdate.setInt(1, qtaDisponibile - qtaRichiesta);
                                psUpdate.setInt(2, codiceSet);
                                psUpdate.executeUpdate();
                            }
                        } else {
                            throw new RuntimeException("Errore: Set Lego non trovato per ID " + codiceSet);
                        }
                    }
                }
            }

            con.commit();
            
        } catch (SQLException | RuntimeException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }


    public SetLegoBean mapResultSetToBean(ResultSet rs) throws SQLException {
        SetLegoBean bean = new SetLegoBean();
        bean.setCodiceSet(rs.getInt("Codice_Set"));
        bean.setNome(rs.getString("Nome"));
        bean.setAnnoUscita(rs.getInt("Anno_Uscita"));
        bean.setAnnoRitiro((Integer) rs.getObject("Anno_Ritiro"));
        bean.setnPezzi(rs.getInt("N_Pezzi"));
        bean.setDescrizione(rs.getString("Descrizione"));
        bean.setPrezzo(rs.getDouble("Prezzo"));
        bean.setIva(rs.getDouble("IVA"));
        bean.setTema(rs.getString("Tema"));
        bean.setQuantitaMagazzino(rs.getInt("Quantita_Magazzino"));
        return bean;
    }

 // ricerca AJAX
    public Collection<SetLegoBean> doRetrieveByName(String namePrefix) throws SQLException {
        List<SetLegoBean> list = new ArrayList<>();
        
        // controllo per nome del set / tema del set
        String query = "SELECT * FROM Set_Lego WHERE Nome LIKE ? OR Tema LIKE ? LIMIT 5"; 
        
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            String parametroRicerca = "%" + namePrefix + "%";
            
            ps.setString(1, parametroRicerca);
            ps.setString(2, parametroRicerca);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBean(rs));
                }
            }
        }
        return list;
    }
    
    public void doUpdate(SetLegoBean set, int codiceOriginale) throws SQLException {
    	String query = "UPDATE Set_Lego SET Codice_Set = ?, Nome = ?, Descrizione = ?, Tema = ?, Prezzo = ?, N_Pezzi = ?, Anno_Uscita = ?, Anno_Ritiro = ?, QuantitaMagazzino = ? WHERE Codice_Set = ?";

    	try (Connection con = ds.getConnection();
    	     PreparedStatement ps = con.prepareStatement(query)) {
    	    
    	    ps.setInt(1, set.getCodiceSet());
    	    ps.setString(2, set.getNome());
    	    ps.setString(3, set.getDescrizione());
    	    ps.setString(4, set.getTema());
    	    ps.setDouble(5, set.getPrezzo());
    	    ps.setInt(6, set.getnPezzi());
    	    ps.setInt(7, set.getAnnoUscita());
    	    
    	    if (set.getAnnoRitiro() == 0) {
    	        ps.setNull(8, java.sql.Types.INTEGER);
    	    } else {
    	        ps.setInt(8, set.getAnnoRitiro());
    	    }
    	    
    	    ps.setInt(9, set.getQuantitaMagazzino());
    	    ps.setInt(10, codiceOriginale);
    	    
    	    ps.executeUpdate();
    	}
    }

// set lego da esporre in home

   public List<SetLegoBean> doRetrieveRandom(int limit) throws SQLException {
    List<SetLegoBean> setCasuali = new ArrayList<>();
    String query = "SELECT * FROM Set_Lego ORDER BY RAND() LIMIT ?";
    
    try (Connection con = ds.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
         
        ps.setInt(1, limit);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                setCasuali.add(mapResultSetToBean(rs)); 
            }
        }
    }
    	return setCasuali;
    }

   @Override
   public void doUpdate(SetLegoBean item) throws SQLException {
	// TODO Auto-generated method stub
	
   }
}
