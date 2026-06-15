package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Ordine.OrdineBean;
import model.DettaglioOrdineBean;
import model.Ordine.OrdineDAO;
import model.Utente.UtenteBean;

@WebServlet("/FatturaServlet")
public class FatturaServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        try {
            int idOrdine = Integer.parseInt(request.getParameter("id"));
            OrdineDAO ordineDAO = new OrdineDAO(ds);
            
            OrdineBean ordine = ordineDAO.doRetrieveByKey(idOrdine);
            
            // Controllo incrociato di sicurezza
            if (ordine == null || ordine.getUtenteId() != utente.getIdUtente()) {
                response.sendRedirect(request.getContextPath() + "/errori/403.jsp");
                return;
            }
            
            Collection<DettaglioOrdineBean> prodottiOrdine = ordineDAO.doRetrieveDettagliByOrdine(idOrdine);
            
            request.setAttribute("ordineFattura", ordine);
            request.setAttribute("dettagliFattura", prodottiOrdine);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/fattura.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}