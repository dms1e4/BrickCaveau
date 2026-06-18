<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Wishlist - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/wishlist.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogo.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main>
        <h1>La mia Wishlist</h1>

        <c:choose>
            <c:when test="${empty listaPreferiti}">
                <div class="messaggio-vuoto">
                    <h2>La tua wishlist è vuota.</h2>
                    <p>Esplora il catalogo e aggiungi i tuoi set preferiti!</p>
                    <a href="${pageContext.request.contextPath}/catalogoServlet" class="btn-primario">Vai al Catalogo</a>
                </div>
            </c:when>
            
            <c:otherwise>
                <div class="grid-catalogo">
                    <c:forEach var="set" items="${listaPreferiti}">
                        <div class="card-prodotto">
                            <button class="btn-wishlist active" data-id="${set.codiceSet}" onclick="toggleWishlist(this)" title="Rimuovi dai Preferiti">
				                ★
				            </button>
                            <img src="${pageContext.request.contextPath}/images/Set/${set.codiceSet}_1.jpg" 
                                 onerror="this.src='${pageContext.request.contextPath}/images/logo.png'" 
                                 alt="Immagine ${set.nome}">
                            
                            <h3>${set.nome}</h3>
                            <p class="prezzo">€ ${set.prezzo}</p>
                            
                            <div class="azioni-prodotto">
                                <a href="${pageContext.request.contextPath}/ProdottoServlet?id=${set.codiceSet}" class="btn-secondario">
                                <img src="${pageContext.request.contextPath}/images/icone/dettagli.png" alt="Dettagli Prodotto" class="icona-carrello dettagli">
                                Dettagli</a>
                                <form action="${pageContext.request.contextPath}/CarrelloServlet" method="POST">
                                    <input type="hidden" name="azione" value="aggiungi">
                                    <input type="hidden" name="id" value="${set.codiceSet}">
                                    <button type="submit" class="btn-primario">
                                    	<img src="${pageContext.request.contextPath}/images/icone/aggiungi_carrello.png" alt="Aggiungi" class="icona-carrello">
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
    <script src="${pageContext.request.contextPath}/js/wishlist.js"></script>
</body>
</html>
