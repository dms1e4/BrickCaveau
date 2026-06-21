<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="login-container">
        <h2>Accedi al tuo Caveau</h2>
        
        <%-- ERRORI / MESSAGGI --%>
		<c:if test="${param.status == 'registrato'}">
		    <p class="text-success">
		        Registrazione completata con successo! Ora puoi accedere.
		    </p>
		</c:if>
		
		<c:if test="${param.error == 'invalid'}">
            <p class="carrello-errore">Credenziali errate. Riprova.</p>
        </c:if>
        
        <c:if test="${param.error == 'vuoti'}">
            <p class="carrello-errore">Per favore, compila tutti i campi.</p>
        </c:if>
        
		<%-- Messaggio se si accede al carrello da non loggati --%>
		<c:if test="${not empty param.error}">
		    <div class="carrello-errore">
		        ${param.error}
		    </div>
		</c:if>

        <form action="${pageContext.request.contextPath}/loginServlet" method="POST">
            <label for="email">Indirizzo e-mail:</label>
            <input type="email" name="email" id="email" required>
            
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
            
            <div>
                <input type="checkbox" onclick="togglePassword()"> Mostra password
            </div>
            
            <button type="submit" class="btn-primario">Accedi</button>
            
            <p>Non hai un account? <a href="${pageContext.request.contextPath}/registrazione.jsp">Registrati ora!</a></p>
        </form>
    </main>

    <jsp:include page="/fragments/footer.jsp" />

    <script>
        function togglePassword() {
            var x = document.getElementById("password");
            if (x.type === "password") {
                x.type = "text";
            } else {
                x.type = "password";
            }
        }
    </script>
</body>
</html>
