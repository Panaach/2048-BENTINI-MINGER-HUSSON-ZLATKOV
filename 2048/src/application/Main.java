/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Panach
 */
public class Main extends Application  {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLJeu.fxml"));

        Scene scene = new Scene(root);
        boolean add = scene.getStylesheets().add("css/styles.css");
<<<<<<< Updated upstream
        
        stage.getIcons().add(new Image(getClass().getResourceAsStream("2048_logo.png")));
        stage.setTitle("2048 3D");
=======

>>>>>>> Stashed changes
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
