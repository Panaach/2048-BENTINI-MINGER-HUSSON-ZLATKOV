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
    public boolean choixDirection(int direction) {
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
                            System.out.println("\u001B[31m" + caseGauche + "\u001B[0m"); 
                            rightClone.add(caseDroite);
                            // fusionne une case de la grille gauche
                            caseGauche.setValeur(caseGauche.getValeur()*2);
                        }
                    });
                System.out.println("Taille clone " + rightClone.size());
                /*Iterator value = rightClone.iterator(); 
                while (value.hasNext()) { 
                    Case c = (Case) value.next();
                    right.getGrille().remove((Case) c);
                } */
                right.getGrille().removeAll(rightClone);
                rightClone.clear();
            });
    }
    
    public void fusionEmptyCase(Grille left, Grille right) {   
        HashSet<Case> rightClone = new HashSet<>(); 
        Iterator value = right.getGrille().iterator(); 
        while (value.hasNext()) { 
            Case c = (Case) value.next();
            if (!left.getGrille().contains(c)) {
                rightClone.add(c);
                left.getGrille().add(c);
                System.out.println("\u001B[35m" + c + "\u001B[0m");
            }
        } 
        right.getGrille().removeAll(rightClone);
        rightClone.clear();
        /*HashSet<Case> rightClone = new HashSet<>();          
        right.getGrille().stream().filter((caseDroite) -> (!left.getGrille().contains(caseDroite))).map((caseDroite) -> 
            caseDroite).forEach((caseDroite) -> {
                rightClone.add(caseDroite);
                            System.out.println("\u001B[35m" + caseDroite + "\u001B[0m"); 
                left.getGrille().add(caseDroite);
            }); 
                System.out.println("Taille clone " + rightClone.size());
        
        right.getGrille().removeAll(rightClone);
        rightClone.clear();
        /*rightClone.forEach((c) -> {
            right.getGrille().remove(c);
        });*/
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
        
        //System.out.println("[" + left.getNumGrille() + "," + right.getNumGrille() + "] clone" + sameCase);
        if (!sameCase.isEmpty())
            return false;
        else 
            return true;
    }
    
    public String nbCaseDansGrille() {
        return "\u001B[33mGrille 0 : " + String.valueOf(this.multiGrille[0].getGrille().size()) +
                ", Grille 1 : " + String.valueOf(this.multiGrille[1].getGrille().size()) +
                ", Grille 2 : " + String.valueOf(this.multiGrille[2].getGrille().size()) + "\u001B[0m";
    }
    
    public boolean fusionGauche() {
        final Grille[] multiGrilleClone = this.multiGrille.clone(); 
        // ordre la sur
        this.fusionSameCase(this.multiGrille[0], this.multiGrille[1]);
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[2]);
        
        this.fusionEmptyCase(this.multiGrille[1], this.multiGrille[2]);
        this.fusionEmptyCase(this.multiGrille[0], this.multiGrille[1]);  
        
        return !multiGrilleClone.equals(this.multiGrille);
    }
    
    public boolean fusionDroite() {
        final Grille[] multiGrilleClone = this.multiGrille.clone();
        // ordre la sur
        this.fusionSameCase(this.multiGrille[2], this.multiGrille[1]); 
        this.fusionSameCase(this.multiGrille[1], this.multiGrille[0]);  
        
        this.fusionEmptyCase(this.multiGrille[1], this.multiGrille[0]);
        this.fusionEmptyCase(this.multiGrille[2], this.multiGrille[1]); 
        
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