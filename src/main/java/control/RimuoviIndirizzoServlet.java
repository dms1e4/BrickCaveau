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

import model.Indirizzo.IndirizzoDAO;
import model.Utente.UtenteBean;

@WebServlet("/RimuoviIndirizzoServlet")

public class RimuoviIndirizzoServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private DataSource ds;

	    @Override
	    public void init() throws ServletException {
	        try {
	            InitialContext ctx = new InitialContext();
	            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/BrickCaveau");
	        } catch (NamingException e) {
	            throw new ServletException("DataSource non trovato", e);
	        }
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	            throws ServletException, IOException {
	        
	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("utente") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return;
	        }

	        UtenteBean utente = (UtenteBean) session.getAttribute("utente");
	        
	        try {
	            int idIndirizzo = Integer.parseInt(request.getParameter("idIndirizzo"));
	            
	            IndirizzoDAO dao = new IndirizzoDAO(ds);
	            dao.doDelete(idIndirizzo, utente.getIdUtente());
	            
	            response.sendRedirect(request.getContextPath() + "/ProfiloServlet?success=indirizzo_rimosso");
	            
	        } catch (SQLException | NumberFormatException e) {
	            e.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
	        }
	    }
	}
