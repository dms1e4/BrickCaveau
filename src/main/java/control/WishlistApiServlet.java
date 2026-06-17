package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Utente.UtenteBean;
import model.WishlistDAO;

@WebServlet("/api/wishlist")
public class WishlistApiServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // risposta JSON per Fetch API
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null) {
            out.print("{\"success\": false, \"message\": \"Devi effettuare il login\"}");
            out.flush();
            return;
        }

        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
        
        try {
            int codiceSet = Integer.parseInt(request.getParameter("codiceSet"));
            WishlistDAO dao = new WishlistDAO(ds);
            
            // se è in wishlist lo tolgo, altrimenti lo aggiungo
            boolean isNowInWishlist;
            if (dao.isPresent(utente.getIdUtente(), codiceSet)) {
                dao.doDelete(utente.getIdUtente(), codiceSet);
                isNowInWishlist = false;
            } else {
                dao.doSave(utente.getIdUtente(), codiceSet);
                isNowInWishlist = true;
            }
            
            // JSON di risposta
            out.print("{\"success\": true, \"inWishlist\": " + isNowInWishlist + "}");
            
        } catch (NumberFormatException | SQLException e) {
            out.print("{\"success\": false, \"message\": \"Errore di sistema\"}");
        } finally {
            out.flush();
        }
    }
}
