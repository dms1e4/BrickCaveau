<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${prodotto.nome} - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prodotto.css">
</head>
<body>
    
    <jsp:include page="/fragments/header.jsp" />

    <main class="prodotto-container">
        
        <c:choose>
            <c:when test="${not empty prodotto}">
                
                <div class="prodotto-grid">
                    
                    <div class="prodotto-galleria">
                        <img id="img-main" class="img-principale" 
                             src="${pageContext.request.contextPath}/images/Set/${prodotto.codiceSet}_1.jpg" 
                             alt="Immagine principale ${prodotto.nome}">
                        
                        <div class="miniature-container">
                            <c:forEach begin="1" end="2" var="i">
                                <img src="${pageContext.request.contextPath}/images/Set/${prodotto.codiceSet}_${i}.jpg" 
                                     alt="Vista ${i}" 
                                     onclick="document.getElementById('img-main').src=this.src;">
                            </c:forEach>
                        </div>
                    </div>

                    <div class="prodotto-info">
                        <h2>${prodotto.nome}</h2>
                        <div class="tema">Set N°: ${prodotto.codiceSet} | Tema: ${prodotto.tema}</div>
                        
                        <div class="prezzo">€ ${prodotto.prezzo}</div>

                        <div class="specifiche">
                            <p><strong>Pezzi:</strong> ${prodotto.nPezzi}</p>
                            <p><strong>Anno Uscita:</strong> ${prodotto.annoUscita}</p>
                            <c:if test="${not empty prodotto.annoRitiro && prodotto.annoRitiro != 0}">
                                <p><strong>Ritirato nel:</strong> ${prodotto.annoRitiro}</p>
                            </c:if>
                            
                            <p><strong>Disponibilità:</strong> 
                                <c:choose>
                                    <c:when test="${prodotto.quantitaMagazzino > 0}">
                                        <span style="color: green;">Disponibile (${prodotto.quantitaMagazzino} pz.)</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: red;">Esaurito</span>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </div>

                        <p>${prodotto.descrizione}</p>

                        <c:if test="${prodotto.quantitaMagazzino > 0}">
                            <form action="${pageContext.request.contextPath}/CarrelloServlet" method="POST" class="form-carrello">
                                <input type="hidden" name="azione" value="aggiungi">
                                <input type="hidden" name="id" value="${prodotto.codiceSet}">
                                
                                <label for="quantita">Quantità:</label>
                                <input type="number" id="quantita" name="qta" value="1" min="1" max="${prodotto.quantitaMagazzino}">
                                
                                <button type="submit" class="btn-primario">Aggiungi al Carrello</button>
                            </form>
                        </c:if>

                    </div>
                </div>

                <div class="recensioni-container">
                    <h3>Recensioni dei clienti</h3>
                    	<c:forEach var="rec" items="${listaRecensioni}">
						    <div class="recensione">
						        <p>Voto: ${rec.rating}</p>
						        <p>Commento: ${rec.testo}</p>
						        <p>Utente: ${rec.nomeUtente}</p>
						    </div>
						</c:forEach>
                </div>

            </c:when>
            
            <c:otherwise>
                <h3>Prodotto non trovato.</h3>
                <p>Il set richiesto non è presente nel Caveau.</p>
            </c:otherwise>
        </c:choose>

    </main>

    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>