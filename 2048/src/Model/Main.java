/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sylvain
 */
public class Main implements Parametres {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Grille left = new Grille(0);
        
        Grille middle = new Grille(1);
        
        Grille right = new Grille(2);
        
        // Tableau des 3 grilles
        Grille[] multiGrille = new Grille[3];
        multiGrille[0] = left; multiGrille[1] = middle; multiGrille[2] = right;
        
        // ajoute deux cases dans les tableaux de façon aléatoire
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            multiGrille[random].nouvelleCase();
        }
        
        //MultiGrille mGrille = new MultiGrille(multiGrille);
        MultiGrille mGrille = MultiGrille.INSTANCE;
        System.out.println(mGrille);
                
        Scanner sc = new Scanner(System.in);
        
        while (!left.partieFinie() || !middle.partieFinie() || !right.partieFinie() || !mGrille.partieFinie()) {
            System.out.println("Déplacer vers la Droite (d), Gauche (g), Haut (h), Bas (b), Tout à Droite (p), ou Tout à Gauche (u) ?");
            String s = sc.nextLine();
            s.toLowerCase();
            if (!(s.equals("d") || s.equals("droite")
                    || s.equals("g") || s.equals("gauche")
                    || s.equals("h") || s.equals("haut")
                    || s.equals("b") || s.equals("bas")
                    || s.equals("u") /*Tout à gauche*/
                    || s.equals("p") /*Tout à droite*/)) {
                System.out.println("Vous devez écrire d pour Droite, g pour Gauche, h pour Haut, b pour Bas, p pour tout à droite ou u pour tout à gauche.");
            } else {
                int direction;
                if (s.equals("d") || s.equals("droite")) {
                    direction = DROITE;
                } else if (s.equals("g") || s.equals("gauche")) {
                    direction = GAUCHE;
                } else if (s.equals("h") || s.equals("haut")) {
                    direction = HAUT;
                } else if (s.equals("p")) {
                    direction = FULLRIGHT;
                } else if (s.equals("u")) {
                    direction = FULLLEFT;
                } else {
                    direction = BAS;
                }
                
                if (direction == FULLRIGHT || direction == FULLLEFT) {
                    boolean fusionSuccess = mGrille.choixDirection(direction); 
                    
                    if (fusionSuccess) {
                        // Tableau d'entiers comportant l'index des grilles
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
                        /*if (grillePossible.isEmpty())
                            multiGrille[0].gameOver(); // peu importe la grille sélectionner */                   
                    }
                } else {
                    // test si on peut déplacer une case sur les 3 grilles
                    boolean b0 = multiGrille[0].lanceurDeplacerCases(direction);
                    boolean b1 = multiGrille[1].lanceurDeplacerCases(direction);
                    boolean b2 = multiGrille[2].lanceurDeplacerCases(direction);
                    // si une case des 3 grilles à bouger alors on ajoute une case sur une des 3 grilles
                    if (b0 || b1 || b2) {
                        // Tableau d'entiers comportant l'index des grilles
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
                        /*if (grillePossible.isEmpty())
                            mGrille.gameOver();  */                
                    }
                }                     
                    
                System.out.println(mGrille);
                if (left.getValeurMax()>=OBJECTIF) mGrille.victory();
            }
        }
        mGrille.gameOver();
    }

}
