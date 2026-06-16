<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ricerca.css">

<header>
    <div class="top-bar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/Home">
                <img src="${pageContext.request.contextPath}/images/logo_bianco.png" alt="Logo BrickCaveau" class="logo-img">
            </a>
        </div>
        
       <div class="search-container">
    		<form class="search-form" onsubmit="return false;" data-context="${pageContext.request.contextPath}">
       			<input type="text" id="searchBar" class="search-input" autocomplete="off" placeholder="Cerca nel Caveau...">
        
        		<div id="searchResults" class="resultBox" hidden="true">
            	</div>
    		</form>
		</div>
        
        <div class="user-interaction">
            <%-- Controllo dinamico della sessione --%>
            <c:choose>
            	<%-- Utente ADMIN --%>
            	<c:when test="${not empty sessionScope.utente and sessionScope.utente.is_Admin()}">
            	
			        <a href="${pageContext.request.contextPath}/admin/dashboardServlet" class="icona">
			        <img src="${pageContext.request.contextPath}/images/icone/admin.png" alt="Admin" class="icona-img">
			        Dashboard Admin</a>
			        
			        <a href="${pageContext.request.contextPath}/LogoutServlet" class="icona">
			        <img src="${pageContext.request.contextPath}/images/icone/logout.png" alt="Esci" class="icona-img">
			        Esci</a>
			        
			        <a href="${pageContext.request.contextPath}/carrello.jsp" class="icona">
			        <img src="${pageContext.request.contextPath}/images/icone/carrello.png" alt="Carrello" class="icona-img">
			        Carrello</a>
			    </c:when>
			    <%-- Utente LOGGATO --%>
                <c:when test="${not empty sessionScope.utente}">
                
                    <a href="${pageContext.request.contextPath}/ProfiloServlet" class="icona">
                    <img src="${pageContext.request.contextPath}/images/icone/profilo.png" alt="Profilo" class="icona-img">
                    Profilo</a>
                    
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="icona">
                    <img src="${pageContext.request.contextPath}/images/icone/logout.png" alt="Esci" class="icona-img">
                    Esci</a>
                    <a href="${pageContext.request.contextPath}/carrello.jsp" class="icona">
                    <img src="${pageContext.request.contextPath}/images/icone/carrello.png" alt="Carrello" class="icona-img">
                    Carrello</a>
                    
                </c:when>
                <%-- Utente VISITATORE --%>
                <c:otherwise>
                
                    <a href="${pageContext.request.contextPath}/login.jsp" class="icona">
                    <img src="${pageContext.request.contextPath}/images/icone/profilo.png" alt="Accedi" class="icona-img">
                    Accedi / Registrati</a>
                    
                    <a href="${pageContext.request.contextPath}/login.jsp?error=Devi effettuare l'accesso per usare il carrello" class="icona">
                    <img src="${pageContext.request.contextPath}/images/icone/carrello.png" alt="Carrello" class="icona-img">
                    Carrello</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <nav class="nav-header">
        <ul>
            <li><a href="${pageContext.request.contextPath}/catalogoServlet">Tutti i set</a></li>
        </ul>
    </nav>
    
    <script src="${pageContext.request.contextPath}/js/ricerca.js"></script>
</header>
