/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;
import Model.BDD;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia
 */
public class FXMLInscriptionController implements Initializable {
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
        boolean empty = false;
        if(pseudo.getText().equals("")){
            pseudo.setPromptText("Pseudo vide!");
            empty = true;
        }
        
        if(mdp1.getText().equals("")){
            mdp1.setPromptText("Mdp vide!");
            empty = true;
        }
        if(mdp2.getText().equals("")){
            mdp2.setPromptText("Mdp vide!");
            empty = true;
        }
        if(mail.getText().equals("")){
            mail.setPromptText("Mail vide!");
            empty = true;
        }
        if(empty) return;
        
        
        if((mdp1.getText().equals(mdp2.getText()))){
            bdd.signUp(pseudo.getText(), mdp1.getText(), mail.getText());
            
            ((Stage)AnchorPane.getScene().getWindow()).close();
        } else {
            mdp2.setPromptText("Mdp différents");
        }
        
        try {
            System.out.println("Clic de souris sur le bouton connexion");
            Parent loader = FXMLLoader.load(getClass().getResource("FXMLJeu.fxml"));

            Scene scene = new Scene(loader);
            boolean add = scene.getStylesheets().add("css/jeuNuit.css");
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void annuler(){
        ((Stage)AnchorPane.getScene().getWindow()).close();
    }
}