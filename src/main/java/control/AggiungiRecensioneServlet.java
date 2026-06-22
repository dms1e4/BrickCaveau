package control;

import java.io.IOException;

import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Recensione.*;
import model.Utente.UtenteBean;

@WebServlet("/AggiungiRecensioneServlet")
public class AggiungiRecensioneServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BrickCaveau");
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // controllo sicurezza: solo un utente loggato può recensire
        
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        
        try {
            int codiceSet = Integer.parseInt(request.getParameter("codiceSet"));
            
            RecensioneDAO dao = new RecensioneDAO(ds);
            if (!dao.checkAcquisto(utente.getIdUtente(), codiceSet)) {
                // se un utente bypassa il controllo simulando una POST, lo rimandiamo alla pagina di errore
                response.sendRedirect(request.getContextPath() + "/ProdottoServlet?id=" + codiceSet + "&error=dati_non_validi");
                return;
            }
            
            int rating = Integer.parseInt(request.getParameter("rating"));
            String testo = request.getParameter("testo");
            
            // validazione base
            if (rating < 1 || rating > 5 || testo == null || testo.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/ProdottoServlet?id=" + codiceSet + "&error=dati_non_validi");
                return;
            }

            RecensioneBean recensione = new RecensioneBean();
            recensione.setUtenteId(utente.getIdUtente());
            recensione.setCodiceSet(codiceSet);
            recensione.setRating(rating);
            recensione.setTesto(testo);
            
            dao.doSave(recensione);
            
            // ricarico la pagina
            response.sendRedirect(request.getContextPath() + "/ProdottoServlet?id=" + codiceSet + "&success=recensione_inviata");
            
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}