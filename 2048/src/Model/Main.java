/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
        
        Grille inferieur = new Grille(0);        
        Grille milieu = new Grille(1);        
        Grille superieur = new Grille(2);
        
        // Tableau des 3 grilles
        Grille[] multiGrille = new Grille[3];
        multiGrille[0] = inferieur; multiGrille[1] = milieu; multiGrille[2] = superieur;
        
        Cube cube = Cube.INSTANCE;
        cube.init(multiGrille);
                
        // ajoute deux cases dans les tableaux de façon aléatoire
        for (int i = 0; i < 2; i++) {
            int random = (int) (Math.random() * 3);
            cube.getMultiGrille()[random].nouvelleCase();
        }
        
        System.out.println(cube);
                
        Scanner sc = new Scanner(System.in);        
        while (!inferieur.partieFinie() || !milieu.partieFinie() || !superieur.partieFinie() || !cube.partieFinie()) {
            System.out.println("Déplacer vers la Droite (d), Gauche (g), Haut (h), Bas (b), Tout à Droite (p), ou Tout à Gauche (u) ?");
            String s = sc.nextLine();
            s.toLowerCase();
            if (!(s.equals("d") || s.equals("droite")
                    || s.equals("g") || s.equals("gauche")
                    || s.equals("h") || s.equals("haut")
                    || s.equals("b") || s.equals("bas")
                    || s.equals("u") /*Etage inf*/
                    || s.equals("p") /*Etage sup*/)) {
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
                    direction = SUPERIEUR;
                } else if (s.equals("u")) {
                    direction = INFERIEUR;
                } else {
                    direction = BAS;
                }
                
                boolean deplacementReussi = cube.deplacement(direction);                    
                if (deplacementReussi) 
                    cube.nouvelleCase();      
                    
                System.out.println(cube);
                if (inferieur.getValeurMax()>=OBJECTIF) cube.victory();
            }
        }
        cube.gameOver();
    }

}
