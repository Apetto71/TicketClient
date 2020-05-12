/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.aaperio.TicketClient;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import it.aaperio.TicketClient.Model.Configuration;
import it.aaperio.TicketClient.Model.Connection;
import it.aaperio.TicketClient.Model.Model;
import it.aaperio.ticketserver.model.User;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {
	
	private Model model ;
    private Logger logger = Logger.getLogger(FXMLController.class); ; 
    
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hboxConnection;

    @FXML
    private TextField txtServer;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnConnetti;

    @FXML
    public void hdlConnetti(ActionEvent event) {
    	logger.info("Premuto il tasto Connetti") ;
    	// Verifico che i campi siano stati inseriti
    	if (txtServer.equals("") || txtUser.equals("") || txtPassword.equals("")) {
    		throw new IllegalArgumentException() ;
    	}
    	
    	logger.debug ("Faccio partire la connessione con il server") ;
    	model.StartConnection(this.txtServer.getText(), Integer.parseInt(model.getConfig().getPORTA())) ;
    	// Se la risposta è true sono connesso, il client procede ad inviare la richiesta
    	// di autenticazione
    	
    	User utente = new User(txtUser.getText(), txtPassword.getText()) ;
    	logger.debug("Creata nuova istanza di User: " + utente.toString()) ;
    
    	model.autorizza (utente) ;
    	
    	while (!model.isAutorizzato()) {
    		System.out.println("Attendo che il server mi autorizzi");
			// disabilito la parte di interfaccia di connessione
	    	hboxConnection.setDisable(true);
	    	// abilito il campo disconnect
			// Imposto i nuovi  valori di default nel file di configurazione
	    	// Altrimenti apro un popup per avvisare che non è andata a buon fine.
        	
    	}
    	
    }

    @FXML
    void initialize() {
        assert hboxConnection != null : "fx:id=\"hboxConnection\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtServer != null : "fx:id=\"txtServer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnetti != null : "fx:id=\"btnConnetti\" was not injected: check your FXML file 'Scene.fxml'.";

        //logger.debug ("Imposto il valore di default del Server") ;
        txtServer.setText(model.getConfig().getHOST()) ;
        txtUser.setText (model.getConfig().getLAST_USER()) ;
    }
    
    public void setModel(Model model) {
    	this.model = Model.getModel();
    }
}

