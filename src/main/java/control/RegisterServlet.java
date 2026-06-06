package control;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utente.UtenteBean;
import model.Utente.UtenteDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@javax.servlet.annotation.WebServlet("/registerServlet")
public class RegisterServlet extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BrickCaveau");
        } catch (NamingException e) {
            throw new ServletException("Impossibile trovare il DataSource", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telefono = request.getParameter("telefono");

        // Controllo lato server (Integrità dati)
        if (nome == null || cognome == null || email == null || password == null || telefono == null ||
            nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || telefono.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=campivuoti");
            return;
        }

        try {
            UtenteDAO utenteDAO = new UtenteDAO(ds);
            
            // Ultimo controllo di sicurezza: l'email esiste già?
            if (utenteDAO.doRetrieveByEmail(email) != null) {
                response.sendRedirect(request.getContextPath() + "/register.jsp?error=emailduplicata");
                return;
            }

            // Popoliamo il Bean
            UtenteBean nuovoUtente = new UtenteBean();
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome);
            nuovoUtente.setEmail(email);
            nuovoUtente.setPassword(hashPassword(password)); // Cifratura di sicurezza!
            nuovoUtente.setTelefono(telefono);
            nuovoUtente.set_Admin(false); // Di default ci si registra come normali clienti

            // Salvataggio nel DB
            utenteDAO.doSave(nuovoUtente);

            // Successo: mandiamo l'utente alla pagina di login con un messaggio positivo
            response.sendRedirect(request.getContextPath() + "/login.jsp?status=registrato");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/register.jsp?error=dberror");
        }
    }

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
            throw new RuntimeException(e);
        }
    }
}
