<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettaglio Ordine #${ordine.id} - BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <style>
        .dettaglio-grid {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 25px;
            margin-top: 20px;
        }
        @media (max-width: 768px) {
            .dettaglio-grid { grid-template-columns: 1fr; }
        }
        .info-card {
            background: #F8F9FA;
            border: 1px solid #E2E8F0;
            border-radius: 8px;
            padding: 20px;
        }
        .info-card p {
            margin: 10px 0;
            font-size: 0.95rem;
            color: #334155;
        }
        .info-card strong {
            color: #1E293B;
        }
        .totale-evidenziato {
            font-size: 1.2rem;
            font-weight: bold;
            color: #2563EB;
        }
    </style>
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="dashboard-container">
        <div class="dashboard-header" style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 15px;">
            <div>
                <h1>Dettaglio Ordine #${ordine.id}</h1>
                <p>Gestione e riepilogo delle informazioni dell'acquisto.</p>
            </div>
            <a href="${pageContext.request.contextPath}/admin/dashboardServlet" class="btn-azione btn-secondario" style="background-color: #64748B; color: white; text-decoration: none; padding: 10px 15px; border-radius: 4px;">
                ← Torna alla Dashboard
            </a>
        </div>

        <div class="dettaglio-grid">
            
            <div class="info-card">
                <h3 style="margin-top:0; border-bottom: 2px solid #E2E8F0; padding-bottom: 8px; color:#1E293B;">Dati Riepilogo</h3>
                
                <p><strong>Cliente:</strong> ${cliente.nome} ${cliente.cognome}</p>
                <p><strong>Email:</strong> ${cliente.email}</p>
                <p><strong>ID Utente nel Sistema:</strong> #${ordine.utenteId}</p>
                <hr style="border: 0; border-top: 1px solid #E2E8F0; margin: 15px 0;">
                <p><strong>Data Acquisto:</strong> ${ordine.dataOrdine}</p>
                <p><strong>Stato Spedizione:</strong> 
                    <span style="background: #E0F2FE; color: #0369A1; padding: 3px 8px; border-radius: 12px; font-size: 0.85rem; font-weight: bold;">
                        ${empty ordine.statoSpedizione ? 'In Elaborazione' : ordine.statoSpedizione}
                    </span>
                </p>
            </div>

            <section class="admin-section" style="margin: 0;">
                <h3 style="margin-top:0; margin-bottom: 15px; color:#1E293B;">Set Acquistati</h3>
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
                                    <td style="font-weight: 500;">${item.nomeSet}</td>
                                    <td>x${item.quantita}</td>
                                    <td>€ ${item.prezzoAcquisto}</td>
                                    <td>${item.iva}%</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr style="background: #F1F5F9;">
                                <td colspan="4" style="text-align: right; font-weight: bold; padding: 15px;">Totale Complessivo:</td>
                                <td class="totale-evidenziato" style="padding: 15px;">€ ${ordine.totale}</td>
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
