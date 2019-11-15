/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.CareTaker;
import Model.Case;
import Model.Grille;
import Model.MultiGrille;
import Model.Originator;
import Model.Parametres;
import Model.Tuile2048;
import Model.TuileComposite;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;


/**
 * FXML Controller class
 *
 * @author Panach
 */
public class FXMLControllerJeu implements Initializable, Parametres {

    @FXML
    private Pane fond;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label score;
    @FXML
    private Label resultat;
    @FXML
    private CheckMenuItem buttonActive;
    @FXML
    private Button btnHaut;
    @FXML
    private Button btnBas;
    @FXML
    private Button btnGauche;
    @FXML
    private Button btnDroite;

    
    // Les 3 grilles
    private Grille left;
    private Grille middle;
    private Grille right;
    // Tableau de grille
    private Grille[] multiGrille;
    // Création de mon instance MultiGrille
    private MultiGrille mGrille;    
        
    // Pour le bouton revenir en arrière
    private Originator originator;
    private CareTaker careTaker;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fond.getStyleClass().add("fond"); 
        //menuBar.getStyleClass().add("menuBar"); 
        // Bouton non visible au départ
        btnHaut.setVisible(false);
        btnGauche.setVisible(false);
        btnDroite.setVisible(false);
        btnBas.setVisible(false);
        // Initialisation de ma multi-grille
        left = new Grille(0);
        middle = new Grille(1);
        right = new Grille(2);        
        multiGrille = new Grille[]{left, middle, right};
        mGrille = MultiGrille.INSTANCE;
        mGrille.init(multiGrille);
                
        // Pour le bouton revenir en arrière
        originator = new Originator();
        careTaker = new CareTaker();
    }  

    private void afficherTuile() { // A chaque nouvelle case cela créé la tuile (de façon dynamique)
        System.out.println("AFFICHAGE DES TUILES");
        for (int k = 0; k < 3; k++) {
            for (Case c : this.multiGrille[k].getGrille()) {
                if (c.getPane() == null) { // la tuile vient d'être créé
                    switch(c.getValeur()) {
                        case 2:
                            c.setPane(new Pane());
                            c.setLabel(new Label());
                            c.getPane().getStyleClass().add("pane2");
                            c.getLabel().getStyleClass().add("tuile");
                            break;
                        case 4:
                            c.setPane(new Pane());
                            c.setLabel(new Label());
                            c.getPane().getStyleClass().add("pane4");
                            c.getLabel().getStyleClass().add("tuile");
                            break;
                    }
                    c.getLabel().setText(Integer.toString(c.getValeur()));
                }
            }
        }        
    }  

    private void positionTuile() {
        for (int i = 0; i < 3; i++) {
            for (Case c : this.multiGrille[i].getGrille()) {
                if (!fond.getChildren().contains(c.getPane())) { // la case vient d'être créé
                    // Position du pane sur le fond
                    c.getPane().setLayoutX(24 + 18 * i + i * 397 + (c.getX() * tailleX)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
                    c.getPane().setLayoutY(191 + (c.getY() * tailleY));
                    // Ajout du label dans le pane
                    c.getPane().getChildren().add(c.getLabel());
                    // Pane et label rendu visible
                    c.getPane().setVisible(true);
                    c.getLabel().setVisible(true);
                    // Ajout du pane sur le fond
                    fond.getChildren().add(c.getPane());
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
        if (this.mGrille.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                int random = (int) (Math.random() * 3);
                this.multiGrille[random].nouvelleCase();
            }
            this.afficherTuile();
            this.positionTuile();
            System.out.println(mGrille);
        } else {
            System.out.println("Jeu déjà en cours!");
        }
    }
    
    @FXML
    private void buttonActionNewGame() {  
        System.out.println("New Game");
        this.destroyTemplate();
        left = new Grille(0);
        middle = new Grille(1);
        right = new Grille(2);   
        multiGrille = new Grille[]{left, middle, right};   
        mGrille.init(multiGrille);
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            this.multiGrille[random].nouvelleCase();
        }
        this.afficherTuile();
        this.positionTuile();
        System.out.println(mGrille);
    }
    
    @FXML
    private void closeApp() {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    private void mouvementGauche(MouseEvent event) throws CloneNotSupportedException {
        this.mouvementNormaux(GAUCHE);
    }
        
    @FXML
    private void mouvementDroite(MouseEvent event) throws CloneNotSupportedException {
        this.mouvementNormaux(DROITE);
    }
    
    @FXML
    private void mouvementBas(MouseEvent event) throws CloneNotSupportedException {
        this.mouvementNormaux(BAS);
    }
    
    @FXML
    private void mouvementHaut(MouseEvent event) throws CloneNotSupportedException {
        this.mouvementNormaux(HAUT);
    }

    @FXML
    private void keyPressed(KeyEvent ke) throws CloneNotSupportedException {
        String touche = ke.getText();
        if (touche.compareTo("q") == 0)  // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
            this.mouvementNormaux(GAUCHE);            
        else if (touche.compareTo("d") == 0) // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
            this.mouvementNormaux(DROITE);
        else if (touche.compareTo("z") == 0) // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
            this.mouvementNormaux(HAUT);
        else if (touche.compareTo("s") == 0) // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
            this.mouvementNormaux(BAS);      
        else if (touche.compareTo("a") == 0) { // FUSION GAUCHE
            this.mouvementFusion(FULLLEFT);
        } else if (touche.compareTo("e") == 0) { // FUSION DROITE
            this.mouvementFusion(FULLRIGHT);
        }
        this.updateTemplate(); 
        System.out.println(mGrille);
    } 
    
    private void mouvementNormaux(int dir) throws CloneNotSupportedException { // Pour les mouvements normaux
        TuileComposite t = new TuileComposite();
        score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
        boolean b1 = this.multiGrille[0].lanceurDeplacerCases(dir);
        boolean b2 = this.multiGrille[1].lanceurDeplacerCases(dir);
        boolean b3 = this.multiGrille[2].lanceurDeplacerCases(dir);
        if (b1 || b2 || b3) {
            // Méthode composite
            t.add(new Tuile2048(this.multiGrille[0]));
            t.add(new Tuile2048(this.multiGrille[1]));
            t.add(new Tuile2048(this.multiGrille[2]));
            t.threadMovement();
            t.threadMovementCaseDead(fond);
            this.nouvelleCase();
        }
        this.finDePartie();
    }
    
    private void mouvementFusion(int dir) throws CloneNotSupportedException {
        TuileComposite t = new TuileComposite();
        boolean fusionSuccess;
        if (dir == FULLLEFT) fusionSuccess = mGrille.fusionGauche();  
            else fusionSuccess = mGrille.fusionDroite();  
        
        if (fusionSuccess) {
            t.add(new Tuile2048(this.multiGrille[0]));
            t.add(new Tuile2048(this.multiGrille[1]));
            t.add(new Tuile2048(this.multiGrille[2]));
            t.threadMovement();
            t.threadMovementCaseDead(fond);
            this.nouvelleCase();                 
        }
        this.finDePartie();
    }
    
    @FXML
    private void buttonActive() {
        if (buttonActive.isSelected()) {
            btnHaut.setVisible(true);
            btnGauche.setVisible(true);
            btnDroite.setVisible(true);
            btnBas.setVisible(true);
        } else {            
            btnHaut.setVisible(false);
            btnGauche.setVisible(false);
            btnDroite.setVisible(false);
            btnBas.setVisible(false);
        }
    }
    
    private void updateTemplate() {
        for (int k = 0; k < 3; k++) {
            for (Case c : this.multiGrille[k].getGrille()) {
                c.getLabel().setText(Integer.toString(c.getValeur()));
                switch(c.getValeur()) {
                    case 4:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane4");
                        break;
                    case 8:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane8");
                        break;
                    case 16:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane16");
                        c.getLabel().getStyleClass().add("tuile10");
                        break;
                    case 32:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane32");
                        break;
                    case 64:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane64");
                        break;
                    case 128:
                        c.getPane().getStyleClass().clear();
                        c.getPane().getStyleClass().add("pane128");
                        c.getLabel().getStyleClass().add("tuile100");
                        break;
                }
            }  
        }
    }
    
    private void nouvelleCase() {
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
        this.afficherTuile();
        this.positionTuile();
    }
    
    private void destroyTemplate() {
        for (int i = 0; i < 3; i++) {
            for (Case c : this.multiGrille[i].getGrille()) {
                fond.getChildren().remove((Node) c.getPane());
            }
        }
    }
    
    private void finDePartie() {
        if (mGrille.valeurMax()>=OBJECTIF) {
            fond.getStyleClass().add("victory");
            resultat.setText("Victoire !");
            resultat.getStyleClass().add("styleResultat");
            resultat.focusedProperty();
        } else if (!left.partieFinie() && !middle.partieFinie() && !right.partieFinie() && !mGrille.partieFinie()) { // enlever -!-
            fond.getStyleClass().add(0, "victory");
            resultat.setText("Perdu !");
            resultat.getStyleClass().add(0, "styleResultat");
            resultat.focusedProperty();
        }
    }
    
    private void perdre() {
        int v = 2;
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x ++) {
                for (int y = 0; y < 3; y ++) {
                    Case c = new Case(x, y, v, i);
                    this.multiGrille[i].getGrille().add(c);
                    v = v + v;
                }
            }
            v = v+v;
        }
    }
}