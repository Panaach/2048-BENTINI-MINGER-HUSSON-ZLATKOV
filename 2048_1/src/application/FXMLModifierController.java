/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;
import Model.BDD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia
 */
public class FXMLModifierController implements Initializable {
    BDD bdd = BDD.getInstance();
    
    @FXML private TextField pseudo;
    @FXML private TextField mail;
    @FXML private PasswordField mdp1;
    @FXML private PasswordField mdp2;
    @FXML private AnchorPane AnchorPane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void validation(){
        if(pseudo.getText().equals("") && mdp1.getText().equals("") && mdp2.getText().equals("") && mail.getText().equals("")){
            pseudo.setPromptText("Pseudo vide!");
            mdp1.setPromptText("Mdp vide!");
            mdp2.setPromptText("Mdp vide!");
            mail.setPromptText("Mail vide!");
            return;
        }
        
        if((mdp1.getText().equals(mdp2.getText()))){
            bdd.updateUser(pseudo.getText(),mdp1.getText(),mail.getText());
            
            ((Stage)AnchorPane.getScene().getWindow()).close();
        } else {
            mdp1.setText("");
            mdp1.setPromptText("Mdp différents");
        }
    }
    
    public void annuler(){
        ((Stage)AnchorPane.getScene().getWindow()).close();
    }
}