document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("searchBar");
    const resultBox = document.getElementById("searchResults");
    const searchForm = document.querySelector('.search-form');

    if (!searchInput || !searchForm) return;

    const contextPath = searchForm.getAttribute("data-context") || "";
    console.log("Ricerca iniziale. Context Path rilevato: " + contextPath); // Vedrai questo nel log di F12!

    searchInput.addEventListener('keyup', function(e) {
        const query = e.target.value.trim();

        if (query.length > 0) {
            fetch(`${contextPath}/ricercaAjax?q=${encodeURIComponent(query)}`)
                .then(response => {
                    if (!response.ok) throw new Error("Errore Servlet: " + response.status);
                    return response.json();
                })
                .then(prodottiTrovati => {
                    console.log("Prodotti ricevuti dal server:", prodottiTrovati);
                    resultBox.hidden = false;
                    mostraSuggerimenti(prodottiTrovati);
                })
                .catch(error => {
                    console.error("Errore durante la Fetch AJAX:", error);
                });
        } else {
            resultBox.hidden = true;
            resultBox.innerHTML = '';
        }
    });

    document.addEventListener('click', (event) => {
        if (!searchForm.contains(event.target)) {
            resultBox.hidden = true;
        }
    });

    searchInput.addEventListener('focusin', function () {
        if (searchInput.value.trim().length > 0) {
            resultBox.hidden = false;
        }
    });

	function mostraSuggerimenti(prodotti) {
	        if (prodotti.length === 0) {
	            resultBox.innerHTML = '<div class="no-result">Nessun set trovato nel Caveau.</div>';
	            return;
	        }
	        
	        let html = prodotti.map(p => 
	            `<a href="${contextPath}/ProdottoServlet?id=${p.id}" class="suggestion-item">
	                
	                <img src="${contextPath}/images/Set/${p.id}_1.jpg" 
	                     onerror="this.src='${contextPath}/images/logo.png'" 
	                     alt="${p.nome}" 
	                     class="suggestion-img">
	                
	                <div class="suggestion-info">
	                    <span class="suggestion-title">${p.nome}</span>
	                    <span class="suggestion-code">Codice Set: ${p.id}</span>
	                </div>
	                
	            </a>`
	        ).join('');
	        
	        resultBox.innerHTML = html;
	    }
});
