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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sofia
 */
public class FXMLModifierController implements Initializable {
    BDD bdd = BDD.getInstance();
    
  
    
   /* @FXML private PasswordField mdp1;
    @FXML private PasswordField mdp2;
    @FXML private AnchorPane AnchorPane;*/
    
      @FXML
    private AnchorPane base;
    @FXML 
    private Pane fond;
    @FXML
    private TextField champPseudo;
    @FXML
    private PasswordField champMdp;
    @FXML
    private PasswordField champConfirmation;
    @FXML
    private TextField champMail;
    @FXML
    private Button modifier;
    @FXML
    private Button annuler;
    @FXML
    private Label titre;
    @FXML
    private Label pseudo;
    @FXML
    private Label mdp;
    @FXML
    private Label confirmation;
    @FXML
    private Label mail;
    @FXML
    private Label texte;
    @FXML
    private Button supprimer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          fond.getStyleClass().add("fond");
        base.getStyleClass().add("fond");
        titre.getStyleClass().add("titre");
        modifier.getStyleClass().add("valider");
        annuler.getStyleClass().add("boutton");
        pseudo.getStyleClass().add("pseudo");
        confirmation.getStyleClass().add("mdp");
        mdp.getStyleClass().add("mdp");
        pseudo.getStyleClass().add("pseudo");
        mail.getStyleClass().add("pseudo");
        champConfirmation.getStyleClass().add("champMdp");
        champMail.getStyleClass().add("champPseudo");
        champPseudo.getStyleClass().add("champPseudo");
        champMdp.getStyleClass().add("champMdp");
        texte.getStyleClass().add("texte");
        supprimer.getStyleClass().add("supprimer");
        // TODO
    }    

    public void validation(){
        if(champPseudo.getText().equals("") && champMdp.getText().equals("") && champConfirmation.getText().equals("") && champMail.getText().equals("")){
            champPseudo.setPromptText("Pseudo vide!");
            champMdp.setPromptText("Mdp vide!");
            champConfirmation.setPromptText("Mdp vide!");
            champMail.setPromptText("Mail vide!");
            return;
        }
        
        if((champMdp.getText().equals(champConfirmation.getText()))){
            bdd.updateUser(champPseudo.getText(),champMdp.getText(),champMail.getText());
            
            ((Stage)base.getScene().getWindow()).close();
        } else {
            champConfirmation.setText("");
            champConfirmation.setPromptText("Mdp différents");
        }
    }
    
    public void annuler(){
        ((Stage)base.getScene().getWindow()).close();
    }
    
    public void suppr(){
        bdd.deleteUser();
        ((Stage)base.getScene().getWindow()).close();
        
    }
}