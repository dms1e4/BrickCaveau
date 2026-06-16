function eseguiControlloLuhn(event) {
    const inputNumero = document.getElementById("numeroCarta");
    const msgErrore = document.getElementById("errore-luhn");
    const numero = inputNumero.value.trim();

    if (!validaLuhn(numero)) {
        // se è falso blocco invio di dati
        event.preventDefault(); 
        
        msgErrore.classList.add("mostra-errore");
        inputNumero.style.borderColor = "var(--errore)";
        
        return false;
    }

    // se è vero, passo a servlet
    msgErrore.classList.remove("mostra-errore");
    return true;
}

// algoritmo di luhn
function validaLuhn(num) {
    num = num.replace(/\s+/g, '');
    
    // controllo che siano 16 numeri
    if (!/^\d{16}$/.test(num)) return false; 

    let somma = 0;
    let isPari = false; 

    for (let i = num.length - 1; i >= 0; i--) {
        let cifra = parseInt(num.charAt(i), 10);
        if (isPari) {
            cifra *= 2; 
            if (cifra > 9) cifra -= 9;
        }
        somma += cifra;
        isPari = !isPari; 
    }
    return (somma % 10) === 0;
}

// resetto l'errore quando l'utente clicca per correggere
document.addEventListener("DOMContentLoaded", function() {
    const inputNum = document.getElementById("numeroCarta");
    const msgErrore = document.getElementById("errore-luhn");
    
    if (inputNum) {
        inputNum.addEventListener("input", function() {
            msgErrore.classList.remove("mostra-errore");
            inputNum.style.borderColor = "";
        });
    }
});