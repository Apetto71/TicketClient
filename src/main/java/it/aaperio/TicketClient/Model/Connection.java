package it.aaperio.TicketClient.Model;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

import org.apache.log4j.*;

import it.aaperio.ticketserver.model.Messaggio;



public class Connection extends Thread {
	
	private Logger logger = Logger.getLogger(Connection.class);
	private Socket sock;

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Model model;
	private boolean connected;
	private String host;
	private int port;
	private UUID sessionId;
	
	
	public Connection(String h, int p)	{
		
		model = Model.getModel();
		this.host = h;
		this.port = p;
		
		boolean c = false;
		logger.info("Provo a fare la connessione al server: " + host +" su porta: " + port);
		try {
			this.sock = new Socket(host,port);
			c= true;
		}	catch (UnknownHostException e1) {
			c = false;
			logger.error("Host " + host +" sconosciuto ", e1);
		}	catch (IOException e2) {
			c= false;
			logger.error("Connessione rifiutata da host: " + host + ":" + port, e2);
		}	catch (NullPointerException e3) {
			logger.error("Errore di NullPointerException", e3);
		}	finally {
			this.setConnected(c);
		}
		
		try {
			logger.info("Cerco di creare il buffer in scrittura");
			this.out = new ObjectOutputStream (sock.getOutputStream());
			out.flush();
		} catch (IOException e) {
			logger.error(" Impossibile inizializzare i buffer di scrittura \n", e);
			setConnected (false);
		}
		logger.debug("Creato il buffer in scrittura " + out.toString());
		
		try {
			logger.info("Cerco di creare il buffer in lettura");
			this.in = new ObjectInputStream (sock.getInputStream());
		} catch (IOException e) {
				logger.error(" Impossibile inizializzare i buffer di lettura \n", e);
				setConnected (false);
		}	
		logger.debug("Creato il buffer in lettura " + in.toString());
		
	}
	
	
	public void run() {
		
			logger.info("Thread di connessione al server attivo, mi metto in ascolto per ricevere messaggi");
			while (this.connected) {
				try {
					Messaggio msg = (Messaggio) in.readObject() ; 
					model.addMessageToQueue(msg) ;

				} catch (ClassNotFoundException e) {
					logger.error("Errore in lettura dallo stream " + in.toString(), e);
					setConnected (false);
				} catch (IOException e) {
					logger.error("Errore IOException in lettura dallo stream: "+ in,e);
					setConnected (false);
				}
			}
		
	}
	
	public void closeSocket() {
		try {
			logger.info("Chiudo la connessione con il server");
			this.sock.close();
			setConnected (false);
		} catch (IOException e) {
			logger.error("Errore in chiusura della socket");
		}
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}


	public UUID getSessionId() {
		return sessionId;
	}


	public void setSessionId(UUID sessionId) {
		this.sessionId = sessionId;
	}
	
	

}
