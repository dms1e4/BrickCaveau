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
import model.Ordine.OrdineDAO;
import model.Utente.UtenteBean;
import model.MetodoPagamento.*;

@WebServlet("/ProfiloServlet")
public class ProfiloServlet extends HttpServlet {
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
        
        // controllo sessione
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            // altrimenti lo mandiamo al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        try {
            OrdineDAO ordineDAO = new OrdineDAO(ds);
            MetodoPagamentoDAO metodoDAO = new MetodoPagamentoDAO(ds);
            
            // recupero ordini e metodi di pagamento dell'utente in base all'id
            Collection<OrdineBean> ordiniUtente = ordineDAO.doRetrieveByUtente(utente.getIdUtente());
            Collection<MetodoPagamentoBean> metodi = metodoDAO.doRetrieveByUtente(utente.getIdUtente());
            
            // salvo nella request
            request.setAttribute("listaOrdiniCliente", ordiniUtente);
            request.setAttribute("listaMetodi", metodi);
            
            // inoltro alla JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/profilo.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
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