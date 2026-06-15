<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<c:if test="${param.success == 'ordine_completato'}">
		    <div class="ordine-completato">
		        Ordine completato con successo! Grazie per il tuo acquisto su BrickCaveau.
		    </div>
		</c:if>
        <div class="profilo-grid">
            <%-- dati personali --%>
            <section class="card-profilo">
                <h2>I tuoi Dati</h2>
                <div class="info-riga"><strong>Nome:</strong> ${sessionScope.utente.nome}</div>
                <div class="info-riga"><strong>Cognome:</strong> ${sessionScope.utente.cognome}</div>
                <div class="info-riga"><strong>Email:</strong> ${sessionScope.utente.email}</div>
                <%-- campi futuri: indirizzo ecc. --%>
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
											       class="btn-secondario" >Visualizza / Stampa</a>
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

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>