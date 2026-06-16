
document.addEventListener("DOMContentLoaded", function() {
    const formCarta = document.getElementById("form-carta");
    const inputNumero = document.getElementById("numeroCarta");
    const msgErrore = document.getElementById("errore-luhn");

    if (formCarta) {
        formCarta.addEventListener("submit", function(e) {
            const numero = inputNumero.value.trim();

            if (!validaLuhn(numero)) {
                // fermo l'invio dei dati
                e.preventDefault(); 
                
                // mostro l'errore
                msgErrore.style.display = "block";
                inputNumero.style.borderColor = "var(--errore)";
                inputNumero.style.boxShadow = "0 0 0 2px rgba(220, 38, 38, 0.2)";
            } else {
                // se è valido, nascondo l'errore
                msgErrore.style.display = "none";
                inputNumero.style.borderColor = "var(--successo)";
            }
        });
    }


    function validaLuhn(num) {
		
		//rimuovo spazi e verifico che contenga solo numeri
        num = num.replace(/\s+/g, '');
        
        if (!/^\d+$/.test(num)) return false;

        let somma = 0;
        let isPari = false; // booleano per raddoppiare una cifra si e una no

        // scorro le cifre dall'ultima a destra fino a sinistra
        for (let i = num.length - 1; i >= 0; i--) {
            let cifra = parseInt(num.charAt(i), 10);

            if (isPari) {
                cifra *= 2; // raddoppio ogni seconda cifra
                
                // se il doppio supera 9, si sottrae 9
                if (cifra > 9) {
                    cifra -= 9;
                }
            }

            somma += cifra;
            isPari = !isPari; // inverte il booleano per il ciclo successivo
        }

        // il numero di carta sarà valido solo se è multiplo di 10
        return (somma % 10) === 0;
    }
});