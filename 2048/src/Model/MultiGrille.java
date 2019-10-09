/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

/**
 *
 * @author Panach
 */
public class MultiGrille implements Parametres{
    
    private Grille[] multiGrille;
    
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
        for (int k = 0; k < 3; k++) {
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
    // déplacer les tuiles dans les cases vides
    // fusion des cases ensuite
    
    public void fusionSameCase(Grille left, Grille right) {
        HashSet<Case> rightClone = new HashSet<>();
        // parcours la grille de gauche, prends une case au hasard et regarde si cette case appartient à la grille de droite
        left.getGrille().stream().filter((caseGauche) -> (right.getGrille().contains(caseGauche))).map((caseGauche) -> 
            caseGauche).forEach((Case caseGauche) -> {
                
                // parcours la grille de droite, prends une case au hasard et regarde si cette case appartient à la grille de gauche
                right.getGrille().stream().filter((caseDroite) -> (left.getGrille().contains(caseDroite))).map((caseDroite) -> 
                    caseDroite).forEach((caseDroite) -> {
                        // ajoute les cases identiques dans un hash pour les supprimer apres
                        if (caseGauche.equals(caseDroite) && caseGauche.valeurEgale(caseDroite)) {   
                            rightClone.add(caseDroite);
                            caseGauche.setValeur(caseGauche.getValeur()*2);
                        }
                    });
                
                rightClone.forEach((c) -> {
                    right.getGrille().remove(c);
                }); 
            });
    }
    
    public void fusionEmptyCase(Grille left, Grille right) {      
        HashSet<Case> rightClone = new HashSet<>();          
        right.getGrille().stream().filter((caseDroite) -> (!left.getGrille().contains(caseDroite))).map((caseDroite) -> 
            caseDroite).forEach((caseDroite) -> {
                rightClone.add(caseDroite);
                left.getGrille().add(caseDroite);
            }); 
        
        rightClone.forEach((c) -> {
            right.getGrille().remove(c);
        });
    }
    
    public boolean fusionGauche() {
        Grille[] test = this.multiGrille;
        this.fusionEmptyCase(this.multiGrille[1], this.multiGrille[2]);  
        this.fusionEmptyCase(this.multiGrille[0], this.multiGrille[1]); 
            
        this.fusionSameCase(this.multiGrille[0], this.multiGrille[1]);
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[2]); 
        
        System.out.println(test[0] + " " + test[1] + " " + test[2]);
        return !Arrays.equals(test, this.multiGrille); 
    }
    
    public boolean fusionDroite() {
        Grille[] test = this.multiGrille;
        this.fusionEmptyCase(this.multiGrille[1], this.multiGrille[0]); 
        this.fusionEmptyCase(this.multiGrille[2], this.multiGrille[1]);  
            
        this.fusionSameCase(this.multiGrille[2], this.multiGrille[1]); 
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[0]);
        
        System.out.println(test[0] + " " + test[1] + " " + test[2]);
        return !Arrays.equals(test, this.multiGrille); 
    }
}
