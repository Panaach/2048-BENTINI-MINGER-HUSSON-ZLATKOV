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
        // TODO code application logic here
        Grille left = new Grille();
        boolean bl = left.nouvelleCase();
        bl = left.nouvelleCase();
        //System.out.print(left + "\n\n\n\n");
        
        Grille middle = new Grille();
        boolean bm = middle.nouvelleCase();
        bm = middle.nouvelleCase();
        //System.out.print(middle);
        
        Grille right = new Grille();
        boolean br = right.nouvelleCase();
        br = right.nouvelleCase();
        
        MultiGrille mGrille = new MultiGrille(left, middle, right);
        System.out.println(mGrille);
        
        Scanner sc = new Scanner(System.in);
        /*System.out.println("X:");
        int x= sc.nextInt();
        System.out.println("Y:");
        int y= sc.nextInt();
        System.out.println("Valeur:");
        int valeur= sc.nextInt();
        Case c = new Case(x,y,valeur);
        g.getGrille().remove(c);
        System.out.println(g);*/
        
        while (!left.partieFinie()) {
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
                } else if (s.equals("u")) {
                    direction = FULLRIGHT;
                } else if (s.equals("p")) {
                    direction = FULLLEFT;
                } else {
                    direction = BAS;
                }
                
                boolean bleft = false, bmiddle = false, bright = false;
                
                if (direction == FULLRIGHT || direction == FULLLEFT) {
                    //boolean[] tabBool = MultiGrille.choixDirection(direction);                    
                } else {
                    bleft = left.lanceurDeplacerCases(direction);
                    bmiddle = middle.lanceurDeplacerCases(direction);
                    bright = right.lanceurDeplacerCases(direction);
                }
                
                if (bleft)
                    bl = left.nouvelleCase();
                else if (bmiddle) 
                    bm = middle.nouvelleCase();
                else if (bright)
                    br = right.nouvelleCase();   
                
                if (!bl && !bm && !br) left.gameOver();
                        
                    
                System.out.println(mGrille);
                if (left.getValeurMax()>=OBJECTIF) left.victory();
            }
        }
        left.gameOver();
        /*
        // Bout de code pour tester manuellement si une grille contient une case ou pas
        Scanner sc = new Scanner(System.in);
        System.out.println("x :");
        int x = sc.nextInt();
        System.out.println("y :");
        int y = sc.nextInt();
        Case c = new Case(x, y, 2);
        Case c2 = new Case(x, y, 4);
        System.out.println("test1=" + g.getGrille().contains(c));
        System.out.println("test2=" + g.getGrille().contains(c2));
         */
    }

}
