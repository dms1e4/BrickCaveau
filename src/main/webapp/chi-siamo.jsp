<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Chi Siamo - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <jsp:include page="/fragments/header.jsp" />

    <main>
        <div class="about-container">
            
            <div class="about-header">
                <h1>Chi siamo</h1>
                <p>La storia, la passione e i mattoncini dietro al progetto BrickCaveau.</p>
            </div>

            <div class="about-section">
                <h2>La Nostra Storia</h2>
                <p>
                    BrickCaveau nasce nel 2026 dalla passione di un gruppo di studenti universitari (e collezionisti accaniti) 
                    con un obiettivo chiaro: creare il "caveau" definitivo per tutti gli amanti dei mattoncini LEGO®. 
                    Quello che era iniziato come un semplice hobby si è trasformato in un punto di riferimento per trovare 
                    set rari, edizioni limitate e i grandi classici introvabili sul mercato tradizionale.
                </p>
            </div>

            <div class="about-section">
                <h2>La Nostra Missione</h2>
                <p>
                    Vogliamo che ogni appassionato, dal costruttore occasionale al collezionista più esigente, possa trovare 
                    il pezzo mancante per la propria collezione in totale sicurezza. Garantiamo l'originalità di ogni set e 
                    una spedizione a prova di urto (sappiamo quanto sia importante l'integrità della scatola per un vero collezionista!).
                </p>
            </div>

            <div class="about-section">
                <h2>Il Nostro Team</h2>
                <p>Dietro alle righe di codice e alla gestione del magazzino ci siamo noi::</p>
                
                <div class="team-grid">
                    <div class="team-member">
                        <h3>Simone Esposito 1</h3>
                        <p>matr. 0512121391</p>
                    </div>
                    <div class="team-member">
                        <h3>Roberto Rega</h3>
                        <p>matr. 0512121583</p>
                    </div>
                </div>
            </div>

        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>

    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>
