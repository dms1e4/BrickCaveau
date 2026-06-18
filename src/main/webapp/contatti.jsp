<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contatti - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contatti.css">
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
                       <span class="orario-supporto">(Lun - Ven: 09:00 - 18:00)</span>
                    </p>
                </div>

                <div class="info-item">
                    <h3>✉️ E-mail Direzione</h3>
                    <p>
                        <a href="mailto:s.esposito176@studenti.unisa.it">s.esposito176@studenti.unisa.it</a><br>
                        <a href="mailto:r.rega24@studenti.unisa.it">r.rega24@studenti.unisa.it</a>
                    </p>
                </div>
            </div>

            <div class="contact-form-box">
                <h2>Inviaci un Messaggio</h2>
                <form id="contatti" action="#" method="POST">
                    <label for="nome">Nome o Nickname:</label>
                    <input type="text" id="nome" name="nome" placeholder="Es. Mario Rossi" required>

                    <label for="email">E-mail:</label>
                    <input type="email" id="email" name="email" placeholder="La tua e-mail" required>

                    <label for="messaggio">Cosa ti serve?</label>
                    <textarea id="messaggio" name="messaggio" rows="4" placeholder="Cerchi un pezzo raro? Scrivici qui... (max 500 caratteri)" maxlength=500 required></textarea>

                    <button type="submit">Invia Richiesta</button>
                </form>
            </div>

        </div>

        <div class="contact-header map-header">
            <h2>Vieni a trovarci</h2>
        </div>
        
        <div class="map-container">
            <iframe src="https://maps.google.com/maps?q=Dipartimento%20di%20Informatica%20Universit%C3%A0%20degli%20Studi%20di%20Salerno&t=&z=15&ie=UTF-8&iwloc=&output=embed"></iframe>
        </div>

    </main>
	<script src="js/contattoMail.js"></script>
    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>
