package it.aaperio.TicketClient;


import java.net.URL;
import java.util.ResourceBundle;

import it.aaperio.TicketClient.Model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model; 
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtserver"
    private TextField txtserver; // Value injected by FXMLLoader

    @FXML // fx:id="txtuser"
    private TextField txtuser; // Value injected by FXMLLoader

    @FXML // fx:id="txtpassword"
    private PasswordField txtpassword; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtserver != null : "fx:id=\"txtserver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtuser != null : "fx:id=\"txtuser\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtpassword != null : "fx:id=\"txtpassword\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
