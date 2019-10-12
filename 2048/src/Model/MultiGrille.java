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
    
    public MultiGrille(Grille[] multiGrille) {
        this.multiGrille = new Grille[3];
        this.multiGrille[0] = multiGrille[0];
        this.multiGrille[1] = multiGrille[1];
        this.multiGrille[2] = multiGrille[2];
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
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MultiGrille) {
            MultiGrille m = (MultiGrille) obj;
            return (this.multiGrille[0].getGrille().equals(m.multiGrille[0].getGrille()) &&
                    this.multiGrille[1].getGrille().equals(m.multiGrille[1].getGrille()) && 
                    this.multiGrille[2].getGrille().equals(m.multiGrille[2].getGrille()));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return this.multiGrille[0].getGrille().size() * 7 + 
                this.multiGrille[1].getGrille().size() * 13 +
                this.multiGrille[2].getGrille().size() * 17;
    }
    
    // présice quelle méthode choisir
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
                            // fusionne une case de la grille gauche
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
    
    public boolean testFusionSameCase(Grille left, Grille right) {
        HashSet<Case> sameCase = new HashSet<>();
        // parcours la grille de gauche, prends une case au hasard et regarde si cette case appartient à la grille de droite
        left.getGrille().stream().filter((caseGauche) -> (right.getGrille().contains(caseGauche))).map((caseGauche) -> 
            caseGauche).forEach((Case caseGauche) -> {
                
                // parcours la grille de droite, prends une case au hasard et regarde si cette case appartient à la grille de gauche
                right.getGrille().stream().filter((caseDroite) -> (left.getGrille().contains(caseDroite))).map((caseDroite) -> 
                    caseDroite).forEach((Case caseDroite) -> {
                        // ajoute les cases identiques dans un hash pour les supprimer apres
                        if (caseGauche.equals(caseDroite) && caseGauche.valeurEgale(caseDroite)) { 
                            sameCase.add(caseDroite);
                        }
                    });
                
            });
        
        System.out.println("[" + left.getNumGrille() + "," + right.getNumGrille() + "] clone" + sameCase);
        if (!sameCase.isEmpty())
            return false;
        else 
            return true;
    }
    
    public boolean fusionGauche() {
        final Grille[] multiGrilleClone = this.multiGrille.clone(); 
             
        this.fusionSameCase(this.multiGrille[0], this.multiGrille[1]);
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[2]);
        
        this.fusionEmptyCase(this.multiGrille[0], this.multiGrille[1]); 
        this.fusionEmptyCase(this.multiGrille[1], this.multiGrille[2]); 
        
        return !multiGrilleClone.equals(this.multiGrille);
    }
    
    public boolean fusionDroite() {
        final Grille[] multiGrilleClone = this.multiGrille.clone();
            
        this.fusionSameCase(this.multiGrille[2], this.multiGrille[1]); 
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[0]);  
        
        this.fusionEmptyCase(this.multiGrille[2], this.multiGrille[1]); 
        this.fusionEmptyCase(this.multiGrille[1], this.multiGrille[0]);
        
        return !multiGrilleClone.equals(this.multiGrille);
    }
    
    public boolean partieFinie() {
        if (this.multiGrille[0].getGrille().size() < TAILLE * TAILLE || 
                this.multiGrille[1].getGrille().size() < TAILLE * TAILLE || 
                this.multiGrille[2].getGrille().size() < TAILLE * TAILLE) {
            return false;
        } else {
            return this.testFusionSameCase(this.multiGrille[0], this.multiGrille[1]) &&
                    this.testFusionSameCase(this.multiGrille[1], this.multiGrille[2]) &&
                    this.testFusionSameCase(this.multiGrille[1], this.multiGrille[0]) &&
                    this.testFusionSameCase(this.multiGrille[2], this.multiGrille[1]);                                 
        }
    }
    
    public int valeurMax() {
        return Math.max(this.multiGrille[0].getValeurMax(), 
                Math.max(this.multiGrille[1].getValeurMax(), this.multiGrille[2].getValeurMax()));
    }
    
    public void gameOver() {
        System.out.println("La partie est finie. Votre score est " + this.valeurMax());
        System.exit(1);
    }
}