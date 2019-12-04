/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Sofia
 */
public class FXMLConnexion implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML 
    private Pane fondconnexion;
    @FXML
    private TextField champPseudo;
    @FXML
    private PasswordField champMdp;
    @FXML
    private Button connexion;
    @FXML
    private Button inscription;
    @FXML
    private Button annuler;
    @FXML
    private Label titre;
    @FXML
    private Label pseudo;
    @FXML
    private Label mdp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    private void handleButtonAction(MouseEvent event) {
        System.out.println("Clic de souris sur le bouton connexion");
       
        }
    
    @FXML
    private void handleDragAction(MouseEvent event2) { 
        
    }
    
}
