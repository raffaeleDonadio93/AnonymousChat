# AnonymousChat on P2P Networks

Progetta e sviluppa un'API di chat anonima basata su rete P2P.
Ogni peer può inviare messaggi nella chat room pubblica in modo anonimo.
Il sistema consente agli utenti di creare una nuova stanza, unirsi alla stanza, lasciare una stanza e inviare un messaggio in una stanza. 
Come descritto nell'API Java di AnonymousChat. 
Progetta e sviluppa un'API di chat anonima basata su rete P2P. 
Ogni peer può inviare messaggi nella chat room pubblica in modo anonimo. 
Il sistema consente agli utenti di creare una nuova stanza, unirsi in una stanza, lasciare una stanza e inviare un messaggio in una stanza. Come descritto nell'API Java di AnonymousChat.

##Funzionalità:
•	Creare una stanza

•	Collegarsi a una stanza

•	Inviare un messaggio alla stanza

•	Lasciare una stanza

######•	Lasciare la rete (funzionalità aggiuntiva)


##Protocollo di base :
Data una rete p2p composta da n nodi l’obiettivo è inviare messaggi in anonimato. 
#####Quando un peer deve inviare un messaggio a un altro peer nella rete, non lo invia direttamente ma lancia una monetina e con il 50% di probabilità invia il messaggio direttamente, con 50% inoltra il messaggio a un altro peer, quest’ultimo lancerà nuovamente una monetina e con 50 % di probabilità verrà inoltrato, con 50% di probabilità invierà direttamente il messaggio al destinatario. Quando un nodo deve inoltrare il messaggio sceglierà in modo casuale un nodo connesso alla stanza. 
Quindi dati n peer connessi a una stanza, ogni nodo avrà una probabilità 1/n di essere scelto per il compito. Questo processo verrà iterato fin tanto che un peer nella rete non otterrà testa e invierà direttamente il messaggio. 

#####La correttezza del protocollo consiste nell’inviare oltre al messaggio anche il destinatario incapsulando questi dati nell’oggetto Messaggio 
che avrà al seguente struttura:
Messaggio: destinatario, messaggio, nome della stanza, time stamp .

Questo garantisce che in un certo numero di step limitato il messaggio arriverà a destinazione(in media sono necessari due inoltri affinchè il messaggio venga consegnato). 
Ogni peer avrà una componente sempre in ascolto, nel momento in cui ascolta un messaggio, se il destinatario è proprio lui allora fare qualche cosa altrimenti in modo  probabilistico inoltra. Abbiamo appena visto come effettuare l’invio di un messaggio in modo anonimo tra due peer della rete, poiché la chat prevede l’uso di stanze per lo scambio di messaggi, quindi data una stanza, ogni stanza vedrà un certo numero di peer collegati, nel momento in cui un nodo invia un messaggio, dovrà ripetere l’operazione appena visto per tutti i peer collegati alla stanza. L’anonimato è garantito perché non è il sorgente del messaggio che invia il messaggio direttamente a destinazione, ma può essere lui come un qualsiasi altro nodo collegato alla stanza.



##Sviluppi futuri:
Amplificare il protocollo di base per gestire il caso in cui un nodo della rete si disconnette dalla rete in modo anomalo senza segnalare l'uscita.



##Requisiti minimi:
Per la compilazione è necessario avere almeno una versione di java 1.8 


