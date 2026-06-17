package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.WishlistDAO;
import model.SetLego.SetLegoBean;
import model.SetLego.SetLegoDAO;

@WebServlet("/catalogoServlet")
public class CatalogoServlet extends HttpServlet {
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
            List<Integer> wishlistIds = new ArrayList<>();
            HttpSession session = request.getSession(false);

            // se l'utente è loggato, inizio estrazione dei set preferiti
            if (session != null && session.getAttribute("utente") != null) {
                model.Utente.UtenteBean utente = (model.Utente.UtenteBean) session.getAttribute("utente");
                try {
                    WishlistDAO wDao = new WishlistDAO(ds);
                    // prendo la lista dei set preferiti
                    List<model.SetLego.SetLegoBean> preferiti = wDao.doRetrieveByUtente(utente.getIdUtente());
                    
                    // estraggo solo i codici
                    for (model.SetLego.SetLegoBean pref : preferiti) {
                        wishlistIds.add(pref.getCodiceSet());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // estraggo tutti i set lego
            Collection<SetLegoBean> listaSet = setDAO.doRetrieveAll("nome ASC");

            // li inserisco nella request per passarli alla JSP
            request.setAttribute("listaSet", listaSet);
            request.setAttribute("wishlistIds", wishlistIds);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/catalogo.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) { //se DB crasha
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
