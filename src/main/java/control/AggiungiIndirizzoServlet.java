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

import model.Indirizzo.IndirizzoBean;
import model.Indirizzo.IndirizzoDAO;
import model.Utente.UtenteBean;

@WebServlet("/AggiungiIndirizzoServlet")
public class AggiungiIndirizzoServlet extends HttpServlet {
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
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        String via = request.getParameter("via");
        String civico = request.getParameter("civico");
        String citta = request.getParameter("citta");
        String cap = request.getParameter("cap");
        String provincia = request.getParameter("provincia");
        String nazione = request.getParameter("nazione");

        IndirizzoBean indirizzo = new IndirizzoBean();
        indirizzo.setVia(via);
        indirizzo.setnCivico(civico);
        indirizzo.setCitta(citta);
        indirizzo.setCap(cap);
        indirizzo.setProvincia(provincia);
        indirizzo.setNazione(nazione);
        
        indirizzo.setUtenteId(utente.getIdUtente()); 

        // salvo nel DB
        try {
            IndirizzoDAO dao = new IndirizzoDAO(ds);
            dao.doSave(indirizzo);
            response.sendRedirect(request.getContextPath() + "/ProfiloServlet?success=indirizzo_aggiunto");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}