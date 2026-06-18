package control.admin;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/admin/ModificaProdottoServlet")
public class ModificaProdottoServlet extends HttpServlet {
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

        try {
            // leggo ID passato dal bottone modifica della dashboard
            int id = Integer.parseInt(request.getParameter("id"));
            
            // uso dao per recuperare le info da passare alla pagina
            SetLegoDAO dao = new SetLegoDAO(ds);
            SetLegoBean setDaModificare = dao.doRetrieveByKey(id);
            
            if (setDaModificare != null) {
                request.setAttribute("setLego", setDaModificare);
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/formProdotto.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/AdminDashboardServlet?error=set_non_trovato");
            }
            
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}