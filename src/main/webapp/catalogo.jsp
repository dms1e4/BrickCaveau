<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="catalogo-layout">
        
        <aside class="sidebar-filtri">
            <h3>Filtra i Set</h3>
            
            <div class="gruppo-filtro">
                <label>Temi</label>
                <div class="checkbox-container">
                    <label class="control-checkbox"><input type="checkbox" class="filtro-tema" value="Harry Potter"> Harry Potter</label>
                    <label class="control-checkbox"><input type="checkbox" class="filtro-tema" value="Star Wars"> Star Wars</label>
                    <label class="control-checkbox"><input type="checkbox" class="filtro-tema" value="Agents"> Agents</label>
                    <label class="control-checkbox"><input type="checkbox" class="filtro-tema" value="Ghostbusters"> Ghostbusters</label>
                    <label class="control-checkbox"><input type="checkbox" class="filtro-tema" value="Indiana Jones"> Indiana Jones</label>
                    <label class="control-checkbox"><input type="checkbox" class="filtro-tema" value="The Simpsons"> The Simpsons</label>
                </div>
            </div>

            <div class="gruppo-filtro">
                <div class="slider-header">
                    <label for="sliderUscita">Anno Uscita (dal):</label>
                    <span id="valoreUscita" class="valore-dinamico">2000</span>
                </div>
                <input type="range" id="sliderUscita" min="2000" max="2026" value="2000" step="1">
            </div>

            <div class="gruppo-filtro">
                <div class="slider-header">
                    <label for="sliderRitiro">Anno Ritiro (fino al):</label>
                    <span id="valoreRitiro" class="valore-dinamico">Tutti</span>
                </div>
                <input type="range" id="sliderRitiro" min="2000" max="2026" value="2026" step="1">
            </div>

            <div class="gruppo-filtro">
                <div class="slider-header">
                    <label for="sliderPezzi">Numero Max Pezzi:</label>
                    <span id="valorePezzi" class="valore-dinamico">8000</span>
                </div>
                <input type="range" id="sliderPezzi" min="0" max="8000" value="8000" step="50">
            </div>

            <button type="button" id="btnReset" class="btn-reset-live">Azzera Filtri</button>
        </aside>

        <div class="contenuto-catalogo">
            <h2>Il Caveau: Tutti i Set Disponibili</h2>

            <div id="nessun-risultato" style="display: none;" class="messaggio-vuoto">
                <h3>Nessun set LEGO corrisponde ai filtri selezionati.</h3>
                <p>Prova a modificare i parametri di ricerca!</p>
            </div>

            <c:choose>
                <c:when test="${empty listaSet}">
                    <div class="messaggio-vuoto">
                        <h3>Il database è attualmente vuoto.</h3>
                    </div>
                </c:when>
                
                <c:otherwise>
                    <div class="grid-catalogo">
                        <c:forEach var="set" items="${listaSet}">
                            <div class="card-prodotto" 
                                 data-tema="${set.tema}" 
                                 data-anno-uscita="${set.annoUscita}" 
                                 data-anno-ritiro="${set.annoRitiro != null ? set.annoRitiro : 0}" 
                                 data-pezzi="${set.nPezzi}">
                                
                                <img src="${pageContext.request.contextPath}/images/Set/${set.codiceSet}_1.jpg" 
                                     onerror="this.src='${pageContext.request.contextPath}/images/logo.png'" 
                                     alt="Immagine ${set.nome}">
                                
                                <h3>${set.nome}</h3>
                                <p class="prezzo">€ ${set.prezzo}</p>
                                
                                <div class="azioni-prodotto">
                                    <a href="${pageContext.request.contextPath}/ProdottoServlet?id=${set.codiceSet}" class="btn-secondario">Dettagli</a>
                                    
                                    <form action="${pageContext.request.contextPath}/gestioneCarrelloServlet" method="POST" style="margin: 0;">
                                        <input type="hidden" name="action" value="add">
                                        <input type="hidden" name="idSet" value="${set.codiceSet}">
                                        <button type="submit" class="btn-primario">Aggiungi</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
    <script src="${pageContext.request.contextPath}/js/filtri.js"></script>
</body>
</html>
