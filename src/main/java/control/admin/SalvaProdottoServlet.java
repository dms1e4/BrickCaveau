package control.admin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import model.SetLego.SetLegoBean;
import model.SetLego.SetLegoDAO;

@WebServlet("/admin/SalvaProdottoServlet")
// per salvare le immagini dei set
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB per l'immagine
    maxRequestSize = 1024 * 1024 * 50     // 50MB per l'intera richiesta
)
public class SalvaProdottoServlet extends HttpServlet {
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
        
        
        // controllare che utente sia admin
        
        try {
            String azione = request.getParameter("azione");
            
            SetLegoBean set = new SetLegoBean();
            set.setCodiceSet(Integer.parseInt(request.getParameter("codiceSet")));
            set.setNome(request.getParameter("nome"));
            set.setTema(request.getParameter("tema"));
            set.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            set.setnPezzi(Integer.parseInt(request.getParameter("nPezzi")));
            set.setAnnoUscita(Integer.parseInt(request.getParameter("annoUscita")));
            set.setDescrizione(request.getParameter("descrizione"));
            
            // gestione anno di ritiro (se è stato lasciato vuoto)
            String annoRitiroStr = request.getParameter("annoRitiro");
            if (annoRitiroStr != null && !annoRitiroStr.trim().isEmpty()) {
                set.setAnnoRitiro(Integer.parseInt(annoRitiroStr));
            } else {
                set.setAnnoRitiro(0); // indica che è ancora in produzione
            }
            
            set.setQuantitaMagazzino(Integer.parseInt(request.getParameter("quantita")));

            // salvataggio nel DB
            SetLegoDAO dao = new SetLegoDAO(ds);

            if ("modifica".equals(azione)) {
                // prendo il codice per capire quale riga aggiornare
                int codiceOriginale = Integer.parseInt(request.getParameter("codiceSetOriginale"));
                dao.doUpdate(set, codiceOriginale); 
            } else {
                dao.doSave(set);
            }

            Part filePart = request.getPart("immagine");
            
            if (filePart != null && filePart.getSize() > 0) {
                // Percorso di destinazione nel server
                String uploadPath = getServletContext().getRealPath("") + File.separator + "images" + File.separator + "Set";
                File uploadDir = new File(uploadPath);
                
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                
                // Nome del file: es. "4476_1.jpg"
                String fileName = set.getCodiceSet() + "_1.jpg";
                File fileToSave = new File(uploadDir, fileName);
                
                // IL METODO INFALLIBILE: Usiamo InputStream invece di filePart.write()
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

            response.sendRedirect(request.getContextPath() + "/admin/dashboardServlet?success=operazione_completata");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errori/500.jsp");
        }
    }
}