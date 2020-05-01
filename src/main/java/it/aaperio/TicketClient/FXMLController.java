/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.aaperio.TicketClient;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import it.aaperio.TicketClient.Model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model ;
    private Logger logger ; 
    
	
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtServer;

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnConnetti;

    @FXML
    void hdlConnetti(ActionEvent event) {
    	
    	// Verifico che i campi siano stati inseriti
    	
    	
    	// Richiamo il model per l'invio della connessione. Attendo un valore booleano di risposta
    	// Se la risposta è true sono connesso: 
    					// disabilito la parte di interfaccia di connessione
    					// abilito il campo disconnect
    					// Imposto i nuovi  valori di default nel file di configurazione
    	// Altrimenti apro un popup per avvisare che non è andata a buon fine.
    }

    @FXML
    void initialize() {
        assert txtServer != null : "fx:id=\"txtServer\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnetti != null : "fx:id=\"btnConnetti\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Imposto i campi di default
        txtServer.setText(model.getConfig().getHOST()) ;
        txtUser.setText (model.getConfig().getLAST_USER()) ;
    }
    
    public void setModel(Model model) {
    	this.model = Model.getModel();
    }
}

