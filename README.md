# BrickCaveau 

BrickCaveau è un sito e-commerce che vende set Lego ritirati e non, mirando principalmente ai collezionisti.
Il progetto è stato realizzato durante il corso di Tecnologie Software per il Web dell'a. a. 2025/2026.

## Tecnologie Utilizzate
* **Linguaggio:** Java 11+
* **Backend:** Servlet, JSP (JavaServer Pages), JSTL
* **Database:** MySQL 8.x
* **Frontend:** HTML5, CSS3, JavaScript
* **Server:** Apache Tomcat 9.0
* **IDE:** Eclipse

---

## Istruzioni per l'installazione e l'avvio locale

Per eseguire correttamente il sito Web in locale sul tuo ambiente di sviluppo, segui attentamente questi passaggi.

### 1. Prerequisiti
Assicurati di aver installato:
* [Java JDK 11](https://www.oracle.com/java/technologies/downloads/)
* [Eclipse IDE for Enterprise Java and Web Developers](https://www.eclipse.org/downloads/packages/)
* [Apache Tomcat 9.0](https://tomcat.apache.org/download-90.cgi)
* [MySQL Server](https://dev.mysql.com/downloads/mysql/) e MySQL Workbench

### 2. Setup del Database (MySQL)
1. Apri MySQL Workbench e connettiti al tuo server locale (generalmente su `localhost:3306`).
2. Apri ed esegui lo script di creazione schema `database.sql` presente nella root di questo repository.
3. Apri ed esegui lo script di popolamento del database `queryins.sql`.

### 3. Importazione del Progetto in Eclipse
1. Apri Eclipse.
2. Vai su **File > Import... > General > Existing Projects into Workspace**.
3. Seleziona la cartella del progetto `BrickCaveau` e clicca su *Finish*.
4. Assicurati che Eclipse stia puntando al server corretto:
   * Clicca col tasto destro sul progetto -> **Build Path > Configure Build Path**.
   * Nella tab *Libraries*, verifica che sia presente la *Server Runtime* di Apache Tomcat v9.0.

### 4. Configurazione del Connection Pool (DataSource)
Il progetto utilizza un DataSource JNDI per gestire le connessioni al database in modo efficiente.
1. All'interno del progetto in Eclipse, naviga in `src/main/webapp/META-INF/`.
2. Assicurati che sia presente il file `context.xml` con il seguente contenuto (modifica `username` e `password` con le credenziali del tuo MySQL locale):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource name="jdbc/BrickCaveau" 
              auth="Container" 
              type="javax.sql.DataSource" 
              maxTotal="20" 
              maxIdle="10" 
              maxWaitMillis="-1" 
              username="root" 
              password="tua_password_mysql" 
              driverClassName="com.mysql.cj.jdbc.Driver" 
              url="jdbc:mysql://localhost:3306/BrickCaveau?serverTimezone=UTC"/>
</Context>
