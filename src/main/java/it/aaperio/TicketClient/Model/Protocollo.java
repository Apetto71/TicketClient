package it.aaperio.TicketClient.Model;

/* Thread indipendente che prende un nuovo messaggio dalla coda, lo elabora ed esegue la richiesta del client
* Nel caso sia necessaria una risposta la invia
* 
*/
import org.apache.log4j.Logger;

import it.aaperio.ticketserver.model.Messaggio;
import it.aaperio.ticketserver.model.User;

/**
* @author aaperio 
* Gestisce le richieste arrivate dai client una alla volta, analizzandone il contenuto 
* e provvede a rispondere al client 
* se la richiesta conporta il salvataggio di una modifica, si occupa di comunicare 
* la modifica a tutti i client
*/
public class Protocollo extends Thread {
	
	Logger logger = Logger.getLogger(Protocollo.class) ;
	Messaggio msg = new Messaggio()	;
	Model m = Model.getModel()  ; 
	private static int protocolNumber = 0 ;
	
	

	/**
	 * 
	 */
	public Protocollo() {
		protocolNumber = protocolNumber +1 ;
		logger.debug("Apro il Thread Protocollo: " + this.currentThread() + " - Numero: " + protocolNumber); 
	}

	public void run() {
		
		while (true) {
			msg = m.pollMsgFromQueue() ;
			logger.debug("Prelevato dalla coda nuovo messaggio: " + msg.toString()) ;
			gestisciMsg();
		}
	}

	/**
	 * 
	 */
	private void gestisciMsg() {
		switch (this.msg.getComando()) {
		case AGGIORNA_LISTA_TICKET:
			break;
		case CONNECT:
			break;
		case DISCONNECT:
			break;
		case POPUP:
			break;
		case RICERCA:
			break;
		case USER:
			System.out.println(msg.getParametro().getClass());
			User u = new User( (User) msg.getParametro()) ; 
			logger.info ("Ricevuto nuovo User: " + u.toString()) ;
			break;
		default:
			break;
		}

		
		
	}
	

	
}
