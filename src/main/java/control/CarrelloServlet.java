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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Carrello;
import model.SetLego.SetLegoBean;
import model.SetLego.SetLegoDAO;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
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
        
        // recupero (o creazione, per questo è true) sessione
        HttpSession session = request.getSession(true);
        
        // recupero (o creazione) carrello
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        // recupero azione da eseguire
        // se non c'è, visualizziamo carrello
        String azione = request.getParameter("azione");
        if (azione == null || azione.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello.jsp");
            return;
        }

        try {
            // switch delle azioni
            switch (azione) {
                
                case "aggiungi":
                    int idDaAggiungere = Integer.parseInt(request.getParameter("id"));
                    // recupero quantità (se non c'è, imposto a 1)
                    String qtaParam = request.getParameter("qta");
                    int qtaDaAggiungere = (qtaParam != null && !qtaParam.isEmpty()) ? Integer.parseInt(qtaParam) : 1;
                    
                    // ora interrogo il DB
                    SetLegoDAO setDAO = new SetLegoDAO(ds);
                    SetLegoBean prodotto = setDAO.doRetrieveByKey(idDaAggiungere);
                    
                    if (prodotto != null) {
                        carrello.aggiungiProdotto(prodotto, qtaDaAggiungere);
                        // reindirizzamento (per evitare ri-invio del form se l'utente aggiorna)
                        response.sendRedirect(request.getContextPath() + "/carrello.jsp");
                    } else {
                        // se l'utente cambia URL
                        response.sendRedirect(request.getContextPath() + "/errori/404.jsp");
                    }
                    break;
                    
                case "rimuovi":
                    int idDaRimuovere = Integer.parseInt(request.getParameter("id"));
                    carrello.rimuoviProdotto(idDaRimuovere);
                    response.sendRedirect(request.getContextPath() + "/carrello.jsp");
                    break;
                    
                case "aggiorna":
                    int idDaAggiornare = Integer.parseInt(request.getParameter("id"));
                    int nuovaQta = Integer.parseInt(request.getParameter("qta"));
                    carrello.aggiornaQuantita(idDaAggiornare, nuovaQta);
                    response.sendRedirect(request.getContextPath() + "/carrello.jsp");
                    break;
                    
                case "svuota":
                    carrello.svuota();
                    response.sendRedirect(request.getContextPath() + "/carrello.jsp");
                    break;
                    
                default:
                    response.sendRedirect(request.getContextPath() + "/carrello.jsp");
            }

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