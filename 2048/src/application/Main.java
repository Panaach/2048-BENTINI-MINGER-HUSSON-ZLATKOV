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
 * classe principale qui lance notre application via l'interface graphique
 * @author Panach
 */
public class Main extends Application  {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLConnexion.fxml"));

        Scene scene = new Scene(root);
        boolean add = scene.getStylesheets().add("css/autreClasses.css");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Image/2048_logo.png")));
        stage.setTitle("2048 3D");
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
