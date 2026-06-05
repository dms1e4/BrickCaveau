package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteDAO;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/checkEmail")
public class CheckEmailServlet extends HttpServlet {
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
        
        String email = request.getParameter("email");
        
        // Impostiamo il content type corretto per la risposta JSON richiesta dal prof
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        boolean dataExists = false;

        if (email != null && !email.trim().isEmpty()) {
            try {
                UtenteDAO utenteDAO = new UtenteDAO(ds);
                // Se restituisce un utente, significa che l'email è già occupata!
                if (utenteDAO.doRetrieveByEmail(email) != null) {
                    dataExists = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Risposta JSON snella pronta per la JavaScript Fetch API del front-end
        response.getWriter().print("{\"esiste\": " + dataExists + "}");
    }
}
