package it.aaperio.TicketClient.Model;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import it.aaperio.ticketserver.model.Messaggio ;
import org.apache.log4j.Logger;


public class Model {

	private static Model model = null;
	private static Logger logger; 
	
	private static Configuration config;
	private boolean connected = false;
	private Connection connection;
	private Queue<Messaggio> codamsginput;
	
	// Costruttore della classe singleton
	private Model() {
		config = Configuration.getConfiguration();
		logger = Logger.getLogger(Model.class);
		logger.info("Istanzio la coda di messaggi in input");
		codamsginput = new LinkedBlockingQueue<Messaggio>() ;
	}
		
	// Costruttore Singleton
	public static Model getModel() {
		
		Model m;
			if (model == null)	{
				m = new Model();
				model = m;
				logger.debug("Instanziamento della classe Model");
			} else {m = model;}
			
			
			return m;
		}
		
		// Connetto il client con il server e ritorna il buon esito dell'operazione nella variabile connected
		public void StartConnection(String host, int port) {
			
			// Lancia la classe connection per attivare la connessione con il server
			logger.info("Preparo la classe connection per attivare la connessione con il server");
			connection = new Connection(host, port);
			
			// se la connessione va a buon fine imposta la variabile connected a true
			logger.debug("Imposto la variabile di stato connected = " + connection.isConnected());
			this.connected = connection.isConnected();
			if (this.connected) {
				connection.start();
			}
			
		}
		
		/**
		 * Invio di un messaggio al server correttamente compilato con il sessionId corrente
		 * @param m: Messaggio da inviare
		 * @return: Ritorna true se l'invio è andato a buon fine, altrimenti false
		 */
		public boolean sendMsg(Messaggio m) {
			boolean c = false;
			m.setSessionId(connection.getSessionId());
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
			this.codamsginput.add(msg) ;
			logger.info ("Messaggio aggiunto alla coda di lavorazione: " + msg.toString()) ;
		}
		

		public void closeConnection() {
			connection.closeSocket();
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
		 * @return the codamsginput
		 */
		public Queue<Messaggio> getCodamsginput() {
			return codamsginput;
		}

		/**
		 * @param codamsginput the codamsginput to set
		 */
		public void setCodamsginput(Queue<Messaggio> codamsginput) {
			this.codamsginput = codamsginput;
		}
		
		
		
		
}