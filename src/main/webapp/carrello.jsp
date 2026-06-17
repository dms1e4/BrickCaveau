<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Carrello - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/carrello.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="cart-container">
        <h1>Il tuo Carrello</h1>

        <%-- controllo se il carrello esiste e ha almeno un elemento --%>
        <c:choose>
            <c:when test="${empty sessionScope.carrello or empty sessionScope.carrello.elementi}">
                <div>
                    <p>Il tuo carrello è vuoto. Tonnellate di mattoncini ti aspettano!</p>
                    	<a href="${pageContext.request.contextPath}/catalogoServlet" class="btn-catalogo">Torna al Catalogo</a>
                    </div>
            </c:when>
            
            <c:otherwise>
                <div>
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th>Codice Set</th>
                                <th>Prodotto</th>
                                <th>Prezzo Unit.</th>
                                <th>Quantità</th>
                                <th>Totale Parziale</th>
                                <th>Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%-- Itero sugli ElementoCarrello restituiti da carrello.getElementi() --%>
                            <c:forEach var="item" items="${sessionScope.carrello.elementi}">
                                <tr>
                                    <td>#${item.prodotto.codiceSet}</td>
                                    <td><strong>${item.prodotto.nome}</strong></td>
                                    <td>€ ${item.prodotto.prezzo}</td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/CarrelloServlet" method="POST" class="form-qta">
                                            <input type="hidden" name="azione" value="aggiorna">
                                            <input type="hidden" name="id" value="${item.prodotto.codiceSet}">
                                            <input type="number" name="qta" value="${item.quantita}" min="1" class="input-qta">
                                            <button type="submit" class="btn-azione btn-modifica">Aggiorna</button>
                                        </form>
                                    </td>
                                    <td class="prezzo-totale">
                                        € ${item.prezzoTotaleElemento}
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/CarrelloServlet" method="POST">
                                            <input type="hidden" name="azione" value="rimuovi">
                                            <input type="hidden" name="id" value="${item.prodotto.codiceSet}">
                                            <button type="submit" class="btn-azione btn-elimina">Rimuovi</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="cart-summary">
                    <p>
                        Totale Complessivo: 
                        <strong>€ ${sessionScope.carrello.prezzoTotaleComplessivo}</strong>
                    </p>
                    
                    <div>
                        <form action="${pageContext.request.contextPath}/CarrelloServlet" method="POST" onsubmit="return confirm('Sei sicuro di voler svuotare l\'intero carrello?');">
                            <input type="hidden" name="azione" value="svuota">
                            <button type="submit" class="btn-svuota">Svuota Carrello</button>
                        </form>
                        
                        <a href="${pageContext.request.contextPath}/CheckoutServlet" class="btn-checkout">Procedi al Checkout</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>