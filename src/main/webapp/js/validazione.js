document.addEventListener("DOMContentLoaded", function() {
    
    
    const form = document.getElementById("formRegistrazione");
    const emailInput = document.getElementById("email");
    const emailFeedback = document.getElementById("emailFeedback");

    if (!form) return;

    // --- REGEX FONDAMENTALI ---
    const regexLettere = /^[a-zA-ZÀ-ÿ\s']{2,50}$/; 
    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Obbliga la presenza di @ e dominio
    const regexPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&.])[A-Za-z\d@$!%*?&.]{8,}$/; // Minimo 8 caratteri, almeno 1 lettera maiuscola, 1 numero, 1 carattere speciale
    const regexTelefono = /^[0-9]{9,10}$/; // 9-10 cifre senza prefisso
    
    /*chiamata Fetch API*/
    
    // L'evento 'blur' scatta non appena l'utente clicca fuori dal campo email
    emailInput.addEventListener("blur", function() {
        const emailValue = emailInput.value.trim();
        
        // Se il campo è vuoto, puliamo il feedback e ci fermiamo
        if (emailValue.length === 0) {
            emailFeedback.textContent = "";
            return;
        }

        // Controllo Regex PRIMA di interrogare il server
        if (!regexEmail.test(emailValue)) {
            emailFeedback.textContent = "Formato email non valido (es. manca @ o dominio).";
            emailFeedback.style.color = "#DC2626";
            return;
        }
        
        // Chiamata asincrona al server
        const url = "checkEmailServlet?email=" + encodeURIComponent(emailValue);

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Leggiamo il valore booleano 'esiste' dal JSON
                if (data.esiste) {
                    emailFeedback.textContent = "Attenzione: questa email è già registrata!";
                    emailFeedback.style.color = "#DC2626"; // feedback negativo
                } else {
                    emailFeedback.textContent = "Email disponibile!";
                    emailFeedback.style.color = "#16A34A"; // feedback positivo
                }
            })
            .catch(error => {
                console.error("Errore durante la chiamata AJAX:", error);
            });
    });

    /* validazione regex */
    
    form.addEventListener("submit", function(event) {
        
        event.preventDefault();

        document.getElementById("errorNome").textContent = "";
        document.getElementById("errorCognome").textContent = "";
        document.getElementById("errorPassword").textContent = "";
        document.getElementById("errorTelefono").textContent = "";

        if (emailFeedback.textContent.includes("non valido") || emailFeedback.textContent.includes("Errore")) {
            emailFeedback.textContent = ""; 
        }
        
        let formValido = true;
        let primoCampoErrato = null; 

        // Controllo Nome
        const inputNome = document.getElementById("nome");
        if (!regexLettere.test(inputNome.value.trim())) {
            document.getElementById("errorNome").textContent = "Inserisci un nome valido (solo lettere).";
            formValido = false;
            if (!primoCampoErrato) primoCampoErrato = inputNome;
        }

        // Controllo Cognome
        const inputCognome = document.getElementById("cognome");
        if (!regexLettere.test(inputCognome.value.trim())) {
            document.getElementById("errorCognome").textContent = "Inserisci un cognome valido (solo lettere).";
            formValido = false;
            if (!primoCampoErrato) primoCampoErrato = inputCognome;
        }

        // Controllo Email
        if (!regexEmail.test(emailInput.value.trim())) {
            emailFeedback.textContent = "Formato email non valido.";
            emailFeedback.style.color = "#DC2626";
            formValido = false;
            if (!primoCampoErrato) primoCampoErrato = emailInput;
        }

        
        // se l'email era già in uso
        if (emailFeedback.textContent.includes("Email già registrata")) {
            formValido = false;
            if (!primoCampoErrato) primoCampoErrato = emailInput;
        }

        // Controllo Password
        const inputPassword = document.getElementById("password");
        if (!regexPassword.test(inputPassword.value.trim())) {
            document.getElementById("errorPassword").textContent = "La password deve avere min 8 caratteri, 1 maiuscola, 1 numero e 1 carattere speciale.";
            formValido = false;
            if (!primoCampoErrato) primoCampoErrato = inputPassword;
        }

        // Controllo Telefono
        const inputTelefono = document.getElementById("telefono");
        if (!regexTelefono.test(inputTelefono.value.trim())) {
            document.getElementById("errorTelefono").textContent = "Inserisci un numero di telefono valido (9 o 10 cifre).";
            formValido = false;
            if (!primoCampoErrato) primoCampoErrato = inputTelefono;
        }

        
        if (!formValido) {
            primoCampoErrato.focus();
        } else {
            form.submit();
        }
    });
});
