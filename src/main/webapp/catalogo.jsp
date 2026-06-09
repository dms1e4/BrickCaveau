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

    <main class="catalogo-container">
        <h2>Il Caveau: Tutti i Set Disponibili</h2>

        <c:choose>
            <c:when test="${empty listaSet}">
                <div>
                    <h3>Nessun set LEGO attualmente disponibile.</h3>
                    <p>I nostri esperti stanno cercando nuovi pezzi da collezione. Torna più tardi!</p>
                </div>
            </c:when>
            
            <c:otherwise>
                <%-- griglia responsive --%>
                <div class="grid-catalogo">
                    

                    <c:forEach var="set" items="${listaSet}">
                        <div class="card-prodotto">
                            
                            
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
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>
