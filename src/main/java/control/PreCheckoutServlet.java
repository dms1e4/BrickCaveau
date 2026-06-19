package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

import model.Utente.UtenteBean;
import model.Indirizzo.IndirizzoDAO;
import model.Indirizzo.IndirizzoBean;
import model.MetodoPagamento.MetodoPagamentoDAO;
import model.MetodoPagamento.MetodoPagamentoBean;

@WebServlet("/PreCheckoutServlet")
public class PreCheckoutServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // controllo base
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=devi_accedere");
            return;
        }

        // controllo del carrello
        if (session.getAttribute("carrello") == null) {
            response.sendRedirect(request.getContextPath() + "/carrello.jsp");
            return;
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");

        try {
            IndirizzoDAO indDAO = new IndirizzoDAO(ds);
            MetodoPagamentoDAO metDAO = new MetodoPagamentoDAO(ds);

            List<IndirizzoBean> indirizzi = indDAO.doRetrieveByUtente(utente.getIdUtente());
            List<MetodoPagamentoBean> metodi = metDAO.doRetrieveByUtente(utente.getIdUtente());

            // business logic (hai riempito i dati?)
            if (indirizzi.isEmpty() || metodi.isEmpty()) {
                // se manca qualcosa viene rimandato al profilo con messaggio di errore
                response.sendRedirect(request.getContextPath() + "/ProfiloServlet?info=dati_mancanti");
                return;
            }

            // se c'è tutto, mettiamo i dati nella request
            request.setAttribute("listaIndirizzi", indirizzi);
            request.setAttribute("listaMetodi", metodi);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/checkout.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
