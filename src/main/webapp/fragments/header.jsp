<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header>
    <div class="top-bar">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Logo BrickCaveau" class="logo-img">
            </a>
        </div>
        
        <div class="search">
            <input type="text" id="search-input" placeholder="Cerca un set...">
        </div>
        
        <div class="user-interaction">
            <%-- Controllo dinamico della sessione --%>
            <c:choose>
                <c:when test="${not empty sessionScope.utente}">
                    <a href="${pageContext.request.contextPath}/profilo.jsp" class="icona">Profilo</a>
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
</header>