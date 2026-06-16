<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Profilo - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />
    <main class="profilo-container">
        <h1>Area Personale</h1>
        <p>Benvenuto nel tuo profilo, <strong>${sessionScope.utente.nome}</strong>!</p>
        
        <%-- NOTIFICHE ED ERRORI (PARAMETRO IN URL) --%>
		
		<c:if test="${param.success == 'ordine_completato'}">
            <div class="alert-successo">Ordine completato con successo! Grazie per il tuo acquisto.</div>
        </c:if>
        
        <c:if test="${param.success == 'metodo_aggiunto'}">
            <div class="alert-successo">Nuovo metodo di pagamento salvato con successo!</div>
        </c:if>

        <c:if test="${param.error == 'carta_duplicata'}">
            <div class="alert-errore">Errore: Hai già salvato una carta che termina con queste cifre.</div>
        </c:if>

        <c:if test="${param.error == 'carta_scaduta'}">
            <div class="alert-errore">Errore: La data di scadenza inserita non è valida.</div>
        </c:if>
        
        <div class="profilo-grid">
            <%-- dati personali --%>
            <section class="card-profilo">
                <h2>I tuoi Dati</h2>
                <div class="info-riga"><strong>Nome:</strong> ${sessionScope.utente.nome}</div>
                <div class="info-riga"><strong>Cognome:</strong> ${sessionScope.utente.cognome}</div>
                <div class="info-riga"><strong>Email:</strong> ${sessionScope.utente.email}</div>
                <%-- campi futuri: indirizzo ecc. --%>
                
                <h2>I tuoi Metodi di Pagamento</h2>
                <c:choose>
                	<c:when test="${not empty listaMetodi}">
                        <ul>
                            <c:forEach var="metodo" items="${listaMetodi}">
                                <li>
                                    <strong>${metodo.tipo}</strong> terminante in <strong>**** ${metodo.ultime4Cifre}</strong> 
                                    <br><span>Scadenza: ${metodo.scadenza}</span>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <p>Non hai ancora salvato nessun metodo di pagamento.</p>
                    </c:otherwise>
                </c:choose>

                <%-- aggiungere carta --%>
                <div>
                    <h3>Aggiungi Nuova Carta</h3>
                    <form id="form-carta" onsubmit="return eseguiControlloLuhn(event)" action="${pageContext.request.contextPath}/AggiungiMetodoServlet" method="POST">
                        <input type="hidden" name="tipo" value="Carta di Credito">
                        
                        <label for="numeroCarta">Numero Carta:</label>
                        <input type="text" id="numeroCarta" name="numeroCarta" maxlength="16" pattern="\d{16}" required placeholder="1234567812345678">
                        
                        <div id="errore-luhn">
                        	Numero di carta non valido. Controlla di averlo digitato correttamente.
                        </div>
                        
                        <label for="scadenza">Data Scadenza:</label>
                        <input type="date" id="scadenza" name="scadenza" min="<%= LocalDate.now().toString() %>" required>
                        
                        <button type="submit" class="btn-primario">Salva Metodo</button>
                    </form>
                </div>
            </section>

            <%-- storico ordini --%>
            <section class="card-profilo">
                <h2>I tuoi Ordini</h2>
                
                <c:choose>
                    <c:when test="${empty listaOrdiniCliente}">
                        <p>Non hai ancora effettuato ordini. Il tuo carrello aspetta nuovi set LEGO!</p>
                        <a href="${pageContext.request.contextPath}/catalogoServlet" class="vai-catalogo">Vai al Catalogo</a>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <table class="ordini-table">
                                <thead>
                                    <tr>
                                        <th>ID Ordine</th>
                                        <th>Data</th>
                                        <th>Totale</th>
                                        <th>Stato</th>
                                        <th>Azioni</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ordine" items="${listaOrdiniCliente}">
                                        <tr>
                                            <td>#${ordine.id}</td> 
                                            <td>${ordine.dataOrdine}</td>
                                            <td>€ ${ordine.totale}</td>
                                            <td><span>${ordine.statoSpedizione}</span></td>
                                            <td>
											    <a href="${pageContext.request.contextPath}/FatturaServlet?id=${ordine.id}" 
											       class="btn-secondario" >Visualizza</a>
											</td>
                                            
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
        </div>
    </main>
	
	<script src="${pageContext.request.contextPath}/js/luhn.js"></script>
    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>