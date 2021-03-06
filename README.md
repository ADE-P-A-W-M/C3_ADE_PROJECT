# C3_ADE
## _Progetto delle 3 C, |C|entro |C|ommerciale in |C|entro_

________________________
## Introduzione:
<pre>Il progetto è un applicazione Web che ha come scopo quello di ravvivare l'economia nel centro storico di un paese.
Permette ad ogni negozio registrato alla piattaforma di vendere i suoi prodotti fisicamente ad un cliente,
il quale potrà avere la possibilità di ritirare subito la merce o di scegliere un altro negozio più comodo 
a seconda delle sue esigenze, peril ritiro. Il trasporto sarà compiuto da un corriere affiliato.</pre>
________________________

## Attori coinvolti nel sistema:
<pre><ul><li><h3>Cliente:</h3>rappresenta un utente registrato nel sistema. E' in grado di svolgere azioni quali la ricerca di un prodotto,
la visualizzazione delle promozioni e infine la consultazione dello storico dei suoi acquisti nei vari negozi;</li>
<li><h3>Addetto:</h3>rappresenta un utente con il ruolo di AddettoNegozio. E' in grado di svolgere azioni quali checkout, consegna vendita,
consulta inventario, assegnazione carta;</li>
<li><h3>Commerciante:</h3>rappresenta un utente con il ruolo di Commerciante. E' in grado di svolgere azioni quali gestione dell’inventario,
gestione delle promozioni, assunzione dei corrieri e di addetti;</li>
<li><h3>Corriere:</h3>rappresenta un utente con il ruolo di Corriere. E' in grado di svolgere azioni quali prelevare e consegnare merci e
in modo da gestire il proprio inventario;</li>
<li><h3>Amministratore:</h3>rappresenta un utente con il ruolo di Amministratore. E' in grado di svolgere azioni quali registrare un nuovo
commerciante, con il relativo negozio, e un nuovo corriere con la relitiva ditta;</li></ul></pre>
________________________

## Casi d'uso principali: 
<pre><ul><li><h3>User:</h3><ul><li><h4>Registrazione nella piattaforma:</h4>Il nuovo utente inserirà le credenziali nome,cognome, email e password per registrarsi.
Se la registrazione va a buon fine l'utente assumerà il ruolo di Clientee ed eseguire le operazioni ad esso corrispondenti.</li>
<li><h4>Accesso nella piattaforma:</h4>L'accesso alla piattaforma avverrà inserendo email e password. Se l'accesso andrà a buon fine si 
verrà reindirizzati nella pagina relativa al ruolo cliente. Se si dispone di altri ruoli, l'utente cambiare i ruoli.</li></ul></li>
<li><h3>Cliente:</h3><ul><li><h4>Consulta acquisti:</h4>Il cliente visualizza tutti gli acquisti effettuati con le informazioni relativi all'acquisto</li>
<li><h4>Ricerca prodotto:</h4>Il cliente cerca un determinato prodotto tramite il nome e visualizza la lista dei negozi che 
lo vendono.</li>
<li><h4>Ricerca promozioni:</h4>Il cliente visualizza le promozioni attive in ogni negozio ed può filtrarle in base alla categoria.</li></ul></li>
<li><h3>Addetto:</h3><ul><li><h4>Checkout:</h4><ul><li>L’addetto identifica le merci portate alla cassa dal cliente tramite l’id</li><li>Viene eventualmente applicato uno sconto relativo alla carta sul prezzo totale</li><li>Il cliente sceglie se ritirare l’acquisto subito oppure farlo spedire nel luogo di ritiro che preferisce(domicilio o negozio)</li><li>La vendita è completata</li></ul></li>
<li><h4>Assegnazione carta:</h4>L'addetto crea una nuova carta assegnandola al cliente specificando anche il tipo di sconto da 
attribuire alla carta</li>
<li><h4>Consulta inventario:</h4>L'addetto visualizza la merce presente nell'inventario del negozio e visionare le informazioni di 
ognuna di essa.</li>
<li><h4>Consegna vendita:</h4>L'addetto consegna la vendita che è stata assegnata al negozio, dopo aver verificato l'identità del cliente
attraverso l'email</li></ul></li>
<li><h3>Commerciante:</h3><ul><li><h4>Gestione promozioni:</h4>Il commerciante visualizza tutte le promozioni attive,può in seguito scegliere se aggiungere 
nuove promozioni o rimuovere quelle esistenti.</li>
<li><h4>Gestione inventario:</h4>Il commerciante accede all'inventario e decide se aggiungere o rimuovere una o piu merci dall'inventario.</li>
<li><h4>Gestione corrieri:</h4>Il commerciante visualizza la lista dei corrieri disponibili all'ingaggio e decide quali affiliare
al proprio negozio.</li>
<li><h4>Assunzione addetto:</h4>Il commerciante cerca un utente registrato alla piattaforma attraverso l'email e lo assume 
come addetto nel suo negozio</li></ul></li>
<li><h3>Corriere:</h3><ul><li><h4>Ritira merce:</h4>Il corriere ritira la merce nel negozio comunicato .</li>
<li><h4>Consegna merce:</h4>Il corriere consegna la merce nel domicilio o negozio specificato.</li>
<li><h4>Consulta inventario:</h4>Il corriere visualizza le merci in attesa di ritiro,da consegnare e consegnate.</li></ul></li>
<li><h3>Amministratore:</h3><ul><li><h4>Registrazione negozio:</h4>L'amministratore ricerca un utente registrato alla piattaforma, gli assegna il ruolo di commerciante 
e crea un negozio utilizzando le informazioni riferite.</li>
<li><h4>Registrazione corriere:</h4>L'amministratore ricerca un utente registrato alla piattaforma e gli assegna il ruolo di corriere 
utilizzando le informazioni riferite.</li></ul></li></ul></pre>
________________________

## Tecnologie utilizzate: 
<pre><ul><li><h4>Back-end:</h4> è stata realizzato in Java utilizzando il framework Spring. Le Java Persistence API messe a disposizione 
da Spring hanno permesso di utilizzare il database con motore MySQL. Per la configurazione del progetto abbiamo utilizzato Maven.</li>
<li><h4>Front-end:</h4>è stata realizzato in formato HTML e CSS con l’aiuto del framework Bootstrap e del generatore di template Thymeleaf.</li></ul></pre>
________________________

## Partecipanti al progetto: 
<pre><ul><li><h4>Daniele Pelosi:</h4>daniele.pelosi@studenti.unicam.it</li>
<li><h4>Edoardo Iommi:</h4>edoardo.iommi@studenti.unicam.it</li>
<li><h4>Avdil Mehmeti:</h4>avdil.mehmeti@studenti.unicam.it</li></ul></pre>
