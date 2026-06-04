-- db relazionale per brickcaveau

DROP SCHEMA IF EXISTS BrickCaveau;
CREATE SCHEMA BrickCaveau;
USE BrickCaveau;

CREATE TABLE Utente(
	ID_Utente INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(30) NOT NULL,
    Cognome VARCHAR(30) NOT NULL,
    Email VARCHAR(150) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Telefono VARCHAR(15) NOT NULL UNIQUE,
    is_Admin BOOLEAN NOT NULL
);

CREATE TABLE Set_Lego(
	Codice_Set INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Anno_Uscita INT NOT NULL,
    Anno_Ritiro INT,
    N_Pezzi INT NOT NULL,
    Descrizione VARCHAR(1000) NOT NULL,
    Prezzo DECIMAL(10,2) NOT NULL,
    Tema VARCHAR(50) NOT NULL,
    Quantita_Magazzino INT NOT NULL
);

CREATE TABLE Indirizzo(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    N_Civico VARCHAR(10) NOT NULL,
    Via VARCHAR(75) NOT NULL,
    Citta VARCHAR(50) NOT NULL,
    CAP VARCHAR(5) NOT NULL,
    Provincia VARCHAR(15) NOT NULL,
    Nazione VARCHAR(20) NOT NULL,
    Utente_ID INT NOT NULL,
    
    FOREIGN KEY (Utente_ID)
    REFERENCES Utente(ID_Utente)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE MetodoPagamento(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Scadenza DATE, -- solo cc
    Tipo VARCHAR(20) NOT NULL,
    Ultime4Cifre CHAR(4), -- solo cc
    Utente_ID INT NOT NULL,
    
    FOREIGN KEY (Utente_ID)
    REFERENCES Utente(ID_Utente)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Immagine(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    URL VARCHAR(100) NOT NULL,
    is_Principale BOOLEAN NOT NULL,
    Codice_Set INT NOT NULL,
    
    FOREIGN KEY (Codice_Set)
    REFERENCES Set_Lego(Codice_Set)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Ordine(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Totale DECIMAL(10,2) NOT NULL,
    Stato_Spedizione VARCHAR(40) NOT NULL,
    Data_Acquisto DATE NOT NULL,
    Utente_ID INT NOT NULL,
    Indirizzo_ID INT,
    
    FOREIGN KEY (Utente_ID)
    REFERENCES Utente(ID_Utente)
		ON DELETE CASCADE
        ON UPDATE CASCADE,

	FOREIGN KEY (Indirizzo_ID)
    REFERENCES Indirizzo(ID)
		ON DELETE SET NULL -- se l'utente cancella indirizzo l'ordine non viene cancellato
        ON UPDATE CASCADE
);

CREATE TABLE Dettaglio_Ordine(
	ID INT AUTO_INCREMENT PRIMARY KEY,
	Ordine_ID INT NOT NULL,
    Codice_Set INT,
    Quantita INT NOT NULL,
    Prezzo_Acquisto DECIMAL(10,2) NOT NULL,
    IVA DECIMAL(4,2) NOT NULL,
    
    
    FOREIGN KEY (Ordine_ID)
    REFERENCES Ordine(ID)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	FOREIGN KEY (Codice_Set)
    REFERENCES Set_Lego(Codice_Set)
		ON DELETE SET NULL -- se si elimina il prodotto, non va perso l'ordine
        ON UPDATE CASCADE
);

CREATE TABLE Wishlist(
	Utente_ID INT NOT NULL,
    Codice_Set INT NOT NULL,
    
    PRIMARY KEY (Utente_ID, Codice_Set),
    
    FOREIGN KEY (Utente_ID)
    REFERENCES Utente(ID_Utente)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	FOREIGN KEY (Codice_Set)
    REFERENCES Set_Lego(Codice_Set)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Recensione(
	Utente_ID INT NOT NULL,
    Codice_Set INT NOT NULL,
    Rating INT CHECK (Rating >= 1 AND Rating <= 5),
    Testo VARCHAR(750) NOT NULL,
    Data_Recensione DATE NOT NULL,
    PRIMARY KEY (Utente_ID, Codice_Set),
    
    FOREIGN KEY (Utente_ID)
    REFERENCES Utente(ID_Utente)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
        
	FOREIGN KEY (Codice_Set)
    REFERENCES Set_Lego(Codice_Set)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);








