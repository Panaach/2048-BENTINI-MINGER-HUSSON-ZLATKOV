/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.Case;
import Model.Grille;
import Model.MultiGrille;
import Model.Parametres;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.HPos;

/**
 * FXML Controller class
 *
 * @author Panach
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Pane fond;
    @FXML
    private GridPane grille1;
    @FXML
    private GridPane grille3;
    @FXML
    private GridPane grille2;
    @FXML
    private Button start;
    @FXML
    private Label score;

    
    // mes changements
    private Grille grilleModel1 = new Grille();
    private Grille grilleModel2 = new Grille();
    private Grille grilleModel3 = new Grille();
    private Grille[] multiGrille = new Grille[]{grilleModel1, grilleModel2, grilleModel3};
    private MultiGrille mGrille = new MultiGrille(multiGrille);
    
    private ArrayList<Pane[][]> listTableauPane = new ArrayList<>();
    private ArrayList<Label[][]> listTableauLabel = new ArrayList<>();
    
    private int tailleX = 397/3;
    private int tailleY = 397/3;

    // variables globales non définies dans la vue (fichier .fxml)
    //private final Pane p = new Pane(); // panneau utilisé pour dessiner une tuile "2"
    //private final Label c = new Label("2");
    private int x = 24, y = 191;
    private int objectifx = 24, objectify = 191;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("le contrôleur initialise la vue");
        grille1.getStyleClass().add("gridpane");
        // je créé toutes mes tuiles au départ
        this.creationTuile();
        
        
        // utilisation de styles pour la grille et la tuile (voir styles.css)
        /*p.getStyleClass().add("pane");
        c.getStyleClass().add("tuile");
        grille1.getStyleClass().add("gridpane");
        fond.getChildren().add(p);
        p.getChildren().add(c);

        // on place la tuile en précisant les coordonnées (x,y) du coin supérieur gauche
        p.setLayoutX(x);
        p.setLayoutY(y);
        p.setPrefHeight(397/3);
        p.setPrefWidth(397/3);
        p.setVisible(true);
        c.setVisible(true);*/
    }  

    private void creationTuile() {
        // créer toutes les cases d'un case d'un coup peu causé un problème
        for (int k = 0; k < 3; k++) {
            Pane[][] listP = new Pane[3][3];
            Label[][] listL = new Label[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    listP[i][j] = new Pane();
                    listL[i][j] = new Label();
                    listP[i][j].getStyleClass().add("pane");
                    listL[i][j].getStyleClass().add("tuile2");
                }
            }
            listTableauPane.add(listP);
            listTableauLabel.add(listL);
        }
    }  

    private void coloriageTuile() {
        Grille[] cloned = this.multiGrille.clone();
        for (int i = 0; i < 3; i++) {
        // Première grille pour l'instant
        //int i = 0;
            for (Case c : cloned[i].getGrille()) {
                Pane[][] tempP = this.listTableauPane.get(i);
                Label[][] tempL = this.listTableauLabel.get(i);

                GridPane.setHalignment(tempL[c.getX()][c.getY()], HPos.CENTER);

                tempP[c.getX()][c.getY()].setLayoutX(24 + 18 * i + i * 397 + (c.getX() * tailleX)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
                tempP[c.getX()][c.getY()].setLayoutY(191 + (c.getY() * tailleY));
                tempL[c.getX()][c.getY()].setText(Integer.toString(c.getValeur()));

                fond.getChildren().add(tempP[c.getX()][c.getY()]);
                tempP[c.getX()][c.getY()].getChildren().add(tempL[c.getX()][c.getY()]);
                tempP[c.getX()][c.getY()].setVisible(true);
                tempL[c.getX()][c.getY()].setVisible(true);
            }
        }
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
    private void handleButtonAction(MouseEvent event) {
        System.out.println("Clic de souris sur le bouton start");
        //boolean b = this.multiGrille[0].nouvelleCase();
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            //System.out.println(random);
            this.multiGrille[random].nouvelleCase();
        }
        //System.out.println(multiGrille[0] + "\n" + multiGrille[1] + "\n" + multiGrille[2]);
        this.coloriageTuile();
    }

    @FXML
    private void keyPressed(KeyEvent ke) {
        System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            if (objectifx > 24) { // possible uniquement si on est pas dans la colonne la plus à gauche
                objectifx -= (int) 397 / 3; // on définit la position que devra atteindre la tuile en abscisse (modèle). Le thread se chargera de mettre la vue à jour
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
                for (int i = 0; i < 3; i++) {
                    this.multiGrille[i].lanceurDeplacerCases(Parametres.GAUCHE);
                }
            }
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            if (objectifx < (int) 1272 - 2 * 397 / 3 - 24) { // possible uniquement si on est pas dans la colonne la plus à droite (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                objectifx += (int) 397 / 3;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
                for (int i = 0; i < 3; i++) {
                    this.multiGrille[i].lanceurDeplacerCases(Parametres.DROITE);
                }
            }
        } else if (touche.compareTo("z") == 0) { // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
            if (objectify > 191) { // possible uniquement si on est pas dans la colonne la plus à bas (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                objectify -= (int) 397 / 3;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
                for (int i = 0; i < 3; i++) {
                    this.multiGrille[i].lanceurDeplacerCases(Parametres.HAUT);
                }
            }
        } else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            if (objectify < (int) 623 - 2 * 397 / 3 - 24) { // possible uniquement si on est pas dans la colonne la plus à bas (taille de la fenêtre - 2*taille d'une case - taille entre la grille et le bord de la fenêtre)
                for (int i = 0; i < 3; i++) {
                    this.multiGrille[i].lanceurDeplacerCases(Parametres.BAS);
                }
                objectify += (int) 397 / 3;
                score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));                
            }
        }
        System.out.println(mGrille);
        // mise a jour des tuiles
        System.out.println("objectifx=" + objectifx);
        System.out.println("objectify=" + objectify);
        Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
            @Override
            public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                while (x != objectifx || y != objectify) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                    if (x < objectifx) {
                        x += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                    } else {
                        x -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                    }
                    if (y < objectify) {
                        y += 1; // si on va vers le , on modifie la position de la tuile pixel par pixel vers la droite
                    } else {
                        y -= 1; // si on va vers le , idem en décrémentant la valeur de x
                    }
                    // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                    Platform.runLater(new Runnable() { // classe anonyme
                        @Override
                        public void run() {
                            mouvementVisuel(x, y);
                            //javaFX operations should go here
                            //p.relocate(x, y); // on déplace la tuile d'un pixel sur la vue, on attend 5ms et on recommence jusqu'à atteindre l'objectif
                            //p.setVisible(true);    
                        }
                    }
                    );
                    Thread.sleep(1);
                } // end while
                return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
            } // end call

        };
        Thread th = new Thread(task); // on crée un contrôleur de Thread
        th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
        th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
    }    
    
    public void mouvementVisuel(int x, int y) {
        for (int k = 0; k < 3; k++) {
            Pane[][] tbPane = listTableauPane.get(k);
            Label[][] tbLabel = listTableauLabel.get(k);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!tbLabel[i][j].getText().isEmpty()) { // dès qu'il y a un text je peux bouger la tuile
                        System.out.println(tbPane[i][j].getLayoutX());
                        System.out.println(tbPane[i][j].getLayoutY());
                        tbPane[i][j].relocate(x, y);
                        tbPane[i][j].setVisible(true);
                    }
                }
            }
        }
    }
}
