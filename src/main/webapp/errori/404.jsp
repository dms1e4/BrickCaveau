<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pagina Non Trovata - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errore.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main>
        <h1>404</h1>
        <img src="${pageContext.request.contextPath}/images/404.png" alt="404">
        <h2>Pagina Non Trovata</h2>
        <p>Oops! Sembra che manchi un pezzo per completare questo set.</p>
        <p>La pagina che stai cercando non esiste.</p>
        
        <div>
            <a href="${pageContext.request.contextPath}/catalogo.jsp" class="btn-primario">Esplora il Catalogo</a>
        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>