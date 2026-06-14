package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

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
import model.Recensione.RecensioneBean;
import model.Recensione.RecensioneDAO;

@WebServlet("/ProdottoServlet")
public class ProdottoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try { // DataSource
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
            // prendo id passato nell'url
            int idSet = Integer.parseInt(request.getParameter("id"));
            
            SetLegoDAO setDAO = new SetLegoDAO(ds);
            
            SetLegoBean prodotto = setDAO.doRetrieveByKey(idSet);
            
            // recupero tutte le recensioni associate al prodotto
            RecensioneDAO recensioneDAO = new RecensioneDAO(ds);
            List<RecensioneBean> recensioni = recensioneDAO.doRetrieveBySetLego(idSet);
            
            // Metto il bean del prodotto e la lista delle recensioni nella request
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("listaRecensioni", recensioni);
            
            // Mando alla pagina JSP corretta
            RequestDispatcher dispatcher = request.getRequestDispatcher("/prodotto.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException | NumberFormatException e) { 
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