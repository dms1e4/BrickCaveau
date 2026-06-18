package control.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Ordine.OrdineBean;
import model.Ordine.OrdineDAO;

@WebServlet("/admin/api/ordini")
public class OrdiniApiServlet extends HttpServlet {
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
        
        // Imposto che la risposta sarà un JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String idUtenteStr = request.getParameter("utenteId");

        Integer utenteId = null;
        if (idUtenteStr != null && !idUtenteStr.trim().isEmpty()) {
            try { utenteId = Integer.parseInt(idUtenteStr); } 
            catch (NumberFormatException e) { utenteId = null; }
        }

        try {
            OrdineDAO ordineDAO = new OrdineDAO(ds);
            Collection<OrdineBean> listaOrdini = ordineDAO.doRetrieveWithFiltersAdmin(dataInizio, dataFine, utenteId);

            // Costruisco la stringa JSON manualmente (come hai fatto in RicercaAjax)
            StringBuilder json = new StringBuilder("[");
            int count = 0;
            for (OrdineBean o : listaOrdini) {
                json.append("{")
                    .append("\"id\":").append(o.getId()).append(",")
                    .append("\"dataOrdine\":\"").append(o.getDataOrdine()).append("\",")
                    .append("\"utenteId\":").append(o.getUtenteId()).append(",")
                    .append("\"totale\":").append(o.getTotale())
                    .append("}");
                
                count++;
                if (count < listaOrdini.size()) {
                    json.append(",");
                }
            }
            json.append("]");

            out.print(json.toString());

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Errore di connessione al database\"}");
        } finally {
            out.flush();
        }
    }
}
