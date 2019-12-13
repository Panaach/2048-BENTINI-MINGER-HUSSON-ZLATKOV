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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Sofia
 */

public class FXMLClassementController implements Initializable {
    @FXML
    private AnchorPane base;
    @FXML 
    private Pane fond;
    @FXML
    private Label nbMoove;
    @FXML
    private Label nbMoove1;
    @FXML
    private Label nbMoove2;
    @FXML
    private Label nbMoove3;
    @FXML
    private Label titre;
    @FXML
    private Label pseudo;
    @FXML
    private Label pseudo1;
    @FXML
    private Label pseudo2;
    @FXML
    private Label pseudo3;
    @FXML
    private Label texte;
    @FXML
    private Label un;
    @FXML 
    private Label deux;
    @FXML
    private Label trois;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           fond.getStyleClass().add("fond");
        base.getStyleClass().add("fond");
        titre.getStyleClass().add("titre");
        nbMoove.getStyleClass().add("pseudo");
        nbMoove1.getStyleClass().add("mooveEx");
        pseudo.getStyleClass().add("pseudo");
        nbMoove2.getStyleClass().add("mooveEx");
        nbMoove3.getStyleClass().add("mooveEx");
        pseudo.getStyleClass().add("pseudo");
        pseudo1.getStyleClass().add("pseudoEx");
        pseudo2.getStyleClass().add("pseudoEx");
        pseudo3.getStyleClass().add("pseudoEx");
        texte.getStyleClass().add("texte");
        un.getStyleClass().add("pseudo");
        deux.getStyleClass().add("pseudo");
        trois.getStyleClass().add("pseudo");
    }
}
