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
import javax.sql.DataSource;

import model.Utente.UtenteDAO;



@WebServlet("/checkEmailServlet")
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
        
        // imposto content-type corretto per json
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        boolean dataExists = false;

        if (email != null && !email.trim().isEmpty()) {
            try {
                UtenteDAO utenteDAO = new UtenteDAO(ds);
                // mail già in uso
                if (utenteDAO.doRetrieveByEmail(email) != null) {
                    dataExists = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // risposta JSON
        response.getWriter().print("{\"esiste\": " + dataExists + "}");
    }
}
