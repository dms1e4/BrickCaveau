package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {

	private static final String url = "jdbc:mysql://127.0.0.1:3306/BrickCaveau";
	private static final String user = "brick_user";
	private static final String password = "TSW_Brick2026!";
	
	private static List<Connection> freeConnections = new LinkedList<>();
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (Exception e) {
		System.err.println("Driver non trovato. " + e.getMessage());
	}
	
	private static synchronized Connection createConnection() throws SQLException {
		Connection newConnection = DriverManager.getConnection(url, user, password);
		return newConnection;
	} // metodo per creare nuova connessione
	
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;
		
		if (!freeConnections.isEmpty()) {
			connection = freeConnections.get(0);
			freeConnections.remove(0);
			
			try {
				if (connection.isClosed()) {
					connection = getConnection();
				}
			} catch (SQLException e) {
				connection = getConnection();
			}
		}
		else {
			connection = createConnection();
		}
		return connection;
	} // metodo usato da DAO per ottenere connessione
	
	
	public static synchronized void releaseConnection (Connection connection) throws SQLException{
		if (connection != null && !connection.isClosed()) {
			freeConnections.add(connection);
		}
	} // chiamato da DAO nel blocco finally per liberare la connessione
}
