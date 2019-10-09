/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.Case;
import Model.Grille;
import Model.MultiGrille;
import static java.lang.Math.random;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Panach
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label score;

    @FXML
    private GridPane grille1;
    @FXML
    private GridPane grille2;

    @FXML
    private GridPane grille3;

    @FXML
    private Button start;

    private Grille grilleModel1 = new Grille();
    private Grille grilleModel2 = new Grille();
    private Grille grilleModel3 = new Grille();
    private Grille[] multiGrille = new Grille[]{grilleModel1, grilleModel2, grilleModel3};

    private int tailleX = 133;
    private int tailleY = 133;

    @FXML
    private Pane fond; // panneau recouvrant toute la fenêtre

    private final Pane p = new Pane(); // panneau utilisé pour dessiner une tuile "2"
    //private final Pane p4 = new Pane();
    private final Label c = new Label();
    //private final Label cbis = new Label("4");
    private int coordX = 24, coordY = 191;
    // private int valeur = 0;
    ;
    private int objectifx = 24, objectify = 191;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("le contrôleur initialise la vue");
        // utilisation de styles pour la grille et la tuile (voir styles.css)
        p.getStyleClass().add("pane");

        c.getStyleClass().add("tuile2");
        grille1.getStyleClass().add("gridpane");
        grille2.getStyleClass().add("gridpane");
        grille3.getStyleClass().add("gridpane");

        multiGrille[0].nouvelleCase();
        System.out.println(multiGrille[2]);
      //  for(int i=0;i<27;i++){
        coloriageTuile(p, c);

        /*grilleModel2.nouvelleCase();
        coloriageTuile(p, c);*/
        // on place la tuile en précisant les coordonnées (x,y) du coin supérieur gauche
    }

    private void coloriageTuile(Pane p, Label l) {
        for (int i = 0; i < 3; i++) {
            for (Case c : multiGrille[i].getGrille()) {
                System.out.println(c);
                GridPane.setHalignment(l, HPos.CENTER);
                p.setLayoutX(24 + (c.getX() * tailleX) + i * 133);
                p.setLayoutY(191 + (c.getY() * tailleY) + i * 133);
                l.setText(Integer.toString(c.getValeur()));

                fond.getChildren().add(p);
                p.getChildren().add(l);

            }
        }
        System.out.println("truc");
        l.setVisible(true);
        p.setVisible(true);
    }

    @FXML
    private void handleButtonAction(MouseEvent event) { // pas juste 
        //System.out.println("clique");
        grilleModel1.nouvelleCase();
    }

    @FXML
    private void handleDragAction(MouseEvent event) {
        System.out.println("Glisser/déposer sur la grille avec la souris");
        double x = event.getX();//translation en abscisse
        double y = event.getY();//translation en ordonnée
        if (x > y) {
            for (int i = 0; i < grille1.getChildren().size(); i++) { //pour chaque colonne
                //for (int j = 0; j < grille.getRowConstraints().size(); j++) { //pour chaque ligne
                System.out.println("ok1");
                grille1.getChildren().remove(i);

                /*Node tuile = grille.getChildren().get(i);
                 if (tuile != null) {
                 int rowIndex = GridPane.getRowIndex(tuile);
                 int rowEnd = GridPane.getRowIndex(tuile);
                 }*/
                // }
            }
        } else if (x < y) {
            System.out.println("ok2");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    Pane p = new Pane();
                    p.getStyleClass().add("pane");
                    grille1.add(p, i, j);
                    p.setVisible(true);
                    grille1.getStyleClass().add("gridpane");
                }
            }
        }
    }

    @FXML
    private void handleButtonAction(KeyEvent event) {
        System.out.println("Clic de souris sur le bouton menu");

    }

    @FXML
    public void keyPressed(KeyEvent ke) {
        System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            if (objectifx > 24) { // possible uniquement si on est pas dans la colonne la plus à gauche
                objectifx -= (int) 397 / 3; // on définit la position que devra atteindre la tuile en abscisse (modèle). Le thread se chargera de mettre la vue à jour
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
            }
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            if (objectifx < (int) 445 - 2 * 397 / 3 - 24) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                objectifx += (int) 397 / 3;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            }
        }
        System.out.println("objectifx=" + objectifx);
        Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
            @Override
            public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                while (coordX != objectifx) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                    if (coordX < objectifx) {
                        coordX += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                    } else {
                        coordX -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                    }
                    // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                    Platform.runLater(new Runnable() { // classe anonyme
                        @Override
                        public void run() {
                            //javaFX operations should go here
                            p.relocate(coordX, coordY); // on déplace la tuile d'un pixel sur la vue, on attend 5ms et on recommence jusqu'à atteindre l'objectif
                            p.setVisible(true);
                        }
                    }
                    );
                    Thread.sleep(5);
                } // end while
                return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
            } // end call

        };
        Thread th = new Thread(task); // on crée un contrôleur de Thread
        th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
        th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)
    }

}
