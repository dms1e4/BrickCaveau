<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty setLego ? 'Modifica Set' : 'Nuovo Set'} - Admin BrickCaveau</title>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/favicon1.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/formProdotto.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="dashboard-container">
        
        <div class="dashboard-header">
            <h1>${not empty setLego ? 'Modifica Set LEGO' : 'Aggiungi Nuovo Set'}</h1>
            
            <c:choose>
                <c:when test="${not empty setLego}">
                    <p>Stai modificando il set #${setLego.codiceSet}</p>
                </c:when>
                <c:otherwise>
                    <p>Inserisci i dettagli del nuovo set nel Caveau.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <section class="admin-section">
            <form action="${pageContext.request.contextPath}/admin/SalvaProdottoServlet" method="POST" enctype="multipart/form-data" class="form-filtri">
                
                <c:if test="${not empty setLego}">
                    <input type="hidden" name="codiceSetOriginale" value="${setLego.codiceSet}">
                    <input type="hidden" name="azione" value="modifica">
                </c:if>
                <c:if test="${empty setLego}">
                    <input type="hidden" name="azione" value="aggiungi">
                </c:if>
                <div class="filtro-gruppo">
                        <label for="codiceSet">Codice Set*</label>
                        <c:choose>
                            <c:when test="${not empty setLego}">
                                <input type="number" id="codiceSet" name="codiceSet" value="${setLego.codiceSet}" required readonly title="Il codice set non può essere modificato">
                            </c:when>
                            <c:otherwise>
                                <input type="number" id="codiceSet" name="codiceSet" required>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="filtro-gruppo">
                        <label for="nome">Nome del Set*</label>
                        <input type="text" id="nome" name="nome" value="${setLego.nome}" required maxlength="100">
                    </div>

                <div>
                    <div class="filtro-gruppo">
                        <label for="tema">Tema*</label>
                        <input type="text" id="tema" name="tema" value="${setLego.tema}" required maxlength="50">
                    </div>
                    <div class="filtro-gruppo">
                        <label for="prezzo">Prezzo (€)*</label>
                        <input type="number" id="prezzo" name="prezzo" value="${setLego.prezzo}" step="0.01" min="0" required>
                    </div>
                    
                    <div class="filtro-gruppo">
                        <label for="iva">IVA (%)</label>
                        <input type="number" id="iva" name="iva" value="${not empty setLego ? setLego.iva : '22.00'}" step="0.01" min="0">
                    </div>
                    
                    <div class="filtro-gruppo">
                        <label for="nPezzi">Numero Pezzi*</label>
                        <input type="number" id="nPezzi" name="nPezzi" value="${setLego.nPezzi}" min="1" required>
                    </div>
                </div>

                <div>
                    <div class="filtro-gruppo">
                        <label for="annoUscita">Anno Uscita*</label>
                        <input type="number" id="annoUscita" name="annoUscita" value="${setLego.annoUscita}" min="1950" max="2026" required>
                    </div>
                    <div class="filtro-gruppo">
                        <label for="annoRitiro">Anno Ritiro</label>
                        <input type="number" id="annoRitiro" name="annoRitiro" value="${setLego.annoRitiro != 0 ? setLego.annoRitiro : ''}" min="1950" max="2026" placeholder="Lasciare vuoto se non è stato ritirato">
                    </div>
                    <div class="filtro-gruppo">
                        <label for="quantita">Quantità in Magazzino*</label>
                        <input type="number" id="quantita" name="quantita" value="${not empty setLego ? setLego.quantitaMagazzino : 1}" min="0" required>
                    </div>
                </div>
                
                <div>
                    <div class="filtro-gruppo">
                        <label for="descrizione">Descrizione del Set*</label>
                        <textarea id="descrizione" name="descrizione" rows="4" required>${setLego.descrizione}</textarea>
                    </div>
                </div>

                <div class="filtro-gruppo">
                    <label for="immagine">Immagine Copertina (.jpg)</label>
                    <input type="file" id="immagine" name="immagine" accept="image/jpeg">
                    <c:if test="${not empty setLego}">
                        <p>Se non carichi nulla, verrà mantenuta l'immagine attuale.</p>
                    </c:if>
                </div>

                <div>
                    <a href="${pageContext.request.contextPath}/admin/dashboardServlet" class="btn-azione btn-secondario">Annulla</a>
                    <button type="submit" class="btn-azione btn-primario">${not empty setLego ? 'Salva Modifiche' : 'Aggiungi Set'}</button>
                </div>

            </form>
        </section>
    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>