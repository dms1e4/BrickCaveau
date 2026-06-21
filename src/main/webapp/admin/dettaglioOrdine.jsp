<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettaglio Ordine #${ordine.id} - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dettaglioOrdine.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="dashboard-container">
        <div class="dashboard-header">
            <div>
                <h1>Dettaglio Ordine #${ordine.id}</h1>
                <p>Gestione e riepilogo delle informazioni dell'acquisto.</p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/dashboardServlet" class="btn-azione btn-secondario">
                ← Torna alla Dashboard
            </a>
        </div>

        <div class="dettaglio-grid">
            
            <div class="info-card">
                <h3>Dati Riepilogo</h3>
                
                <p><strong>Cliente:</strong> ${cliente.nome} ${cliente.cognome}</p>
                <p><strong>Email:</strong> ${cliente.email}</p>
                <p><strong>ID Utente nel Sistema:</strong> #${ordine.utenteId}</p>
                <hr>
                <p><strong>Data Acquisto:</strong> ${ordine.dataOrdine}</p>
                <p><strong>Stato Spedizione:</strong> 
                    <span>
                        ${empty ordine.statoSpedizione ? 'In Elaborazione' : ordine.statoSpedizione}
                    </span>
                </p>
            </div>

            <section class="admin-section">
                <h3>Set Acquistati</h3>
                <div class="table-responsive">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <th>ID Set</th>
                                <th>Nome Prodotto</th>
                                <th>Quantità</th>
                                <th>Prezzo Unitario</th>
                                <th>Aliquota IVA</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${dettagli}">
                                <tr>
                                    <td>#${item.codiceSet}</td>
                                    <td>${item.nomeSet}</td>
                                    <td>x${item.quantita}</td>
                                    <td>€ ${item.prezzoAcquisto}</td>
                                    <td>${item.iva}%</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="4">Totale Complessivo:</td>
                                <td class="totale-evidenziato">€ ${ordine.totale}</td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </section>

        </div>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>
