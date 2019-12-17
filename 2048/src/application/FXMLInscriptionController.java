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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Cette classe fait le lien entre la fenêtre d'inscrption et la base de données
 * 
 *
 * @author Sofia
 */
public class FXMLInscriptionController implements Initializable {

    BDD bdd = BDD.getInstance();

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
    private Button valider;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fond.getStyleClass().add("fond");
        base.getStyleClass().add("fond");
        titre.getStyleClass().add("titre");
        valider.getStyleClass().add("boutton");
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
        // TODO
    }

    public void validation() {
        boolean empty = false;
        if (pseudo.getText().equals("")) {
            champPseudo.setPromptText("Pseudo vide!");
            empty = true;
        }

        if (champMdp.getText().equals("")) {
            champMdp.setPromptText("Mdp vide!");
            empty = true;
        }
        if (champConfirmation.getText().equals("")) {
            champConfirmation.setPromptText("Mdp vide!");
            empty = true;
        }
        if (champMail.getText().equals("")) {
            champMail.setPromptText("Mail vide!");
            empty = true;
        }
        if (empty) {
            return;
        }

        if ((champMdp.getText().equals(champConfirmation.getText()))) {
            bdd.signUp(champPseudo.getText(), champMdp.getText(), champMail.getText());
            bdd.signIn(champPseudo.getText(), champMdp.getText());
            bdd.signUp(pseudo.getText(), champMdp.getText(), champMail.getText());

            ((Stage) base.getScene().getWindow()).close();
        } else {
            champConfirmation.setText("");
            champConfirmation.setPromptText("Mdp différents");
            return;
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

    public void annuler() {
        ((Stage) base.getScene().getWindow()).close();
    }
}
