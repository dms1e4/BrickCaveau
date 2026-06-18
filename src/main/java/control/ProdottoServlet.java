package control;

import java.io.IOException;

import java.sql.SQLException;
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
            int idSet = Integer.parseInt(request.getParameter("id"));
            
            SetLegoDAO setDAO = new SetLegoDAO(ds);
            SetLegoBean prodotto = setDAO.doRetrieveByKey(idSet);
            
            RecensioneDAO recensioneDAO = new RecensioneDAO(ds);
            List<RecensioneBean> recensioni = recensioneDAO.doRetrieveBySetLego(idSet);
            // calcolo media voti
            double media = recensioneDAO.getMediaVoti(idSet);
            boolean haAcquistato = false;
            
            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("utente") != null) {
                model.Utente.UtenteBean utente = (model.Utente.UtenteBean) session.getAttribute("utente");
                // verifico che l'utente loggato abbia effettivamente acquistato il set
                haAcquistato = recensioneDAO.checkAcquisto(utente.getIdUtente(), idSet);
            }
            
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("listaRecensioni", recensioni);
            request.setAttribute("mediaVoti", media);
            request.setAttribute("numeroRecensioni", recensioni.size());
            request.setAttribute("utenteHaAcquistato", haAcquistato);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/prodotto.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException | NumberFormatException e) { 
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}