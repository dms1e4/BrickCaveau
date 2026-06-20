<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${prodotto.nome} - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/prodotto.css">
</head>
<body>
    
    <jsp:include page="/fragments/header.jsp" />

    <main class="prodotto-container">
        <%-- gestione dell'errore prodotto dalla servlet recensione --%>
        
        <c:if test="${param.success == 'recensione_inviata'}">
            <div class="msg-success">
                Grazie! La tua recensione è stata pubblicata con successo.
            </div>
        </c:if>
        <c:if test="${param.error == 'dati_non_validi'}">
            <div class="msg-errore">
                Assicurati di inserire un voto valido (1-5) e di non lasciare il testo vuoto.
            </div>
        </c:if>
        
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
                        
                        <%-- media delle recensioni --%>
                        
                        <div class="rating-medio">
                            <c:choose>
                                <c:when test="${numeroRecensioni > 0}">
                                    ⭐ ${mediaVoti} / 5 <span>(${numeroRecensioni} recensioni)</span>
                                </c:when>
                                <c:otherwise>
                                    <span>Ancora nessuna recensione. Sii il primo!</span>
                                </c:otherwise>
                            </c:choose>
                            
                        </div>
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
                                        <span>Disponibile (${prodotto.quantitaMagazzino} pz.)</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span>Esaurito</span>
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
                    
                    <%-- form di inserimento recensione solo per utenti loggati --%>
                    
                    <c:choose>
                        <%-- utente loggato che HA acquistato --%>
                        <c:when test="${not empty sessionScope.utente && utenteHaAcquistato}">
                            <div class="scrivi-recensione">
                                <h4>Scrivi la tua recensione </h4>
                                <form action="${pageContext.request.contextPath}/AggiungiRecensioneServlet" method="POST">
                                    <input type="hidden" name="codiceSet" value="${prodotto.codiceSet}">
                                    
                                    <div>
                                        <label for="rating">Voto:</label>
                                        <select name="rating" id="rating" required>
                                            <option value="5">⭐⭐⭐⭐⭐ (Eccezionale)</option>
                                            <option value="4">⭐⭐⭐⭐ (Molto Buono)</option>
                                            <option value="3">⭐⭐⭐ (Nella media)</option>
                                            <option value="2">⭐⭐ (Deludente)</option>
                                            <option value="1">⭐ (Pessimo)</option>
                                        </select>
                                    </div>
                                    
                                    <div>
                                        <label for="testo">Commento (max 750 caratteri):</label>
                                        <textarea name="testo" id="testo" rows="4" required maxlength="750" placeholder="Cosa ne pensi di questo set?"></textarea>
                                    </div>
                                    
                                    <button type="submit" class="btn-primario">Pubblica Recensione</button>
                                </form>
                            </div>
                        </c:when>
                        
                        <%-- utente loggato ma NON ha acquistato --%>
                        <c:when test="${not empty sessionScope.utente && !utenteHaAcquistato}">
                            <div>
                                <p><strong>Recensione riservata:</strong> Solo i clienti che hanno acquistato questo set LEGO nel nostro negozio possono lasciare una recensione.</p>
                            </div>
                        </c:when>
                        
                        <%-- utente non loggato --%>
                        <c:otherwise>
                            <div>
                                <p>Vuoi condividere la tua opinione? <a href="${pageContext.request.contextPath}/login.jsp">Accedi</a> per verificare il tuo acquisto e scrivere una recensione.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                    <%-- recensioni già esistenti --%>
                    
                    	<div class="lista-recensioni">
                        <c:choose>
                            <c:when test="${empty listaRecensioni}">
                                <p>Nessuno ha ancora recensito questo set.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="rec" items="${listaRecensioni}">
                                    <div class="recensione-card">
                                        <div class="rec-header">
                                            <strong>${rec.nomeUtente}</strong>
                                            
                                            <%-- stampa del numero di stelle --%>
                                            <span>
                                                <c:forEach begin="1" end="${rec.rating}">★</c:forEach>
                                                
                                                <%-- stelle grigie rimanenti --%>
                                                
                                                <span><c:forEach begin="${rec.rating + 1}" end="5">★</c:forEach></span>
                                            </span>
                                            
                                            <span>${rec.dataRecensione}</span>
                                        </div>
                                        <p>${rec.testo}</p>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>

                </div>

            </c:when>
            
            <c:otherwise>
                <div>
                    <h3>Prodotto non trovato.</h3>
                    <p>Il set richiesto non è presente nel Caveau.</p>
                    <a href="${pageContext.request.contextPath}/catalogo.jsp" class="btn-primario">Torna al catalogo</a>
                </div>
            </c:otherwise>
        </c:choose>

    </main>

    <jsp:include page="/fragments/footer.jsp" />

</body>
</html>