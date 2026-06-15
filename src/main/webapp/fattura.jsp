<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Fattura Ordine #${ordineFattura.id} | BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fattura.css">
</head>
<body>
	<jsp:include page="/fragments/header.jsp" />
    <div class="btn-toolbar no-print">
        <button onclick="window.print()" class="btn-stampa">Stampa Fattura</button>
    </div>

    <div class="fattura-box">
        <div class="intestazione">
            <div>
                <h1 class="titolo-brand">BrickCaveau</h1>
                <p class="sottotitolo-brand">Il caveau dei mattoncini ritirati</p>
                <p>Via Università, Fisciano (SA)<br>P.IVA: IT12345678901</p>
            </div>
            <div class="allineamento-destra">
                <h2 class="titolo-documento">FATTURA #DOC-${ordineFattura.id}</h2>
                <p>Data Ordine: <strong>${ordineFattura.dataOrdine}</strong></p>
            </div>
        </div>

        <div class="dati-cliente">
            <h3 class="sezione-titolo">Intestata a:</h3>
            <p class="paragrafo-dati">
                <strong>${sessionScope.utente.nome} ${sessionScope.utente.cognome}</strong><br>
                Email: ${sessionScope.utente.email}<br>
                Codice Cliente: ${sessionScope.utente.idUtente}
            </p>
        </div>

        <table class="tabella-prodotti">
            <thead>
                <tr>
                    <th>Cod. Set</th>
                    <th>Descrizione</th>
                    <th class="centro">Quantità</th>
                    <th class="destra">Prezzo Unit.</th>
                    <th class="centro">IVA</th>
                    <th class="destra">Totale Riga</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${dettagliFattura}">
                    <tr>
                        <td>#${item.codiceSet}</td>
                        <td><strong>${item.nomeSet}</strong></td>
                        <td class="centro">${item.quantita}</td>
                        <td class="destra">€ ${item.prezzoAcquisto}</td>
                        <td class="centro">${item.iva}%</td>
                        <td class="destra l-evidenziato">€ ${item.totaleRiga}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="totali-box">
            <h2 class="totale-definitivo">Totale da Pagare: <span class="colore-totale">€ ${ordineFattura.totale}</span></h2>
            <p class="nota-fiscale">*Il totale include l'IVA come indicato per ogni riga.</p>
        </div>
    </div>
<jsp:include page="/fragments/footer.jsp" />
</body>
</html>