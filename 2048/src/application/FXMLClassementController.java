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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

/**
 * FXML Controller class
 *
 * @author Sofia
 */
public class FXMLClassementController implements Initializable {
    @FXML private TreeTableView tab;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] test = {"pseudo","score"};
        addItem(test);
    }
    
    public void addItem(String[] values){
        TreeItem test = new TreeItem(values);
        TreeItem root = new TreeItem(values);
        
        root.getChildren().add(test);
        
        tab.setRoot(root);
    }

}