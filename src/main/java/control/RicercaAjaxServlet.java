package control;

import java.io.IOException;
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

import model.SetLego.SetLegoBean;
import model.SetLego.SetLegoDAO;

@WebServlet("/ricercaAjax")
public class RicercaAjaxServlet extends HttpServlet {
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
        
        String q = request.getParameter("q");
        
        // Impostiamo l'header per dire al browser che gli stiamo mandando un JSON!
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Se la barra è vuota, restituiamo un array JSON vuoto
        if (q == null || q.trim().isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        try {
            SetLegoDAO setDAO = new SetLegoDAO(ds);
            Collection<SetLegoBean> risultati = setDAO.doRetrieveByName(q);

            // Costruiamo la stringa JSON manualmente (es: [{"id": 1, "nome": "Harry Potter..."}, {...}])
            StringBuilder json = new StringBuilder("[");
            int count = 0;
            for (SetLegoBean set : risultati) {
                // Escape degli apici nel nome per non rompere il JSON
                String nomePulito = set.getNome().replace("\"", "\\\""); 
                
                json.append("{")
                    .append("\"id\":").append(set.getCodiceSet()).append(",")
                    .append("\"nome\":\"").append(nomePulito).append("\"")
                    .append("}");
                
                count++;
                if (count < risultati.size()) {
                    json.append(","); // Virgola tra un oggetto e l'altro
                }
            }
            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Errore di connessione\"}");
        }
    }
}
