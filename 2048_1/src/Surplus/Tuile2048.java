/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Surplus;

import Model.Case;
import Model.Grille;
import Model.Parametres;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author Antoine le meilleur
 */
public class Tuile2048 implements Tuile, Parametres{
    private Grille grille;
    
    public Tuile2048 (Grille grille) {
        this.grille = (Grille) grille.clone();
    }

    // mouvement pour une tuile
    @Override
    public void threadMovement() { 
        //System.out.println("MOUVEMENT GRAPHIQUE");
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
            
            /*task.setOnSucceeded(e -> {
                Boolean result = (Boolean) task.getValue();
            });*/
            
            
            Thread th = new Thread(task); // on crée un contrôleur de Thread
            th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
            //th.setPriority(Thread.MAX_PRIORITY);
            th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
        }
    }    
    
    @Override
    public void threadMovementCaseDead(Pane fond) {  
        for (Case c  : grille.getCasesDestroy()) {
            final int fi = grille.getNumGrille();
            /*Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
                @Override
                public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task
                    // Après mouvement
                    int objectifx = 24 + 18 * fi + fi * 397 + (c.getX() * tailleX);
                    int objectify = 191 + tailleY * c.getY();
                    // Avant mouvement
                    int x = 24 + 18 * fi + fi * 397 + (c.getLastX() * tailleX);
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
                                    //System.out.println(c.getPane());
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
            //System.out.println("SORTI DU TYASK");
            Thread th = new Thread(task); // on crée un contrôleur de Thread
            th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
            th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)*/
            fond.getChildren().remove((Node) c.getPane()); // ICI POUR L4INSTANT COTE VISUEL
        }
        this.grille.getCasesDestroy().clear();
    }     
}
    /*
    
                            System.out.println("OX:"+objectifx);
                            System.out.println("OY:"+objectify);
                            System.out.println("X:"+x);
                            System.out.println("Y:"+y);
    */
