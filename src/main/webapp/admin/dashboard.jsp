<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Admin - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="dashboard-container">
        <div class="dashboard-header">
            <h1>Pannello di Controllo</h1>
            <p>Benvenuto nel caveau di amministrazione, <strong>${sessionScope.utente.nome}.</strong></p>
        </div>

        <%-- feedback positivo: operazione completata --%>
        <c:if test="${not empty param.success}">
            <div class="msg-success">
                Operazione completata con successo!
            </div>
        </c:if>

        <div class="dashboard-layout">
            
            <section class="admin-section">
                <div class="section-title-bar">
                    <h2>Gestione Catalogo</h2>
                    <a href="${pageContext.request.contextPath}/admin/formProdotto.jsp" class="btn-azione btn-aggiungi">+ Nuovo Set</a>
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
						            <td class="non-trovato" colspan="5">Nessun prodotto trovato.</td>
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
                
                <form id="formFiltriOrdini" data-context="${pageContext.request.contextPath}" class="form-filtri" style="margin-bottom: 25px; display: flex; gap: 15px; align-items: flex-end; flex-wrap: wrap; background: #F8F9FA; padding: 15px; border-radius: 8px; border: 1px solid #E2E8F0;">
                
                    <div class="filtro-gruppo" style="margin-bottom: 0;">
                        <label for="dataInizio" style="font-weight:bold; font-size:0.9rem;">Da data:</label>
                        <input type="date" id="dataInizio" style="padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
                    </div>
                    
                    <div class="filtro-gruppo" style="margin-bottom: 0;">
                        <label for="dataFine" style="font-weight:bold; font-size:0.9rem;">A data:</label>
                        <input type="date" id="dataFine" style="padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
                    </div>

                    <div class="filtro-gruppo" style="margin-bottom: 0;">
                        <label for="utenteId" style="font-weight:bold; font-size:0.9rem;">ID Cliente (opzionale):</label>
                        <input type="number" id="utenteId" placeholder="Es. 1" min="1" style="padding: 8px; border: 1px solid #ccc; border-radius: 4px;">
                    </div>

                    <div style="display: flex; gap: 10px;">
                        <button type="submit" class="btn-azione btn-primario">🔍 Filtra</button>
                        <button type="button" id="btnResetOrdini" class="btn-azione btn-secondario" style="background-color: #64748B; color: white;">✖ Reset</button>
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
                        <tbody id="tabellaOrdiniBody">
                            </tbody>
                    </table>
                </div>
            </section>

        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
    <script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</body>
</html>
