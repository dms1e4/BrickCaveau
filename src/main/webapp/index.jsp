<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BrickCaveau - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />
    
    <main>
        <section class="banner">
            <div class="banner-content">
                <h1>La piattaforma fatta da collezionisti per i collezionisti</h1>
            </div>
        </section>
    </main>
	    <section class="sezione-popolari">
	    <div class="container-titolo-carosello">
	        <h2>I set che ti consigliamo</h2>
	        <div class="frecce-carosello">
	        </div>
	    </div>
	
	    <div class="carosello-wrapper" id="carosello-set">
	        <c:forEach var="set" items="${setPopolari}">
	            <div class="card-prodotto card-carosello">
	                <img src="${pageContext.request.contextPath}/images/Set/${set.codiceSet}_1.jpg" 
	                     onerror="this.src='${pageContext.request.contextPath}/images/logo.png'" 
	                     alt="Immagine ${set.nome}">
	                
	                <h3>${set.nome}</h3>
	                <p class="prezzo">€ ${set.prezzo}</p>
	                
	                <div class="azioni-prodotto">
	                    <a href="${pageContext.request.contextPath}/ProdottoServlet?id=${set.codiceSet}" class="btn-secondario">
	                    <img src="${pageContext.request.contextPath}/images/icone/dettagli.png" class="icona-btn">
	                    Dettagli</a>
	                </div>
	            </div>
	        </c:forEach>
	    </div>
	</section>
	
    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>