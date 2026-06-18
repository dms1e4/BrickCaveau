package control.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente.UtenteBean;


@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        boolean isAuthorized = false;

        // controllo se utente è loggato
        if (session != null && session.getAttribute("utente") != null) {
            UtenteBean utente = (UtenteBean) session.getAttribute("utente");
            
            // controllo se l'utente loggato è admin
            if (utente.is_Admin()) {
                isAuthorized = true;
            }
        }

        if (isAuthorized) {
        	// non salva in cache le pagine admin
        	httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // per http 1.1
            httpResponse.setHeader("Pragma", "no-cache"); // per http 1.0
            httpResponse.setDateHeader("Expires", 0);
            
            chain.doFilter(request, response);
        } else {
           // permesso negato
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/errori/403.jsp");
        }
    }

    public void destroy() {
        
    }
}