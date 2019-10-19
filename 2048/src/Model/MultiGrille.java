/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
    
    /*public void updateGrille() {
        for (int i = 0; i < 3; i++) {
            this.multiGrille[0].setGrille(this.multiGrille[i].getGrille());;
        }
    }*/
    
    // présice quelle méthode choisir
    public boolean choixDirection(int direction) throws CloneNotSupportedException {
        boolean b;
        switch (direction) {
            case FULLLEFT:
                b = this.fusionGauche();
                //if (b) this.updateGrille();
                break;
            default:
                b = this.fusionDroite();
                //if (b) this.updateGrille();
                break;                        
        }
        return b;
    }
    
    /*public void fusionSameCase(Grille left, Grille right) {        
        HashSet<Case> rightClone = new HashSet<>(); 
        
        // Parcours la grille de DROITE        
        for (Case cRight : right.getGrille()) { 
            // si cette case appartient à l'autre grille alors je cherche la case correspondante
            if (left.getGrille().contains(cRight)) {
                // parcours la grille de GAUCHE                
                for (Case cLeft : left.getGrille()) {
                    // une fois trouvé je regarde si elle sont identiques
                    if (cLeft.equals(cRight) && cLeft.getValeur() == cRight.getValeur()) {
                        rightClone.add(cRight);
                        cLeft.setValeur(cLeft.getValeur() * 2);
                    }
                } 
            }
        } 
        if (!rightClone.isEmpty()) {
            right.getGrille().removeAll(rightClone);
            rightClone.clear();
        }
    }
    
    public void fusionEmptyCase(Grille left, Grille right) {   
        HashSet<Case> rightClone = new HashSet<>(); 
        for (Case c : right.getGrille()) {
            if (!left.getGrille().contains(c)) {
                rightClone.add(c);
                left.getGrille().add(c);
            }
        } 
        right.getGrille().removeAll(rightClone);
        rightClone.clear();
    }*/
    
    public boolean testFusionSameCase(Grille left, Grille right) {
        HashSet<Case> sameCase = new HashSet<>(); 
        
        // Parcours la grille de DROITE        
        for (Case cRight : right.getGrille()) { 
            // si cette case appartient à l'autre grille alors je cherche la case correspondante
            if (left.getGrille().contains(cRight)) {
                // parcours la grille de GAUCHE                
                for (Case cLeft : left.getGrille()) {
                    // une fois trouvé je regarde si elle sont identiques
                    if (cLeft.equals(cRight) && cLeft.getValeur() == cRight.getValeur()) {
                        sameCase.add(cRight);
                    }
                } 
            }
        } 
        System.out.println("Test partie fini fusion " + sameCase);
        if (!sameCase.isEmpty())
            return false;
        else 
            return true;
    }
    
    public boolean fusionGauche() throws CloneNotSupportedException {
        
        /*boolean b1 = this.multiGrille[0].fusionSameCase(this.multiGrille[1]);
        boolean b2 = this.multiGrille[1].fusionSameCase(this.multiGrille[2]);*/
        
        boolean b3 = this.multiGrille[1].fusionEmptyCase(this.multiGrille[2]);
        if (b3) this.multiGrille[2].setGrille(this.multiGrille[2].getGrille());
        boolean b4 = this.multiGrille[0].fusionEmptyCase(this.multiGrille[1]);
        if (b3) this.multiGrille[1].setGrille(this.multiGrille[1].getGrille());
        
        return b3 || b4;
    }
    
    public boolean fusionDroite() throws CloneNotSupportedException {
        final Grille[] multiGrilleClone = this.multiGrille.clone();
        
        /*this.fusionSameCase(this.multiGrille[2], this.multiGrille[1]); 
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[0]); */ 
        
        this.multiGrille[1].fusionEmptyCase(this.multiGrille[0]);
        this.multiGrille[2].fusionEmptyCase(this.multiGrille[1]);
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
    
    public String nbCaseDansGrille() {
        return "\u001B[33mGrille 0 : " + String.valueOf(this.multiGrille[0].getGrille().size()) +
                ", Grille 1 : " + String.valueOf(this.multiGrille[1].getGrille().size()) +
                ", Grille 2 : " + String.valueOf(this.multiGrille[2].getGrille().size()) + "\u001B[0m";
    }
    
    public int valeurMax() {
        return Math.max(this.multiGrille[0].getValeurMax(), 
                Math.max(this.multiGrille[1].getValeurMax(), this.multiGrille[2].getValeurMax()));
    }
    
    public void gameOver() {
        System.out.println("La partie est finie. Votre score est " + this.valeurMax());
        System.exit(1);
    }
    
    public void victory() {
        System.out.println("Bravo ! Vous avez atteint " + this.valeurMax());
        System.exit(0);
    }
    
    public String yo() {
        String res = "\u001B[36m";
        for (int j = 0;j<3;j++) {

            ArrayList<Case> test = new ArrayList<>(); 
            Iterator value = this.multiGrille[j].getGrille().iterator();
            while (value.hasNext()) { 
                test.add((Case) value.next()); 
            } 
            System.out.println(test);
            res += "Grille " + String.valueOf(j);
            for(int i =0; i < test.size();i++) {
                Case c = test.get(i);
                test.remove(i);
                if (test.contains(c))
                    res += "\u001B[36m" + c + "\u001B[0m";
            }
            
            res += "\u001B[0m\n";
        }
        return res;
    }
}