/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.HashSet;

/**
 *
 * @author Panach
 */
public class MultiGrille implements Parametres{
    
    private Grille[] troisGrille;   
    private boolean gLeft, gMiddle, gRight;
    
    public MultiGrille(Grille g, Grille m, Grille r) {
        this.troisGrille = new Grille[3];
        this.troisGrille[0] = g;
        this.troisGrille[1] = m;
        this.troisGrille[2] = r;
    }    

    /**
     * @return the gLeft
     */
    public boolean isgLeft() {
        return gLeft;
    }

    /**
     * @return the gMiddle
     */
    public boolean isgMiddle() {
        return gMiddle;
    }

    /**
     * @return the gRight
     */
    public boolean isgRight() {
        return gRight;
    }   
    
    @Override
    public String toString() {
        int nb = 0;
        int[][] tableau = new int[TAILLE][TAILLE*3];
        //System.out.println("SIZE : " + tableau.length);
        for (int k = 0; k < 3; k++) {
            //System.out.println(this.troisGrille[k]);
            for (Case c : this.troisGrille[k].getGrille()) {
                tableau[c.getY()][c.getX()+nb] = c.getValeur();
            }
            nb += TAILLE;
        }
        
        String result = "";
        for (int i = 0 ; i < TAILLE ; i++) {
            result += "[ ";
            for (int j = 0; j < TAILLE*3; j++) {
                if (j == TAILLE || j == TAILLE*2)
                    result += "]  [ ";
                result += tableau[i][j] + " ";
            }
            result += "]\n";
        }
        return result;
    }
    
    // présice quelle méthode choisir
    public void choixDirection(int direction) {
        switch (direction) {
            case FULLLEFT:
                this.fusionCaseGauche(troisGrille);
            case FULLRIGHT:
                this.fusionCaseDroite(troisGrille);
                break;                        
        }
    }
    
    public static boolean[] fusionCaseGauche(Grille[] g) {
        //PAS OUBLIER LE TB DE BOOL
        int k = 0, l = 0;
        boolean[] tabBool = new boolean[3];
        
        for (int i = 0; i < 1; i++) {
            HashSet<Case> gauche = g[k].getGrille();
            HashSet<Case> droite = g[k+1].getGrille();

            for (Case caseDroite : droite) {
                // if second set has the current element
                if (droite.contains(caseDroite)) {
                    caseDroite.setValeur(caseDroite.getValeur()*2);
                    droite.remove(caseDroite);
                    tabBool[k+1] = true;
                } else {
                    // Regarde si les deux éléments ont la même position dans les grilles
                    boolean isInsideBoth = false;
                    for (Case caseGauche : droite) {
                        if (caseDroite.getX() == caseGauche.getX() && caseDroite.getY() == caseGauche.getY()) {
                            isInsideBoth = true; 
                            break;
                        }
                    }                
                    //si non alors la je peux fusionner la case puisqu'elle est vide
                    if(!isInsideBoth) {
                        gauche.add(caseDroite);
                        droite.remove(caseDroite);
                        tabBool[k+1] = true;
                    }
                }
            }
            k++;
        }
        return tabBool;
    }
    
    public boolean[] fusionCaseDroite(Grille[] g) {
        return null;
    }
}
