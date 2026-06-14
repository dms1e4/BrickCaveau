<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Privacy Policy - BrickCaveau</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/privacy.css">
</head>
<body>
    <jsp:include page="/fragments/header.jsp" />

    <main class="policy-container">
        <h1>Informativa sulla Privacy</h1>
        <p class="policy-date">Ultimo aggiornamento: Giugno 2026</p>

        <p>Benvenuto su <strong>BrickCaveau</strong>. 
        La protezione dei tuoi dati personali è importante per noi tanto quanto è per te trovare il pezzo mancante per completare il tuo set LEGO preferito. 
        In questa pagina ti spieghiamo in modo trasparente quali dati raccogliamo, come li utilizziamo e quali sono i tuoi diritti, nel rispetto del Regolamento Generale sulla Protezione dei Dati (GDPR - Reg. UE 2016/679).</p>

        <h2>1. Titolare del Trattamento</h2>
        <p>Il Titolare del trattamento dei dati raccolti tramite questo sito web è <strong>BrickCaveau</strong> (Progetto Universitario).
        Per qualsiasi domanda o richiesta relativa alla gestione dei tuoi dati personali, puoi contattarci ad uno degli indirizzi e-mail presenti nella pagina <a href="contatti.jsp">Contatti</a>.</p>

        <h2>2. Quali dati raccogliamo</h2>
        <p>Durante la tua navigazione e interazione con il nostro e-commerce, raccogliamo le seguenti categorie di dati:</p>
        <ul>
            <li><strong>Dati di registrazione e profilo:</strong> Nome, cognome, indirizzo email e password (salvata con cifratura SHA-256) forniti durante la creazione dell'account.</li>
            <li><strong>Dati di spedizione e fatturazione:</strong> Indirizzo fisico, città, CAP e dettagli necessari per l'evasione degli ordini.</li>
            <li><strong>Dati di navigazione:</strong> Indirizzo IP, tipo di browser, e le pagine visitate all'interno del nostro catalogo.</li>
            <li><strong>Dati relativi agli ordini:</strong> Storico degli acquisti, set LEGO inseriti nel carrello e recensioni rilasciate sui prodotti.</li>
        </ul>

        <h2>3. Perché raccogliamo i tuoi dati (Finalità)</h2>
        <p>Utilizziamo i tuoi dati esclusivamente per i seguenti scopi:</p>
        <ul>
            <li>Permetterti di creare e gestire il tuo account personale su BrickCaveau.</li>
            <li>Elaborare e spedire gli ordini effettuati, inviandoti le relative comunicazioni di servizio (es. conferma d'ordine).</li>
            <li>Gestire il carrello degli acquisti e memorizzare le tue preferenze.</li>
            <li>Prevenire frodi e garantire la sicurezza della piattaforma.</li>
        </ul>

        <h2>4. Come proteggiamo il nostro Caveau (Sicurezza)</h2>
        <p>I tuoi dati sono conservati in database sicuri. Adottiamo misure di sicurezza tecniche e organizzative avanzate, tra cui la cifratura delle password e la protezione da attacchi informatici (come le SQL Injection), per garantire che le tue informazioni personali non vadano perse, rubate o accessibili a persone non autorizzate.</p>

        <h2>5. Condivisione dei dati</h2>
        <p>I tuoi dati personali <strong>non</strong> saranno mai venduti a terzi. Potremmo condividere alcune informazioni strettamente necessarie solo con fornitori di servizi di cui ci avvaliamo per il funzionamento del sito (ad esempio, i corrieri per la consegna dei set LEGO acquistati o i gateway di pagamento per l'elaborazione delle transazioni).</p>

        <h2>6. I tuoi Diritti</h2>
        <p>Ai sensi del GDPR, hai il diritto in qualunque momento di:</p>
        <ul>
            <li><strong>Accedere</strong> ai tuoi dati personali e richiederne una copia.</li>
            <li><strong>Rettificare</strong> dati inesatti o aggiornare quelli incompleti tramite la tua area personale.</li>
            <li><strong>Cancellare</strong> il tuo account e i dati ad esso associati (Diritto all'oblio).</li>
            <li><strong>Opporti</strong> al trattamento dei tuoi dati per finalità di marketing.</li>
        </ul>
        <p>Per esercitare questi diritti, puoi contattarci via email.</p>

        <h2>7. Utilizzo dei Cookie</h2>
        <p>BrickCaveau utilizza cookie tecnici per garantire il corretto funzionamento del sito (mantenere attiva la tua sessione dopo il login, ricordare i prodotti nel tuo carrello). Non utilizziamo cookie di profilazione di terze parti senza il tuo esplicito consenso.</p>
        
        <hr>
        <p class="disclaimer">
            <em><strong>Nota bene:</strong> BrickCaveau è un progetto didattico universitario simulato. Questa Informativa sulla Privacy è redatta a scopo accademico per soddisfare i requisiti del corso di Tecnologie Software per il Web e non rappresenta un documento legalmente vincolante per una reale attività commerciale.</em>
        </p>

    </main>

    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>