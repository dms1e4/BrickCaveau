document.addEventListener("DOMContentLoaded", function() {
    const formFiltri = document.getElementById("formFiltriOrdini");
    const btnReset = document.getElementById("btnResetOrdini");
    const tabellaBody = document.getElementById("tabellaOrdiniBody");
    
    // Recuperiamo il contextPath in modo sicuro al 100% dal form HTML
    const contextPath = formFiltri.getAttribute("data-context") || "";

    // Funzione che scarica e disegna gli ordini
    function caricaOrdini(params = {}) {
        // Costruiamo l'URL in modo relativo (sicuro su ogni server)
        const url = new URL(window.location.origin + contextPath + "/admin/api/ordini");
        
        if (params.dataInizio) url.searchParams.append("dataInizio", params.dataInizio);
        if (params.dataFine) url.searchParams.append("dataFine", params.dataFine);
        if (params.utenteId) url.searchParams.append("utenteId", params.utenteId);

        fetch(url)
            .then(response => {
                // Se la Servlet non viene trovata (404) o va in crash (500), blocchiamo tutto prima di leggere il JSON!
                if (!response.ok) {
                    throw new Error("Errore Server: " + response.status);
                }
                return response.json();
            })
            .then(ordini => {
                tabellaBody.innerHTML = ""; // Pulisco

                if(ordini.length === 0) {
                    tabellaBody.innerHTML = '<tr><td class="non-trovato" colspan="5">Nessun ordine trovato.</td></tr>';
                    return;
                }

                ordini.forEach(ordine => {
                    tabellaBody.innerHTML += `
                        <tr>
                            <td>#${ordine.id}</td>
                            <td>${ordine.dataOrdine}</td>
                            <td>${ordine.utenteId}</td>
                            <td>€ ${ordine.totale.toFixed(2)}</td>
                            <td><a href="${contextPath}/admin/DettaglioOrdineAdminServlet?id=${ordine.id}" class="btn-azione btn-modifica">Vedi Dettagli</a></td>
                        </tr>
                    `;
                });
            })
            .catch(err => {
                console.error("Errore nel caricamento ordini:", err);
                tabellaBody.innerHTML = '<tr><td class="non-trovato" colspan="5" style="color:red;">Errore di connessione al server.</td></tr>';
            });
    }

    // Caricamento iniziale
    caricaOrdini(); 

    // Evento Submit Filtri
    formFiltri.addEventListener("submit", (e) => {
        e.preventDefault();
        caricaOrdini({
            dataInizio: document.getElementById("dataInizio").value,
            dataFine: document.getElementById("dataFine").value,
            utenteId: document.getElementById("utenteId").value
        });
    });

    // Evento Reset
    btnReset.addEventListener("click", () => {
        document.getElementById("dataInizio").value = "";
        document.getElementById("dataFine").value = "";
        document.getElementById("utenteId").value = "";
        caricaOrdini(); // Ricarica tutto senza filtri
    });
});
