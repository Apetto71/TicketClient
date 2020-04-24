package it.aaperio.TicketClient.Model;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.log4j.Logger;


public class Model {

	private static Model model = null;
	private static Logger logger; 
	
	private static Configuration config;
	private boolean connected = false;
	private Connection connection;
	private ConcurrentLinkedDeque<String> codamsginput;
	
	// Costruttore della classe singleton
	private Model() {
		config = Configuration.getConfiguration();
		logger = Logger.getLogger(Model.class);
		logger.info("Istanzio la coda di messaggi in input");
		codamsginput = new ConcurrentLinkedDeque<String>();
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
		
		// Legge una stringa dal buffer in input e la mette nella coda di lavorazione
		public String receiveString() {
			String buf;
			try {
				buf = (String) connection.getIn().readObject();
			} catch (IOException e1) {
				logger.error("Errore in lettura dalla socket" , e1);
				buf = "Errore in lettura";
			} catch (ClassNotFoundException e2) {
				logger.error("Errore in lettura, mi aspettavo una stringa è arrivato qualcos altro", e2);
				buf = "Errore in lettura";
			} 
			this.codamsginput.add(buf);
			return buf;
		}

		public boolean sendString(String s) {
			boolean c = false;
			logger.info("Invio messaggio al server " + s);
			try {
				connection.getOut().flush();
				connection.getOut().writeBytes(s);
				c = true;
			} catch (IOException e) {
				c = false;
				logger.error("Errore in scrittura sulla socket", e);
			}
			return c;
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
		
		
}