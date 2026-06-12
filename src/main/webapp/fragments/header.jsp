<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ricerca.css">

<header>
    <div class="top-bar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo BrickCaveau" class="logo-img">
            </a>
        </div>
        
       <div class="search-container" style="position: relative;">
    		<form class="search-form" onsubmit="return false;" data-context="${pageContext.request.contextPath}">
       			<input type="text" id="searchBar" class="search-input" autocomplete="off" placeholder="Cerca nel Caveau...">
        
        		<div id="searchResults" class="resultBox" hidden="true">
            	</div>
    		</form>
		</div>
        
        <div class="user-interaction">
            <%-- Controllo dinamico della sessione --%>
            <c:choose>
            	<c:when test="${not empty sessionScope.utente and sessionScope.utente.is_Admin()}">
			        <a href="${pageContext.request.contextPath}/admin/dashboardServlet" class="icona">Dashboard Admin</a>
			        <a href="${pageContext.request.contextPath}/LogoutServlet" class="icona">Esci</a>
			    </c:when>
                <c:when test="${not empty sessionScope.utente}">
                    <a href="${pageContext.request.contextPath}/profilo.jsp" class="icona">Profilo</a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="icona">Esci</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="icona">Accedi / Registrati</a>
                </c:otherwise>
            </c:choose>
            <a href="${pageContext.request.contextPath}/carrello.jsp" class="icona">Carrello</a>
        </div>
    </div>
    
    <nav class="nav-header">
        <ul>
            <li><a href="${pageContext.request.contextPath}/catalogoServlet">Tutti i set</a></li>
        </ul>
    </nav>
    
    <script src="${pageContext.request.contextPath}/js/ricerca.js"></script>
</header>
