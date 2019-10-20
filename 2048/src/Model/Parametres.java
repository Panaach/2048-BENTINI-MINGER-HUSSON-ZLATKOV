/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author Sylvain
 */
public interface Parametres {
    static final int HAUT = 1;
    static final int DROITE = 2;
    static final int BAS = -1;
    static final int GAUCHE = -2;
    static final int TAILLE = 3;
    static final int OBJECTIF = 2048;
    static final int FULLRIGHT = 5;
    static final int FULLLEFT = -5;
}


      /*  System.out.println("objectifx=" + objectifx);
        Task task = new Task<Void>() { // on définit une tâche parallèle pour mettre à jour la vue
            @Override
            public Void call() throws Exception { // implémentation de la méthode protected abstract V call() dans la classe Task

                for (int j = 0; j < 27; j++) {
                    int coordX = (int) listP.get(j).getLayoutX();
                    int coordY = (int) listP.get(j).getLayoutY();
                    //  Pane p = listP.get(j);

                    while (coordX != objectifx && coordY != objectify) { // si la tuile n'est pas à la place qu'on souhaite attendre en abscisse
                        if (coordX < (double) objectifx) {
                            listP.get(j).setLayoutX(coordX + 1); // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                        } else {
                            listP.get(j).setLayoutX(coordX - 1); // si on va vers la gauche, idem en décrémentant la valeur de x
                        }

                        /*  if (coordY < (double) objectify) {
                            listP.get(j).setLayoutY(coordY + 1); // si on va vers la droite, on modifie la position de la tuile pixel par pixel vers la droite
                        } else {
                            listP.get(j).setLayoutY(coordY - 1); // si on va vers la gauche, idem en décrémentant la valeur de x
                        }
                        /* Pane panneauADeplacer;
                                for (Pane p:listP) {
                                    if ()
                                }*/
                        // Platform.runLater est nécessaire en JavaFX car la GUI ne peut être modifiée que par le Thread courant, contrairement à Swing où on peut utiliser un autre Thread pour ça
               /*         Platform.runLater(new Runnable() { // classe anonyme
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

                }

                return null; // la méthode call doit obligatoirement retourner un objet. Ici on n'a rien de particulier à retourner. Du coup, on utilise le type Void (avec un V majuscule) : c'est un type spécial en Java auquel on ne peut assigner que la valeur null
            } // end call

        };
        Thread th = new Thread(task); // on crée un contrôleur de Thread
        th.setDaemon(true); // le Thread s'exécutera en arrière-plan (démon informatique)
        th.start(); // et on exécute le Thread pour mettre à jour la vue (déplacement continu de la tuile horizontalement)
*/