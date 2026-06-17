<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accesso Negato - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errore.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main>
        <h1>403</h1>
        <img src="${pageContext.request.contextPath}/images/403.png" alt="403">
        <h2>Accesso Negato</h2>
        <p><strong>Alt!</strong> Questa zona del caveau è riservata esclusivamente agli Amministratori.</p>
        <p>Non hai i permessi necessari per visualizzare questa pagina.</p>
        
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-primario">Torna alla Home</a>
        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>