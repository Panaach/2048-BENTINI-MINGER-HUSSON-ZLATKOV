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
import java.util.HashSet;
import java.util.Iterator;
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
    private Grille grilleModel1 = new Grille(0);
    private Grille grilleModel2 = new Grille(1);
    private Grille grilleModel3 = new Grille(2);
    private Grille[] multiGrille = new Grille[]{grilleModel1, grilleModel2, grilleModel3};
    private MultiGrille mGrille = new MultiGrille(multiGrille);
    
    //private ArrayList<Pane[][]> listTableauPane = new ArrayList<>();
    //private ArrayList<Label[][]> listTableauLabel = new ArrayList<>();
    
    private int tailleX = 397/3;
    private int tailleY = 397/3;

    // variables globales non définies dans la vue (fichier .fxml)
    //private final Pane p = new Pane(); // panneau utilisé pour dessiner une tuile "2"
    //private final Label c = new Label("2");
    //private int x = 24, y = 191;
    //private int objectifx = 24, objectify = 191;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("le contrôleur initialise la vue");
        //grille1.getStyleClass().add("gridpane");
        // je créé toutes mes tuiles au départ
        fond.getStyleClass().add("fond");   
        
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
        System.out.println("CREATION DES TUILES");
        for (int k = 0; k < 3; k++) {
            for (Case c : this.multiGrille[k].getGrille()) {
                if (c.getPane() == null) {
                    System.out.println("DEDANS");
                    c.setPane(new Pane());
                    c.setLabel(new Label());
                    c.getPane().getStyleClass().add("pane");
                    c.getLabel().getStyleClass().add("tuile2");
                }
            }
        }
    }  

    private void coloriageTuile() {
        Grille[] cloned = this.multiGrille.clone();
        synchronized(cloned) {
            for (int i = 0; i < 3; i++) {
                for (Case c : cloned[i].getGrille()) {
                    System.out.println(c);
                    if (c.getLastX() == -1) {
                        System.out.println("JE SUIS ENTRE");
                        GridPane.setHalignment(c.getLabel(), HPos.CENTER);

                        System.out.println("1");

                        c.getPane().setLayoutX(24 + 18 * i + i * 397 + (c.getX() * tailleX)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
                        c.getPane().setLayoutY(191 + (c.getY() * tailleY));

                        fond.getChildren().add(c.getPane());
                        c.getPane().getChildren().add(c.getLabel());
                        c.getPane().setVisible(true);
                        c.getLabel().setVisible(true);
                    }
                    c.getLabel().setText(Integer.toString(c.getValeur()));
                }
            }
        }
    }

    @FXML
    private void handleDragAction(MouseEvent event) { 
        /*System.out.println("Glisser/déposer sur la grille avec la souris");
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
        }*/
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        System.out.println("Clic de souris sur le bouton start");
        //boolean b = this.multiGrille[0].nouvelleCase();
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            //System.out.println(random);
            this.multiGrille[i].nouvelleCase();
        }
        //System.out.println(multiGrille[0] + "\n" + multiGrille[1] + "\n" + multiGrille[2]);
        this.creationTuile();
        this.coloriageTuile();
        System.out.println(mGrille);
    }

    @FXML
    private void keyPressed(KeyEvent ke) throws CloneNotSupportedException {
        System.out.println("touche appuyée");
        String touche = ke.getText();
        if (touche.compareTo("q") == 0) { // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
            boolean b1 = this.multiGrille[0].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b2 = this.multiGrille[1].lanceurDeplacerCases(Parametres.GAUCHE);
            boolean b3 = this.multiGrille[2].lanceurDeplacerCases(Parametres.GAUCHE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                this.threadMouvement();
                // function weird
                ArrayList<Integer> grillePossible = new ArrayList<>();
                grillePossible.add(0); grillePossible.add(1); grillePossible.add(2);
                // si le tableau est vide cela signifie qu'on ne peut ajouter aucune case dans les grilles
                while (!grillePossible.isEmpty()) {
                    int random = (int) (Math.random() * grillePossible.size());                    
                    boolean newCase = multiGrille[grillePossible.get(random)].nouvelleCase();
                    
                    if (!newCase)
                        grillePossible.remove(random);
                    else
                        break;
                }
                System.out.println("COLORIAGE");
                System.out.println(mGrille);
            }
            
        } else if (touche.compareTo("d") == 0) { // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            boolean b1 = this.multiGrille[0].lanceurDeplacerCases(Parametres.DROITE);
            boolean b2 = this.multiGrille[1].lanceurDeplacerCases(Parametres.DROITE);
            boolean b3 = this.multiGrille[2].lanceurDeplacerCases(Parametres.DROITE);
            System.out.println(b1 || b2 || b3);
            if (b1 || b2 || b3) {
                this.threadMouvement();
            }
        } else if (touche.compareTo("z") == 0) { // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
            score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));
            boolean b1 = this.multiGrille[0].lanceurDeplacerCases(Parametres.HAUT);
            boolean b2 = this.multiGrille[1].lanceurDeplacerCases(Parametres.HAUT);
            boolean b3 = this.multiGrille[2].lanceurDeplacerCases(Parametres.HAUT);
            if (b1 || b2 || b3) {
                this.threadMouvement();
            }
            
        } else if (touche.compareTo("s") == 0) { // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1));  
            boolean b1 = this.multiGrille[0].lanceurDeplacerCases(Parametres.BAS);
            boolean b2 = this.multiGrille[1].lanceurDeplacerCases(Parametres.BAS);
            boolean b3 = this.multiGrille[2].lanceurDeplacerCases(Parametres.BAS);
            if (b1 || b2 || b3) {
                this.threadMouvement();
            }              
            
        }
                this.coloriageTuile();
        System.out.println(mGrille);
    }   
    
    public void threadMouvement() {
        int nb = 0;
        for (int i = 0; i < 3; i++) {    
            for (Case c  : this.multiGrille[i].getGrille()) {
                final int fi = i;
                Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
                    @Override
                    public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                        //System.out.println("X:"+c.getX()+"Y:"+c.getY()+"\nLast X:"+c.getLastX()+"Last Y:"+c.getLastY());
                        // Après mouvement
                        int objectifx = 24 + 18 * fi + fi * 397 + (c.getX() * tailleX);
                        int objectify = 191 + tailleY * c.getY();
                        //int test = 191 + 191 * c.getY();
                        // Avant mouvement
                        int x = 24 + 18 * fi + fi * 397 + (c.getLastX() * tailleX);
                        int y = 191 + tailleY * c.getLastY();
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
                            final int varX = x;
                            final int varY = y;
                            Platform.runLater(new Runnable() { // classe anonyme
                                    @Override
                                    public void run() {
                                        /*System.out.println("Case de traitement : " + c);
                                        System.out.println("OBJECTIF:");
                                        System.out.println("objectifx:"+objectifx);
                                        System.out.println("X:        "+varX);
                                        System.out.println("objectify:"+objectify);
                                        System.out.println("Y        :"+varY);*/
                                        c.getPane().relocate(varX, varY);
                                        c.getPane().setVisible(true); 
                                    }
                                }
                            );
                            Thread.sleep(1);
                        } // end while
                        return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
                    } // end call

                };
                Thread th = new Thread(task); // on crée un contrôleur de Thread
                System.out.println(th + " " + nb++);
                th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
                th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
            }
        }
    }
}
