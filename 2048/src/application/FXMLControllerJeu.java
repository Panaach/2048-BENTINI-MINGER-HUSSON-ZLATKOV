/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Model.BDD;
import Model.Utopie;
import Model.CareTaker;
import Model.Case;
import Model.Grille;
import Model.Cube;
import Model.Originator;
import Model.Parametres;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * Cette classe fait le lien entre notre package model et la vue
 * @author Panach
 */
public class FXMLControllerJeu implements Initializable, Parametres, java.io.Serializable {
    @FXML private Pane fond;
    @FXML private Label score;
    @FXML private CheckMenuItem buttonActive;
    @FXML private Button btnHaut;
    @FXML private Button btnBas;
    @FXML private Button btnGauche;
    @FXML private Button btnDroite;
    @FXML private Button btnFusionSuperieur;
    @FXML private Button btnFusionInferieur;
    
    private Label lblResultat;
    private boolean partieFini = false;

    // Les 3 grilles
    private Grille inferieur;
    private Grille milieu;
    private Grille superieur;
    // Tableau de grille
    private Grille[] multiGrille;
    // Création de mon instance MultiGrille
    protected Cube cube;            

    // Pour le bouton revenir en arrière
    private Originator originator;
    private CareTaker careTaker;
    
    
    BDD bdd = BDD.getInstance();

    /**
     * Initialisation de l'interface graphique, à l'ouverture du FXML
     
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fond.getStyleClass().add("fond");  
        // Bouton non visible au départ
        btnHaut.setVisible(false);
        btnGauche.setVisible(false);
        btnDroite.setVisible(false);
        btnBas.setVisible(false);
        btnFusionSuperieur.setVisible(false);
        btnFusionInferieur.setVisible(false);
        
        // Initialisation de ma multi-grille
        inferieur = new Grille(0);
        milieu = new Grille(1);
        superieur = new Grille(2);
        multiGrille = new Grille[]{inferieur, milieu, superieur};
        cube = Cube.INSTANCE;
        cube.init(multiGrille);

        // Pour le bouton revenir en arrière
        originator = new Originator();
        careTaker = new CareTaker();
    }

    /**
     * Acive ou désactive le mode avec les boutons
     */
    @FXML
    private void buttonActive() {
        if (buttonActive.isSelected()) {
            btnHaut.setVisible(true);
            btnGauche.setVisible(true);
            btnDroite.setVisible(true);
            btnBas.setVisible(true);
            btnFusionSuperieur.setVisible(true);
            btnFusionInferieur.setVisible(true);
        } else {
            btnHaut.setVisible(false);
            btnGauche.setVisible(false);
            btnDroite.setVisible(false);
            btnBas.setVisible(false);
            btnFusionSuperieur.setVisible(false);
            btnFusionInferieur.setVisible(false);
        }
    }
    /*SERIALISATION*/
    /**
     * Sauvegarde dans un fichier les 3 grilles. Il ne peut pas enregistrer le
     * cube car c'est une énuération
     */
    @FXML
    private void savePartie() {
        ObjectOutputStream oos = null;
        try {
            File f = new File(new File(new File(FXMLControllerJeu.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent()).getParent() + "\\mg.ser");
            final FileOutputStream fichier = new FileOutputStream(f);
            oos = new ObjectOutputStream(fichier);
            oos.writeObject(inferieur);
            oos.writeObject(milieu);
            oos.writeObject(superieur);
            oos.flush();
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLControllerJeu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (oos != null) {
                    oos.flush();
                    oos.close();
                    System.out.println("Fichier enregistré");
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Recharge la dernière partie qui a été enregistré
     */
    @FXML
    private void chargerPartie() {
        this.destroyTemplate();
        ObjectInputStream ois = null;
        Grille[] grilles = new Grille[3];
        try {
            File f = new File (new File(new File(FXMLControllerJeu.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent()).getParent() + "\\mg.ser");

            final FileInputStream fichierIn = new FileInputStream(f);
            ois = new ObjectInputStream(fichierIn);
            //cube = (MultiGrille) ois.readObject();
            for (int i = 0; i < grilles.length; i++) {
                grilles[i] = (Grille) ois.readObject();
            }
            this.multiGrille = grilles;
            this.cube.setMultiGrille(grilles);

        } catch (final java.io.IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException ex) {
            Logger.getLogger(FXMLControllerJeu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println(cube);
        this.updateTemplate(true);
    }

    /**
     * Lance la partie s'il n'y a rien dans le cube
     * @param event 
     */
    @FXML
    private void buttonStartGame(MouseEvent event) {
        System.out.println("Clic de souris sur le bouton start");
        if (this.cube.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                int random = (int) (Math.random() * 3);
                this.multiGrille[random].nouvelleCase();
            }
            this.afficherNouvelleTuile();
            System.out.println(cube);
        } else {
            System.out.println("Jeu déjà en cours!");
        }
    }

    /**
     * Lance une nouvelle partie
     */
    @FXML
    private void buttonActionNewGame() {
        System.out.println("New Game");
        this.destroyTemplate();
        inferieur = new Grille(0);
        milieu = new Grille(1);
        superieur = new Grille(2);
        multiGrille = new Grille[]{inferieur, milieu, superieur};
        cube.init(multiGrille);
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            this.multiGrille[random].nouvelleCase();
        }
        this.partieFini = false;
        this.afficherNouvelleTuile();
        System.out.println(cube);
    }

    /**
     * Ferme l'application
     */
    @FXML
    private void closeApp() {
        Platform.exit();
        System.exit(0);
    }
    
    /**
     * Mouvement produit avec les boutons
     */
    @FXML
    private void mouvement() {        
        if (!this.partieFini) {
            if (this.btnDroite.isArmed()) 
                this.deplacementTuile(DROITE);
            else if (this.btnGauche.isArmed()) 
                this.deplacementTuile(GAUCHE);
            else if (this.btnBas.isArmed()) 
                this.deplacementTuile(BAS);
            else if (this.btnHaut.isArmed()) 
                this.deplacementTuile(HAUT);
            else if (this.btnFusionInferieur.isArmed()) 
                this.deplacementTuile(INFERIEUR);
            else if (this.btnFusionSuperieur.isArmed()) 
                this.deplacementTuile(SUPERIEUR);

            this.partieFini = this.finDePartie();
            this.updateTemplate(false);            
            System.out.println(cube);
        }
    }
    
    /**
     * Change le look de l'interface graphique
     */    
    @FXML
    private void cssJour() {
        fond.getStylesheets().clear();
        fond.getStylesheets().add("css/jeuJour.css");
    }
    @FXML
    private void cssNuit() {
        fond.getStylesheets().clear();
        fond.getStylesheets().add("css/jeuNuit.css");
    }
    
    @FXML
    private void updateAccount(){
        try {
            System.out.println("Clic de souris sur le bouton modif " + bdd.getID());
            Parent loader = FXMLLoader.load(getClass().getResource("FXMLModifier.fxml"));

            Scene scene = new Scene(loader);
            boolean add = scene.getStylesheets().add("css/autreClasses.css");
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void chargerClassement(){
        try {
            System.out.println("Clic de souris sur le bouton modif " + bdd.getID());
            Parent loader = FXMLLoader.load(getClass().getResource("FXMLClassement.fxml"));

            Scene scene = new Scene(loader);
            boolean add = scene.getStylesheets().add("css/autreClasses.css");
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(FXMLConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Mouvement produit par les touches z, q, s, d, a, e.
     * @param ke 
     */
    @FXML
    private void keyPressed(KeyEvent ke) {
        if (!this.partieFini) {
            String touche = ke.getText();
            if (touche.compareTo("q") == 0) // utilisateur appuie sur "q" pour envoyer la tuile vers la gauche
                this.deplacementTuile(GAUCHE);
             else if (touche.compareTo("d") == 0) // utilisateur appuie sur "d" pour envoyer la tuile vers la droite
                this.deplacementTuile(DROITE);
             else if (touche.compareTo("z") == 0) // utilisateur appuie sur "z" pour envoyer la tuile vers le haut
                this.deplacementTuile(HAUT);
             else if (touche.compareTo("s") == 0) // utilisateur appuie sur "s" pour envoyer la tuile vers le bas
                this.deplacementTuile(BAS);
             else if (touche.compareTo("a") == 0)  // FUSION GAUCHE
                this.deplacementTuile(INFERIEUR);
             else if (touche.compareTo("e") == 0)  // FUSION DROITE
                this.deplacementTuile(SUPERIEUR);

            this.updateTemplate(false);
            partieFini = this.finDePartie();
            System.out.println(partieFini);
            System.out.println(cube);
        }
    }

    /**
     * Bouton pour revenir en arrière
     */
    @FXML
    private void moveBack() {
        System.out.println("BACK");
        originator.getStateFromMemento(careTaker.get(0));
        Iterator it = originator.getState().iterator();
        cube = (Cube) it.next();
        this.updateTemplate(false);
        System.out.println(cube);
    }
    
    /**
     * Lance l'IA pour trouver des solutions
     */
    @FXML
    private void playUtopie() {
        System.out.println("UTOPIE");
        Utopie utopie = new Utopie(fond);
        utopie.launchUtopie();
    }

    /**
     * Créer une nouvelle tuile pour chaque nouvelle case créer dans la console
     */
    private void afficherNouvelleTuile() { // A chaque nouvelle case cela créé la tuile (de façon dynamique)
        System.out.println("AFFICHAGE DES TUILES");
        for (int k = 0; k < 3; k++) {
            for (Case c : this.multiGrille[k].getGrille()) {
                if (c.getPane() == null) { // la tuile vient d'être créé
                    switch (c.getValeur()) {
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
                // c est la case en cour de traitement
                this.positionTuile(c);
            }
        }
    }

    /**
     * Positionne toutes les tuiles
     */
    private void positionTuile(Case c) {
        if (!fond.getChildren().contains(c.getPane())) { // la case vient d'être créé
            // Position du pane sur le fond
            c.getPane().setLayoutX(24 + 18 * c.getNumGrille() + c.getNumGrille() * 397 + (c.getX() * tailleX)); // 18*i == la bordure entre chaque grille ,   i*133 c'est pour se metre sur chaque grille
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

    /**
     * Mouvement pour les tuiles HAUT, BAS, DOITE, GAUCHE, FUSION SUP, FUSION INF
     * @param dir 
     */
    private void deplacementTuile(int dir) { // Pour les mouvements normaux
        //memento
        /*EnumSet<Cube> tempCloned = EnumSet.allOf(Cube.class);
        EnumSet<Cube> cloned = tempCloned.clone();
        this.originator.setState(cloned);
        this.careTaker.add(originator.saveStateToMemento());
        originator.getStateFromMemento(careTaker.get(0));*/
        
        boolean b = cube.deplacement(dir);
        if (b) {
            score.setText(Integer.toString(Integer.parseInt(score.getText()) + 1)); // mise à jour du compteur de mouvement
            // Mouvement des tuiles
            this.threadMouvement(cube);
            this.threadMouvementCaseDead(fond);
            this.nouvelleCase();
        }
    }

    /**
     * Met à jour la vue de l'interface graphique
     * Cette méthode et aussi utiliser pour la sérialization et le et ce cas le
     * paramètre b est forcément égal à true
     * @param b 
     */
    private void updateTemplate(boolean b) {
        for (int k = 0; k < 3; k++) {
            for (Case c : this.cube.getMultiGrille()[k].getGrille()) {
                // Pour adapter lors de la sérialization
                if (b) {                    
                    c.setPane(new Pane());
                    c.setLabel(new Label());
                    this.positionTuile(c);
                }
                c.getLabel().setText(Integer.toString(c.getValeur()));
                switch (c.getValeur()) {
                    case 2:
                        c.getPane().getStyleClass().add("pane2");
                        c.getLabel().getStyleClass().add("tuile");
                        break;
                    case 4:
                        c.getPane().getStyleClass().add("pane4");
                        c.getLabel().getStyleClass().add("tuile");
                        break;
                    case 8:
                        c.getPane().getStyleClass().add("pane8");
                        c.getLabel().getStyleClass().add("tuile");
                        break;
                    case 16:
                        c.getPane().getStyleClass().add("pane16");
                        c.getLabel().getStyleClass().add("tuile10");
                        break;
                    case 32:
                        c.getPane().getStyleClass().add("pane32");
                        c.getLabel().getStyleClass().add("tuile10");
                        break;
                    case 64:
                        c.getPane().getStyleClass().add("pane64");
                        c.getLabel().getStyleClass().add("tuile10");
                        break;
                    case 128:
                        c.getPane().getStyleClass().add("pane128");
                        c.getLabel().getStyleClass().add("tuile100");
                        break;
                    case 256:
                        c.getPane().getStyleClass().add("pane256");
                        c.getLabel().getStyleClass().add("tuile100");
                        break;
                    case 512:
                        c.getPane().getStyleClass().add("pane512");
                        c.getLabel().getStyleClass().add("tuile100");
                        break;
                    case 1024:
                        c.getPane().getStyleClass().add("pane1024");
                        c.getLabel().getStyleClass().add("tuile1000");
                        break;
                    case 2048:
                        c.getPane().getStyleClass().add("pane2048");
                        c.getLabel().getStyleClass().add("tuile1000");
                        break;
                }
            }
        }
    }

    /**
     * Affiche la nouvelle case dans l'interface graphique et l'ajoute aussi dans
     * la grille (au niveau de la console)
     */
    private void nouvelleCase() {
        this.cube.nouvelleCase();
        this.afficherNouvelleTuile();
    }

    /**
     * Réinitialise le fond, supprime tous les éléments (Tuiles) déjà présents
     */
    private void destroyTemplate() {
        for (int i = 0; i < 3; i++) {
            for (Case c : this.cube.getMultiGrille()[i].getGrille()) {
                fond.getChildren().remove((Node) c.getPane());
            }
        }
        try {
            fond.getChildren().remove((Node) this.lblResultat);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    /**
     * Fin de la partie (victoire ou défaite)
     */
    private boolean finDePartie() {
        if (cube.valeurMax() == OBJECTIF) {            
            lblResultat = new Label();
            lblResultat.setLayoutX(398);
            lblResultat.setLayoutY(250);
            lblResultat.setOpacity(0.9);
            lblResultat.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Image/victoire.gif"))));
            this.fond.getChildren().add(lblResultat);
            bdd.updateScore(OBJECTIF, true);
            return true;           
        } else if (inferieur.partieFinie() && milieu.partieFinie() && superieur.partieFinie() && cube.partieFinie()) {
            lblResultat = new Label();
            lblResultat.setLayoutX(398);
            lblResultat.setLayoutY(250);
            lblResultat.setOpacity(0.9);
            lblResultat.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Image/defaite.gif"))));
            this.fond.getChildren().add(lblResultat);
            bdd.updateScore(1, true);
            return true;
        }
        return false;
    }
    
    /**
     * Méthode pour bouger visuellement les tuiles encore présentes sur le cube
     * @param cube instance de la classe Cube pris en paramètre
     */
    protected void threadMouvement(Cube cube) {
        for (Grille grille : cube.getMultiGrille()) {
            for (Case c  : grille.getGrille()) {
                final int numGrille = grille.getNumGrille();
                Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
                    @Override
                    public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                        // Après mouvement
                        int objectifx = 24 + 18 * numGrille + numGrille * 397 + (c.getX() * tailleX);
                        int objectify = 191 + tailleY * c.getY();
                        // Avant mouvement
                        int x = 24 + 18 * c.getLastGrille() + c.getLastGrille() * 397 + (c.getLastX() * tailleX);
                        int y = 191 + tailleY * c.getLastY();
                        while (x != objectifx || y != objectify) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                            if (x < objectifx) {
                                x += 1; // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                            } else if (x > objectifx) {
                                x -= 1; // si on va vers la gauche, idem en décrémentant la valeur de x
                            }
                            if (y < objectify) {
                                y += 1; // si on va vers le , on modifie la position de la tuile pixel par pixel vers la droite
                            } else if (y > objectify) {
                                y -= 1; // si on va vers le , idem en décrémentant la valeur de x
                            }
                            // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
                            final int varX = x;
                            final int varY = y;
                            Platform.runLater(new Runnable() { // classe anonyme
                                    @Override
                                    public void run() {
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
                th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
                th.setPriority(Thread.MAX_PRIORITY);
                th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
            }
        }  
    }
    
    /**
     * Supprime les tuiles qui ont fusionné
     * @param fond template de l'interface graphique
     */
    protected void threadMouvementCaseDead(Pane fond) {  
        for (Grille grille : this.cube.getMultiGrille()) {
            for (Case c  : grille.getCasesDestroy()) {
                fond.getChildren().remove((Node) c.getPane()); // ICI POUR L4INSTANT COTE VISUEL
            }
            grille.getCasesDestroy().clear();
        }
    }     
}
