<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Admin - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="dashboard-container">
        <div class="dashboard-header">
            <h1>Pannello di Controllo</h1>
            <p>Benvenuto nel caveau di amministrazione, ${sessionScope.utente.nome}.</p>
        </div>

        <%-- Messaggio di feedback (es. "Prodotto inserito con successo") --%>
        <c:if test="${not empty param.success}">
            <div class="msg-success">
                Operazione completata con successo!
            </div>
        </c:if>

        <div class="dashboard-layout">
            
            <section class="admin-section">
                <div class="section-title-bar">
                    <h2>Gestione Catalogo</h2>
                    <a href="${pageContext.request.contextPath}/admin/inserisciProdotto.jsp" class="btn-primario">+ Nuovo Set</a>
                </div>
                
                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome Set</th>
                                <th>Prezzo</th>
                                <th>Pezzi</th>
                                <th>Azioni</th>
                            </tr>
                        </thead>
                        <tbody> <%-- prodotto servlet --%>
    						<c:forEach var="set" items="${listaProdottiAdmin}">
						        <tr>
						            <td>#${set.codiceSet}</td>
						            <td>${set.nome}</td>
						            <td>€ ${set.prezzo}</td>
						            <td>${set.nPezzi}</td>
						            <td class="azioni-tabella">
						                <a href="${pageContext.request.contextPath}/admin/ModificaProdottoServlet?id=${set.codiceSet}" class="btn-azione btn-modifica">Modifica</a>
						                
						                <form action="${pageContext.request.contextPath}/admin/EliminaProdottoServlet" method="POST" class="form-elimina" 
						                      onsubmit="return confirm('ATTENZIONE: Sei sicuro di voler cancellare il set ${set.nome} dal catalogo? Questa azione è irreversibile!');">
						                    <input type="hidden" name="idSet" value="${set.codiceSet}">
						                    <button type="submit" class="btn-azione btn-elimina">Elimina</button>
						                </form>
						            </td>
						        </tr>
						    </c:forEach>
						    <%-- fallback se catalogo è vuoto --%>
						    <c:if test="${empty listaProdottiAdmin}">
						        <tr>
						            <td colspan="5" style="text-align:center; padding:20px;">Nessun prodotto trovato.</td>
						        </tr>
						    </c:if>
						</tbody>
                    </table>
                </div>
            </section>

            <section class="admin-section mt-40">
                <div class="section-title-bar">
                    <h2>Storico Ordini Clienti</h2>
                </div>
                
                <form action="${pageContext.request.contextPath}/admin/FiltraOrdiniServlet" method="GET" class="form-filtri">
                    <div class="filtro-gruppo">
                        <label for="dataDa">Da data:</label>
                        <input type="date" name="dataDa" id="dataDa">
                    </div>
                    <div class="filtro-gruppo">
                        <label for="dataA">A data:</label>
                        <input type="date" name="dataA" id="dataA">
                    </div>
                    <div class="filtro-gruppo">
                        <label for="emailCliente">Cliente (Email):</label>
                        <input type="email" name="emailCliente" id="emailCliente" placeholder="es. mario@email.com">
                    </div>
                    <div class="filtro-gruppo btn-filtro-container">
                        <button type="submit" class="btn-secondario">Applica Filtri</button>
                    </div>
                </form>

                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID Ordine</th>
                                <th>Data</th>
                                <th>Cliente</th>
                                <th>Totale</th>
                                <th>Azioni</th>
                            </tr>
                        </thead>
                        <tbody>
						    <%-- lista degli ordini --%>
						    <c:forEach var="ordine" items="${listaOrdiniAdmin}">
						        <tr>
						            <td>#${ordine.id}</td>
						            <td>${ordine.dataOrdine}</td>
						            <td>${ordine.utenteEmail}</td>
						            <td>€ ${ordine.totale}</td>
						            <td>
						                <a href="${pageContext.request.contextPath}/admin/DettaglioOrdineAdminServlet?id=${ordine.id}" class="btn-azione btn-modifica">Vedi Dettagli</a>
						            </td>
						        </tr>
						    </c:forEach>
						    <%-- fallback --%>
						    <c:if test="${empty listaOrdiniAdmin}">
						        <tr>
						            <td colspan="5" style="text-align:center; padding:20px;">Nessun ordine trovato con i filtri attuali.</td>
						        </tr>
						    </c:if>
						</tbody>
                    </table>
                </div>
            </section>

        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>