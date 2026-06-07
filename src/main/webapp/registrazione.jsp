<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrazione - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    
    <script src="${pageContext.request.contextPath}/js/validazione.js" defer></script>
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="registrazione-container">
        <h2>Crea il tuo account BrickCaveau</h2>

        <form action="${pageContext.request.contextPath}/registerServlet" method="POST" id="formRegistrazione">
            
            <label for="nome">Nome:</label>
            <input type="text" name="nome" id="nome" required>
            <span id="errorNome" class="testo-errore"></span><br>

            <label for="cognome">Cognome:</label>
            <input type="text" name="cognome" id="cognome" required>
            <span id="errorCognome" class="testo-errore"></span><br>

            <label for="email">Indirizzo e-mail:</label>
            <input type="email" name="email" id="email" required>
            <span id="emailFeedback"></span><br>

            <label for="password">Password:</label>
            <input type="password" name="password" id="password" placeholder="Almeno 8 caratteri" required>
            <span id="errorPassword" class="testo-errore"></span><br>

            <label for="telefono">Telefono:</label>
            <div style="display: flex; gap: 5px;">
                <select id="prefisso" name="prefisso">
                    <option value="+39" selected>🇮🇹 +39</option>
                    <option value="+378">🇸🇲 +378</option>
                    <option value="+41">🇨🇭 +41</option>
                    <option value="+43">🇦🇹 +43</option>
                    <option value="+33">🇫🇷 +33</option>
                    <option value="+49">🇩🇪 +49</option>
                    <option value="+32">🇧🇪 +32</option>
                    <option value="+31">🇳🇱 +31</option>
                    <option value="+34">🇪🇸 +34</option>
                    <option value="+44">🇬🇧 +44</option>
                    <option value="+1">🇺🇸 +1</option>
                    <option value="+86">🇨🇳 +86</option>
                    <option value="+61">🇦🇺 +61</option>
                    <option value="+81">🇯🇵 +81</option>
                </select>
                <input type="text" name="telefono" id="telefono" placeholder="Es. 3331234567" required>
            </div>
            <span id="errorTelefono" class="testo-errore"></span><br>

            <button type="submit" class="btn-primario" id="btnRegistrati">Registrati</button>
            
            <p>Hai già un account? <a href="${pageContext.request.contextPath}/login.jsp">Accedi qui</a></p>
        </form>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>
