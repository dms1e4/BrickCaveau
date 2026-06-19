<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Profilo - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
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
        
        <c:if test="${param.success == 'metodo_rimosso'}">
		    <div class="alert-successo">Il metodo di pagamento è stato rimosso correttamente.</div>
		</c:if>
		
		<c:if test="${param.success == 'indirizzo_rimosso'}">
		    <div class="alert-successo">L'indirizzo è stato rimosso correttamente.</div>
		</c:if>
		
		<c:if test="${param.success == 'indirizzo_aggiunto'}">
		    <div class="alert-successo">L'indirizzo è stato aggiunto correttamente.</div>
		</c:if>
		
		<c:if test="${param.info == 'dati_mancanti'}">
			<div class="alert-errore">Aggiungi un metodo di pagamento / indirizzo per procedere.</div>
        </c:if>
        
        <div class="profilo-grid">
            <%-- dati personali --%>
            <section class="card-profilo">
                <h2>I tuoi Dati</h2>
                <div class="info-riga"><strong>Nome:</strong> ${sessionScope.utente.nome}</div>
                <div class="info-riga"><strong>Cognome:</strong> ${sessionScope.utente.cognome}</div>
                <div class="info-riga"><strong>Email:</strong> ${sessionScope.utente.email}</div>
                <div class="info-riga"><strong>Telefono:</strong> ${sessionScope.utente.telefono}</div>
                
                <h2>I tuoi Metodi di Pagamento</h2>
                <c:choose>
                	<c:when test="${not empty listaMetodi}">
                        <ul>
                            <c:forEach var="metodo" items="${listaMetodi}">
                                <li>
                                <div>
                                    <strong>${metodo.tipo}</strong> terminante in <strong>**** ${metodo.ultime4Cifre}</strong> 
                                    <br><span>Scadenza: ${metodo.scadenza}</span>
                                </div>
                                    <form action="${pageContext.request.contextPath}/RimuoviMetodoServlet" method="POST" 
						              onsubmit="return confirm('Sei sicuro di voler rimuovere definitivamente questa carta?');">
						            <input type="hidden" name="idMetodo" value="${metodo.id}">
									<button type="submit" class="btn-secondario">Rimuovi</button>
						        </form>
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
            
            <%-- Sezione Indirizzi di Spedizione --%>
			<section class="card-profilo">
			    <h2 >Indirizzi di Spedizione</h2>
			    
			    <c:choose>
			        <c:when test="${not empty listaIndirizzi}">
			            <ul>
			                <c:forEach var="ind" items="${listaIndirizzi}">
			                    <li>
			                        <div>
			                            <strong>${ind.via}, ${ind.nCivico}</strong><br>
			                            <span>
			                                ${ind.cap} ${ind.citta} (${ind.provincia}) - ${ind.nazione}
			                            </span>
			                        </div>
			                        
			                        <form action="${pageContext.request.contextPath}/RimuoviIndirizzoServlet" method="POST" 
			                              onsubmit="return confirm('Vuoi davvero rimuovere questo indirizzo?');">
			                            <input type="hidden" name="idIndirizzo" value="${ind.id}">
			                            <button type="submit">Rimuovi</button>
			                        </form>
			                    </li>
			                </c:forEach>
			            </ul>
			        </c:when>
			        <c:otherwise>
			            <p>Nessun indirizzo salvato.</p>
			        </c:otherwise>
			    </c:choose>
			
			    <%-- FORM AGGIUNTA INDIRIZZO --%>
			    <div>
			        <h3>Aggiungi Nuovo Indirizzo</h3>
			        <form action="${pageContext.request.contextPath}/AggiungiIndirizzoServlet" method="POST">
			            
			            <div>
			                <label for="via">Via / Piazza:</label>
			                <input type="text" id="via" name="via" required placeholder="Es: Via Roma" maxlength="75">
			            </div>
			            <div>
			                <label for="civico">N. Civico:</label>
			                <input type="text" id="civico" name="civico" required placeholder="Es: 12/A" maxlength="10">
			            </div>
			            
			            <div>
			                <label for="citta">Città:</label>
			                <input type="text" id="citta" name="citta" required placeholder="Es: Pomigliano d'Arco" maxlength="50">
			            </div>
			            <div>
			                <label for="cap">CAP:</label>
			                <input type="text" id="cap" name="cap" required pattern="\d{5}" placeholder="Es: 80038" maxlength="5">
			            </div>
			            
			            <div>
			                <label for="provincia">Prov.:</label>
			                <input type="text" id="provincia" name="provincia" required placeholder="Es: NA" maxlength="15">
			            </div>
			            <div>
			                <label for="nazione">Nazione:</label>
			                <input type="text" id="nazione" name="nazione" required value="Italia" maxlength="20">
			            </div>
			
			            <button type="submit" class="btn-primario">Salva Indirizzo</button>
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