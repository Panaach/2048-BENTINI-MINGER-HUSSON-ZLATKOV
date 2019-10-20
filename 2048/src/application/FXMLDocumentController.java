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
import java.util.ArrayList;
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
 * @author Gael
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
    //private final Label c = new Label();
    private final ArrayList<Pane> listP = new ArrayList<>();
    private final ArrayList<Label> listL = new ArrayList<>();
    //private final Label cbis = new Label("4");
    // private int coordX = 24, coordY = 191;
    // private int valeur = 0;

    private int objectifx = 24, objectify = 191;
    // private int objectifGrille2x= 438;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("le contrôleur initialise la vue");
        // utilisation de styles pour la grille et la tuile (voir styles.css)

        grille1.getStyleClass().add("gridpane");
        grille2.getStyleClass().add("gridpane");
        grille3.getStyleClass().add("gridpane");
        creationTuile();

        coloriageTuile();

    }

    public void premieresCases() {
        //System.out.println("clique");
        boolean b = false;
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);

            System.out.println(random);
            b = multiGrille[random].nouvelleCase();
        }
    }

    private void creationTuile() {
        for (int i = 0; i < 27; i++) {
            listP.add(new Pane());
            listL.add(new Label());
            listP.get(i).getStyleClass().add("pane");
            listL.get(i).getStyleClass().add("tuile2");

        }
    }

    private void coloriageTuile() {
        int y = 0;
        for (int i = 0; i < 3; i++) {
            for (Case c : multiGrille[i].getGrille()) {
                System.out.println(c);
                GridPane.setHalignment(listL.get(y), HPos.CENTER);
                listP.get(y).setLayoutX(24 + 18 * i + i * 397 + (c.getX() * tailleX)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
                listP.get(y).setLayoutY(191 + (c.getY() * tailleY));
                listL.get(y).setText(Integer.toString(c.getValeur()));

                fond.getChildren().add(listP.get(y));
                listP.get(y).getChildren().add(listL.get(y));
                listL.get(y).setVisible(true);
                listP.get(y).setVisible(true);
                y++;
            }
        }
        System.out.println("truc");
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        premieresCases();
        coloriageTuile();
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
            grilleModel1.lanceurDeplacerCases(-2);
            grilleModel2.lanceurDeplacerCases(-2);
            grilleModel3.lanceurDeplacerCases(-2);

            /*if (objectifx > 24) { // possible uniquement si on est pas dans la colonne la plus à gauche
                objectifx -= (int) 397 / 3; // on définit la position que devra atteindre la tuile en abscisse (modèle). Le thread se chargera de mettre la vue à jour
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
            }*/
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoye%µ§r la tuile vers la droite
            grilleModel1.lanceurDeplacerCases(2);
            grilleModel2.lanceurDeplacerCases(2);
            grilleModel3.lanceurDeplacerCases(2);

            /*  if (objectifx < (int) 445 - 2 * 397 / 3 - 24) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                objectifx += (int) 397 / 3;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            }*/
        } else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            /* if (objectify < (int) 191) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                objectify += (int) 397 / 3;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            }*/
            grilleModel1.lanceurDeplacerCases(-1);
            //grilleModel2.lanceurDeplacerCases(-1);
            // grilleModel3.lanceurDeplacerCases(-1);
        }
        coloriageTuile();

    }
}
