package control.admin;

import java.io.IOException;
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

import model.SetLego.SetLegoDAO;
import model.Utente.UtenteBean;

@WebServlet("/admin/EliminaProdottoServlet")
public class EliminaProdottoServlet extends HttpServlet {
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
        
    	// controllo che l'utente sia loggato e sia admin
    	
    	HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("utente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }


        UtenteBean utente = (model.Utente.UtenteBean) session.getAttribute("utente");
        
        if (!utente.is_Admin()) { 
            response.sendRedirect(request.getContextPath() + "/403.jsp");
            return;
        }
        
        try {
            int idSet = Integer.parseInt(request.getParameter("idSet"));
            SetLegoDAO dao = new SetLegoDAO(ds);
            
            dao.doDelete(idSet);
            
            response.sendRedirect(request.getContextPath() + "/admin/dashboardServlet?success=eliminato");
            
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}