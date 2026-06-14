package control;

import javax.servlet.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import model.Utente.UtenteBean;
import model.Utente.UtenteDAO;


@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try {
            // recupero DataSource tramite JNDI
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BrickCaveau");
        } catch (NamingException e) {
            throw new ServletException("Impossibile trovare il DataSource", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // controllo base di sicurezza (con trim(
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=vuoti");
            return;
        }

        try {
            // cifratura password con funzione helper
            String passwordCifrata = hashPassword(password);
            
            UtenteDAO utenteDAO = new UtenteDAO(ds);
            UtenteBean utente = utenteDAO.doRetrieveByLogin(email, passwordCifrata);

            if (utente != null) {
                // login convalidato: creo o ottengo sessione
                HttpSession session = request.getSession(true);
                session.setAttribute("utente", utente); // salvo l'utente in sessione
                
                // reindirizzamento admin/utente
                if (utente.is_Admin()) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboardServlet");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            } else {
                // credenziali errate
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalid");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/500.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }

    // funzione per cifrare la password in SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore nell'algoritmo di hashing", e);
        }
    }
}
