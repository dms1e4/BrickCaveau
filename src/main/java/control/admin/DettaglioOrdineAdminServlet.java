package control.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.DettaglioOrdineBean;
import model.Ordine.OrdineBean;
import model.Ordine.OrdineDAO;

@WebServlet("/admin/DettaglioOrdineAdminServlet")
public class DettaglioOrdineAdminServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboardServlet");
            return;
        }

        try {
            int idOrdine = Integer.parseInt(idStr);
            OrdineDAO ordineDAO = new OrdineDAO(ds);

            // recupero l'ordine
            OrdineBean ordine = ordineDAO.doRetrieveByKey(idOrdine);
            if (ordine == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ordine non trovato");
                return;
            }

            // recupero i set lego inseriti
            Collection<DettaglioOrdineBean> dettagli = ordineDAO.doRetrieveDettagliByOrdine(idOrdine);

            // recupero le info del cliente
            Map<String, String> cliente = ordineDAO.doRetrieveClienteInfoByOrdine(idOrdine);

            // passo tutto alla pagina JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);
            request.setAttribute("cliente", cliente);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/dettaglioOrdine.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException | SQLException e) {
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
