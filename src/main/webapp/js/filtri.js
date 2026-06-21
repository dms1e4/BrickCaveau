document.addEventListener("DOMContentLoaded", function() {
    
    // catturo gli elementi dei filtri
    const checkboxesTema = document.querySelectorAll(".filtro-tema");
    const sliderUscita = document.getElementById("sliderUscita");
    const spanValoreUscita = document.getElementById("valoreUscita");
    const sliderRitiro = document.getElementById("sliderRitiro");
    const spanValoreRitiro = document.getElementById("valoreRitiro");
    const sliderPezzi = document.getElementById("sliderPezzi");
    const spanValorePezzi = document.getElementById("valorePezzi");
    
    // catturo le condizioni di ordinamento
    const selectOrdinamento = document.getElementById("ordinamento");
    const grigliaCatalogo = document.querySelector(".grid-catalogo");
    
	// carico i bottoni e gli elementi presenti nella pagina
    const btnReset = document.getElementById("btnReset");
    const cards = document.querySelectorAll(".card-prodotto");
    const boxNessunRisultato = document.getElementById("nessun-risultato");

    // filtri live (mostra/nascondi)
    function applicaFiltri() {
        const temiSelezionati = Array.from(checkboxesTema).filter(cb => cb.checked).map(cb => cb.value);
        const minUscitaVal = parseInt(sliderUscita.value);
        const maxRitiroVal = parseInt(sliderRitiro.value);
        const maxPezziVal = parseInt(sliderPezzi.value);
        
        spanValoreUscita.textContent = minUscitaVal;
        spanValorePezzi.textContent = maxPezziVal;
        
        if (maxRitiroVal === 2026) {
            spanValoreRitiro.textContent = "Tutti";
        } else {
            spanValoreRitiro.textContent = maxRitiroVal;
        }

        let prodottiVisibili = 0;

        cards.forEach(card => {
            const cardTema = card.getAttribute("data-tema");
            const cardAnnoUscita = parseInt(card.getAttribute("data-anno-uscita"));
            const cardAnnoRitiro = parseInt(card.getAttribute("data-anno-ritiro"));
            const cardPezzi = parseInt(card.getAttribute("data-pezzi"));

            const matchTema = temiSelezionati.length === 0 || temiSelezionati.includes(cardTema);
            const matchAnnoUscita = cardAnnoUscita >= minUscitaVal;
            
            let matchAnnoRitiro = false;
            if (maxRitiroVal === 2026) {
                matchAnnoRitiro = true; 
            } else {
                matchAnnoRitiro = (cardAnnoRitiro > 0 && cardAnnoRitiro <= maxRitiroVal);
            }

            const matchPezzi = cardPezzi <= maxPezziVal;

            if (matchTema && matchAnnoUscita && matchAnnoRitiro && matchPezzi) {
                card.style.display = "flex";
                prodottiVisibili++;
            } else {
                card.style.display = "none";
            }
        });

        if (prodottiVisibili === 0) {
            boxNessunRisultato.style.display = "block";
        } else {
            boxNessunRisultato.style.display = "none";
        }
    }

    // funzione di ordinamento delle card prodotto
    function ordinaCards() {
        if (!grigliaCatalogo) return; 

        // array con tutte le card
        let cardsArray = Array.from(grigliaCatalogo.querySelectorAll('.card-prodotto'));
        const criterio = selectOrdinamento.value;

        // ordino l'array
        cardsArray.sort((a, b) => {
            if (criterio === 'nome-asc') {
                return a.dataset.nome.localeCompare(b.dataset.nome);
            } else if (criterio === 'nome-desc') {
                return b.dataset.nome.localeCompare(a.dataset.nome);
            } else if (criterio === 'codice-asc') {
                return parseInt(a.dataset.codice) - parseInt(b.dataset.codice);
            } else if (criterio === 'codice-desc') {
                return parseInt(b.dataset.codice) - parseInt(a.dataset.codice);
            } else if (criterio === 'pezzi-asc') {
                return parseInt(a.dataset.pezzi) - parseInt(b.dataset.pezzi);
            } else if (criterio === 'pezzi-desc') {
                return parseInt(b.dataset.pezzi) - parseInt(a.dataset.pezzi);
            }
            return 0;
        });

        // svuoto la griglia precedente e aggiungo le card nell'ordine corretto
        grigliaCatalogo.innerHTML = '';
        cardsArray.forEach(card => grigliaCatalogo.appendChild(card));
    }


    // event listeners
    checkboxesTema.forEach(cb => cb.addEventListener("change", applicaFiltri));
    sliderUscita.addEventListener("input", applicaFiltri);
    sliderRitiro.addEventListener("input", applicaFiltri);
    sliderPezzi.addEventListener("input", applicaFiltri);
    
    if(selectOrdinamento) {
        selectOrdinamento.addEventListener("change", ordinaCards);
    }

    // tasto "azzera filtri"
    if (btnReset) {
        btnReset.addEventListener("click", function() {
            checkboxesTema.forEach(cb => cb.checked = false);
            sliderUscita.value = 2000;
            sliderRitiro.value = 2026;
            sliderPezzi.value = 5000;
            selectOrdinamento.value = "nome-asc"; // torna all'ordinamento di default
            applicaFiltri();
            ordinaCards();
        });
    }

    // di default, ordine alfabetico
    ordinaCards();
});
