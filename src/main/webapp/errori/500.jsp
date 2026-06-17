<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Errore Server - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errore.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main>
        <h1>500</h1>
        <img src="${pageContext.request.contextPath}/images/500.png" alt="500">
        <h2>Errore Interno del Server</h2>
        <p>Ahi! I nostri server hanno appena calpestato un mattoncino LEGO a piedi scalzi.</p>
        <p>Si è verificato un errore imprevisto. Stiamo lavorando per risolvere  al più presto.</p>
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-primario">Torna in un Luogo Sicuro</a>
        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>