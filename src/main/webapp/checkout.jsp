<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="profilo-container">
        <h1>Riepilogo Ordine</h1>
        <p>Controlla i dati e conferma l'acquisto.</p>

        <%-- form da inviare alla servlet --%>
        <form action="${pageContext.request.contextPath}/CheckoutServlet" method="POST">
            
            <div class="profilo-grid">
                
                <%-- colonna a sinistra dei dati --%>
                <div>
                    
                    <section class="card-profilo">
                        <h2>1. Indirizzo di Spedizione</h2>
                        <div>
                            <c:forEach var="ind" items="${listaIndirizzi}" varStatus="loop">
                                <label>
                                    <%-- primo indirizzo selezionato di default --%>
                                    <c:choose>
									    <c:when test="${loop.first}">
									        <input type="radio" name="idIndirizzo" value="${ind.id}" required checked>
									    </c:when>
									    <c:otherwise>
									    	<input type="radio" name="idIndirizzo" value="${ind.id}" required>
									    </c:otherwise>
									</c:choose>
                                    <div>
                                        <strong>${ind.via}, ${ind.nCivico}</strong><br>
                                        <span>
                                            ${ind.cap} ${ind.citta} (${ind.provincia})
                                        </span>
                                    </div>
                                </label>
                            </c:forEach>
                        </div>
                    </section>

                    <section class="card-profilo">
                        <h2>2. Metodo di Pagamento</h2>
                        <div>
                            <c:forEach var="metodo" items="${listaMetodi}" varStatus="loop">
                                <label>
                                    <c:choose>
									    <c:when test="${loop.first}">
									        <input type="radio" name="idMetodo" value="${metodo.id}" required checked>
									    </c:when>
									    <c:otherwise>
									        <input type="radio" name="idMetodo" value="${metodo.id}" required>
									    </c:otherwise>
									</c:choose>
									<p>
                                        <strong>${metodo.tipo}</strong> terminante in <strong>**** ${metodo.ultime4Cifre}</strong>
                                    </p>
                                </label>
                            </c:forEach>
                        </div>
                    </section>

                </div>

                <%-- riepilogo del carrello a destra --%>
                <div>
                    <section class="card-profilo">
                        <h2>Il tuo Carrello</h2>
                        <ul >
                            <%-- Sostituito 'prodotti' con 'elementi' per allinearsi a getElementi() --%>
                            <c:forEach var="item" items="${sessionScope.carrello.elementi}">
                                <li>
                                    <span>${item.prodotto.nome} (x${item.quantita})</span>
                                    <strong>€ ${item.prodotto.prezzo * item.quantita}</strong>
                                </li>
                            </c:forEach>
                        </ul>
                        
                        <div>
                            <span><strong>Totale da pagare:</strong></span>
                            <span><strong>€ ${sessionScope.carrello.prezzoTotaleComplessivo}</strong></span>
                        </div>

                        <button type="submit" class="btn-primario">
                            Conferma Ordine e Paga
                        </button>
                    </section>
                </div>

            </div>
        </form>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>