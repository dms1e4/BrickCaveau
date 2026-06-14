package control;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        String prefisso = request.getParameter("prefisso");
        String numero = request.getParameter("telefono");
        String telefonoCompleto = "";

        if(prefisso != null && numero != null) {
            telefonoCompleto = prefisso + numero;
        }

        // controllo base lato server (trim)
        if (nome == null || cognome == null || email == null || password == null || numero == null ||
            nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty() || numero.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/registrazione.jsp?error=campivuoti");
            return;
        }

        try {
            UtenteDAO utenteDAO = new UtenteDAO(ds);
            
            // mail già presa?
            if (utenteDAO.doRetrieveByEmail(email) != null) {
                response.sendRedirect(request.getContextPath() + "/registrazione.jsp?error=emailduplicata");
                return;
            }

            // associo i dati al bean
            UtenteBean nuovoUtente = new UtenteBean();
            nuovoUtente.setNome(nome);
            nuovoUtente.setCognome(cognome);
            nuovoUtente.setEmail(email);
            nuovoUtente.setPassword(hashPassword(password));
            nuovoUtente.setTelefono(telefonoCompleto);
            nuovoUtente.set_Admin(false);

            // salvo nel DB
            utenteDAO.doSave(nuovoUtente);
            UtenteBean utenteCompleto = utenteDAO.doRetrieveByEmail(nuovoUtente.getEmail());
            HttpSession session = request.getSession(true);
            session.setAttribute("utente", utenteCompleto);


            response.sendRedirect(request.getContextPath() + "/login.jsp?status=registrato");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
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
