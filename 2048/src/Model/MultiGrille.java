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
    
    private Grille[] multiGrille;   
    private boolean gLeft, gMiddle, gRight;
    
    public MultiGrille(Grille g, Grille m, Grille r) {
        this.multiGrille = new Grille[3];
        this.multiGrille[0] = g;
        this.multiGrille[1] = m;
        this.multiGrille[2] = r;
    }      
    
    @Override
    public String toString() {
        int nb = 0;
        int[][] tableau = new int[TAILLE][TAILLE*3];
        //System.out.println("SIZE : " + tableau.length);
        for (int k = 0; k < 3; k++) {
            //System.out.println(this.troisGrille[k]);
            for (Case c : this.multiGrille[k].getGrille()) {
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

    /**
     *
     * @param direction
     * @return
     */
    public boolean choixDirection(int direction) {
        boolean b;
        switch (direction) {
            case FULLLEFT:
                b = this.fusionGauche();
                break;
            default:
                b = this.fusionDroite();
                break;                        
        }
        return b;
    }
    
    public boolean fusionGauche() {
        //PAS OUBLIER LE TB DE BOOL
        int k = 0;
        boolean fusionSuccess = false;
        
        for (int i = 0; i < 2; i++) {
            /*HashSet<Case> gauche = g[k].getGrille();
            HashSet<Case> droite = g[k+1].getGrille();*/

            for (Case caseDroite : this.multiGrille[k].getGrille()) {
                System.out.println("case droite " + caseDroite);
                // si le second set à la même élément
                if (this.multiGrille[k+1].getGrille().contains(caseDroite)) {
                    caseDroite.setValeur(caseDroite.getValeur()*2);
                    this.multiGrille[k+1].getGrille().remove(caseDroite);
                    fusionSuccess = true;
                } else {
                    // Regarde si les deux éléments ont la même position dans les grilles
                    boolean isInsideBoth = false;
                    for (Case caseGauche : this.multiGrille[k+1].getGrille()) {
                        if (caseDroite.getX() == caseGauche.getX() && caseDroite.getY() == caseGauche.getY()) {
                            isInsideBoth = true; 
                            break;
                        }
                    }                
                    //si non alors la je peux fusionner la case puisqu'elle est vide
                    if(!isInsideBoth) {
                        this.multiGrille[k].getGrille().add(caseDroite);
                        this.multiGrille[k+1].getGrille().remove(caseDroite);
                        fusionSuccess = true;
                    }
                }
                // fin if
            }
            k++;
        }
        return fusionSuccess;
    }
    
    public boolean fusionDroite() {
        return false;
    }
}
