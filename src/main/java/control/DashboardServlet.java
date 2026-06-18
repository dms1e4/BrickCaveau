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
import javax.sql.DataSource;


import model.SetLego.SetLegoBean;
import model.SetLego.SetLegoDAO;
import model.Ordine.OrdineBean;
import model.Ordine.OrdineDAO;

@WebServlet("/admin/dashboardServlet") //filter per admin
public class DashboardServlet extends HttpServlet {
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
        
        try {
            SetLegoDAO setDAO = new SetLegoDAO(ds);
            OrdineDAO ordineDAO = new OrdineDAO(ds);

            // 1. Catalogo completo (non filtrato qui)
            Collection<SetLegoBean> listaProdotti = setDAO.doRetrieveAll("Codice_Set ASC");
            
            // 2. Catturo i parametri di filtro dal form della Dashboard
            String dataInizio = request.getParameter("dataInizio");
            String dataFine = request.getParameter("dataFine");
            String idUtenteStr = request.getParameter("utenteId");
            
            Integer utenteId = null;
            if (idUtenteStr != null && !idUtenteStr.trim().isEmpty()) {
                try {
                    utenteId = Integer.parseInt(idUtenteStr);
                } catch (NumberFormatException e) {
                    utenteId = null; // ignoro se l'admin ha inserito lettere per sbaglio
                }
            }

            // 3. Uso il metodo avanzato del tuo OrdineDAO al posto di doRetrieveAll
            Collection<OrdineBean> listaOrdini = ordineDAO.doRetrieveWithFiltersAdmin(dataInizio, dataFine, utenteId);

            // 4. Salvo tutto nella request
            request.setAttribute("listaProdottiAdmin", listaProdotti);
            request.setAttribute("listaOrdiniAdmin", listaOrdini);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/dashboard.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // se arriva una POST la reindirizziamo su GET
        doGet(request, response);
    }
}
