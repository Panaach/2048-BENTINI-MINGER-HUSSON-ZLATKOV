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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia
 */
public class FXMLConnexion implements Initializable {

    BDD bdd = BDD.getInstance();
    
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
    @FXML 
    private AnchorPane AnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void handleDragAction(MouseEvent event2) { 

    }

    public void connexion(MouseEvent event) {
        boolean empty = false;
        if(champPseudo.getText().equals("")){
            champPseudo.setPromptText("Pseudo vide!");
            empty = true;
        }
        
        if(champMdp.getText().equals("")){
            champMdp.setPromptText("Mdp vide!");
            empty = true;
        }
        if(empty) return;
        
        bdd.signIn(champPseudo.getText(), champMdp.getText());
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
        
        ((Stage)AnchorPane.getScene().getWindow()).close();
    }
    
    @FXML
    public void inscription(MouseEvent event) {
        try {
            System.out.println("Clic de souris sur le bouton connexion");
            Parent loader = FXMLLoader.load(getClass().getResource("FXMLInscription.fxml"));

            Scene scene = new Scene(loader);
            boolean add = scene.getStylesheets().add("css/inscriptionNuit.css");
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