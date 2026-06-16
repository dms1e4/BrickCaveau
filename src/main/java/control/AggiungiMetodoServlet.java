package control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.MetodoPagamento.MetodoPagamentoBean;
import model.MetodoPagamento.MetodoPagamentoDAO;
import model.Utente.UtenteBean;

@WebServlet("/AggiungiMetodoServlet")
public class AggiungiMetodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BrickCaveau");
        } catch (NamingException e) {
            throw new ServletException("DataSource non trovato", e);
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
        
        // parametri del form
        String tipo = request.getParameter("tipo"); // carta di credito...
        String numeroCompleto = request.getParameter("numeroCarta");
        String scadenzaString = request.getParameter("scadenza"); // in formato YYYY-MM-DD
        
        // prendo solo le ultime 4 cifre
        String ultime4 = numeroCompleto.substring(numeroCompleto.length() - 4);
        Date dataScadenza = Date.valueOf(scadenzaString);
        
        // controllo se la carta è scaduta
        if (dataScadenza.before(Date.valueOf(LocalDate.now()))) {
            response.sendRedirect(request.getContextPath() + "/ProfiloServlet?error=carta_scaduta");
            return;
        }

        try {
            MetodoPagamentoDAO dao = new MetodoPagamentoDAO(ds);
            
            // controllo se la carta è già inserita
            if (dao.isDuplicato(utente.getIdUtente(), ultime4)) {
                response.sendRedirect(request.getContextPath() + "/ProfiloServlet?error=carta_duplicata");
                return;
            }
            
            MetodoPagamentoBean metodo = new MetodoPagamentoBean();
            metodo.setTipo(tipo);
            metodo.setUltime4Cifre(ultime4);
            metodo.setScadenza(Date.valueOf(scadenzaString));
            metodo.setUtenteId(utente.getIdUtente());
            
            dao.doSave(metodo);
            
            // reindirizzo al profilo con messaggio di successo
            response.sendRedirect(request.getContextPath() + "/ProfiloServlet?success=metodo_aggiunto");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}