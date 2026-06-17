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
import javax.sql.DataSource;

import model.SetLego.SetLegoDAO;

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
        
        // aggiungere verifica che utente sia veramente admin
        
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