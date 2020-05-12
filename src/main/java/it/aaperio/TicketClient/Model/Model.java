package it.aaperio.TicketClient.Model;

import java.awt.TextField;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import it.aaperio.ticketserver.model.Comandi;
import it.aaperio.ticketserver.model.Messaggio ;
import it.aaperio.ticketserver.model.User;

import org.apache.log4j.Logger;


public class Model {

	private static Model model = null;
	private static Logger logger; 
	
	private static Configuration config;
	private boolean connected = false;
	private boolean autorizzato = false ;
	private Connection connection;
	private Queue<Messaggio> codaMsgInput;
	private ExecutorService protocol = Executors.newSingleThreadExecutor();
	
	
	// Costruttore della classe singleton
	private Model() {
		
	}
		
	// Costruttore Singleton
	public static Model getModel() {
		
		Model m;
			if (model == null)	{
				m = new Model();
				model = m;
				m.Initialization() ;
				logger.debug("Instanziamento della classe Model");
			} else {m = model;}
			
			
			return m;
		}
		
	private  void Initialization() {
		config = Configuration.getConfiguration();
		logger = Logger.getLogger(Model.class);
		logger.info("Istanzio la coda di messaggi in input");
		codaMsgInput = new LinkedBlockingQueue<Messaggio>() ;
		protocol.submit(new Protocollo()) ;
	}
		
		/**
		 * Fa partire la connessione con il server indicato nel parametro. Per farlo richiama 
		 * la classe {@link Connection} che si occupa di aprire la socket e lancia il thread Connection	
		 * @param host Ip o nome macchina del server
		 * @param port porta per il collegamento TCP/IP
		 * @return
		 */
		public boolean StartConnection(String host, int port) {
			
			// Lancia la classe connection per attivare la connessione con il server
			logger.info("Preparo la classe connection per attivare la connessione con il server");
			connection = new Connection(host, port);
			connection.start();
			// Attendo che la connession vada a buonfine
			while (!connection.isConnected()) {
				try {
					Thread.sleep(10) ;
				} catch (InterruptedException e) {
					logger.error("Errore in attesa della connessione") ;
				}
			}
			logger.debug("Imposto la variabile di stato connected = " + connection.isConnected());
			this.connected = connection.isConnected() ;
			return this.connected ; 
		}
		
		/**
		 * Invio di un messaggio al server correttamente compilato con il sessionId corrente
		 * @param m: Messaggio da inviare
		 * @return: Ritorna true se l'invio è andato a buon fine, altrimenti false
		 */
		public boolean autorizza (User u) {
			boolean c = false;
			Messaggio m = new Messaggio(Comandi.USER, this.connection.getSessionId()) ; 
 			m.setParametro(u) ;
			logger.info("Invio messaggio al server " + m.toString());
			try {
				connection.getOut().flush();
				connection.getOut().writeObject(m) ;
				c = true;
			} catch (IOException e) {
				c = false;
				logger.error("Errore in scrittura sulla socket", e);
			}
			return c;
		}

		public void addMessageToQueue(Messaggio msg) {
			this.codaMsgInput.add(msg) ;
			logger.info ("Messaggio aggiunto alla coda di lavorazione: " + msg.toString()) ;
		}
		

		public void closeConnection() {
			connection.closeSocket();
			this.connected = false;
		}
		
		/**
		 * @return the codamsginput
		 */
		public Queue<Messaggio> getCodamsginput() {
			return codaMsgInput;
		}

		/**
		 * @param codamsginput the codamsginput to set
		 */
		public void setCodamsginput(Queue<Messaggio> codamsginput) {
			this.codaMsgInput = codamsginput;
		}
		
		
		public Messaggio pollMsgFromQueue() {
			Messaggio m = new Messaggio() ;
			
			try {
				m = ((LinkedBlockingQueue<Messaggio>) this.codaMsgInput).take();
			} catch (InterruptedException e) {
				logger.error("Errore nell'accesso alla coda dei messaggi" + e) ;
			} 
			return m ;
		}
		
		
		public boolean isConnected() {
			return connected;
		}

		public void setConnected(boolean connected) {
			this.connected = connected;
		}

		public static Configuration getConfig() {
			return config;
		}

		public Connection getConnection() {
			return connection;
		}

		/**
		 * @return the autorizzato
		 */
		public boolean isAutorizzato() {
			return autorizzato;
		}

		/**
		 * @param autorizzato the autorizzato to set
		 */
		public void setAutorizzato(boolean autorizzato) {
			this.autorizzato = autorizzato;
		}


}