function toggleWishlist(buttonElement) {
    const setId = buttonElement.getAttribute('data-id');
    
    const formData = new URLSearchParams();
    formData.append("codiceSet", setId);

    fetch(contextPath + "/api/wishlist", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Errore di rete durante la comunicazione col server.");
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            if (data.inWishlist) {
                // comportamento nel catalogo: attiva la stella
                buttonElement.classList.add("active"); 
            } else {
                buttonElement.classList.remove("active"); 
                
                // per wishlist.jsp
                const card = buttonElement.closest('.card-prodotto');
                
                if (window.location.pathname.includes("WishlistServlet") || document.title.includes("Wishlist")) {
                    card.style.transition = "all 0.5s ease";
                    card.style.opacity = "0";
                    card.style.transform = "scale(0.8)";
                    
                    setTimeout(() => {
                        card.remove();
                        
                        // se abbiamo rimosso l'ultimo elemento, ricarichiamo la pagina per mostrare "lista vuota"
                        const rimanenti = document.querySelectorAll('.card-prodotto');
                        if (rimanenti.length === 0) {
                            window.location.reload();
                        }
                    }, 500);
                }
            }
        } else {
            alert("Si è verificato un errore: " + data.message);
            if (data.message === "Devi effettuare il login") {
                window.location.href = contextPath + "/login.jsp";
            }
        }
    })
    .catch(error => {
        console.error("Errore Fetch API:", error);
        alert("Impossibile connettersi al server. Riprova più tardi.");
    });
}