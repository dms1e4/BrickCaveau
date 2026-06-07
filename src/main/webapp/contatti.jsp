<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Contatti - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/fragments/header.jsp" />

    <main class="contact-container">
        
        <div class="contact-header">
            <h1>Entra in Contatto col Caveau!</h1>
            <p>Hai domande sui nostri set o sulla tua spedizione? Il team di BrickCaveau è a tua disposizione.</p>
        </div>

        <div class="contact-grid">
            
            <div class="contact-info">
                <h2>I Nostri Recapiti</h2>
                
                <div class="info-item">
                    <h3>📍 Il nostro Quartier Generale</h3>
                    <p>Università degli Studi di Salerno<br>
                       Dipartimento di Informatica<br>
                       Via Giovanni Paolo II, 132<br>
                       84084 Fisciano (SA)
                    </p>
                </div>

                <div class="info-item">
                    <h3>📞 Supporto Telefonico</h3>
                    <p>Centralino Dipartimento: <strong>089 966104</strong><br>
                       <span style="font-size: 0.85em; color: #94A3B8;">(Lun - Ven: 09:00 - 18:00)</span>
                    </p>
                </div>

                <div class="info-item">
                    <h3>✉️ E-mail Direzione</h3>
                    <p>
                        <a href="mailto:s.esposito176@studenti.unisa.it" style="color: #1976D2;">s.esposito176@studenti.unisa.it</a>
                        <a href="mailto:r.rega24@studenti.unisa.it" style="color: #1976D2;">r.rega24@studenti.unisa.it</a><br>
                    </p>
                </div>
            </div>

            <div class="contact-form-box">
                <h2>Inviaci un Messaggio</h2>
                <form action="#" method="POST">
                    <label for="nome">Nome o Nickname:</label>
                    <input type="text" id="nome" name="nome" placeholder="Es. Emmet Brickowski" required>

                    <label for="email">E-mail:</label>
                    <input type="email" id="email" name="email" placeholder="La tua e-mail" required>

                    <label for="messaggio">Cosa ti serve?</label>
                    <textarea id="messaggio" name="messaggio" rows="4" placeholder="Cerchi un pezzo raro? Scrivici qui..." required></textarea>

                    <button type="submit">Invia Richiesta</button>
                </form>
            </div>

        </div>

        <div class="contact-header" style="margin-bottom: 20px;">
            <h2>Vieni a trovarci</h2>
        </div>
        
        <div class="map-container">
            <iframe 
                src="https://maps.google.com/maps?width=100%25&amp;height=400&amp;hl=it&amp;q=Dipartimento%20di%20Informatica,%20Universit%C3%A0%20di%20Salerno,%20Fisciano&amp;t=&amp;z=16&amp;ie=UTF8&amp;iwloc=B&amp;output=embed" 
                frameborder="0" 
                scrolling="no" 
                marginheight="0" 
                marginwidth="0">
            </iframe>
        </div>

    </main>

    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>
