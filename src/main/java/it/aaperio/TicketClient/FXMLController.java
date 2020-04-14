package it.aaperio.TicketClient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtserver;

    @FXML
    private TextField txtuser;

    @FXML
    private PasswordField txtpassword;

    @FXML
    void initialize() {
        assert txtserver != null : "fx:id=\"txtserver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtuser != null : "fx:id=\"txtuser\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtpassword != null : "fx:id=\"txtpassword\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
