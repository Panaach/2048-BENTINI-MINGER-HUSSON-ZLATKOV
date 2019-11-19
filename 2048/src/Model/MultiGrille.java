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
public enum MultiGrille implements Parametres{
    INSTANCE;
    
    private MultiGrille mGrille;
    private Grille[] multiGrille;
    
    public void init(Grille[] mGrille) {
        this.mGrille.getMultiGrille()[0] = mGrille[0];
        this.mGrille.getMultiGrille()[1] = mGrille[1];
        this.mGrille.getMultiGrille()[2] = mGrille[2];
    }

    /**
     * @return the multiGrille
     */
    public Grille[] getMultiGrille() {
        return multiGrille;
    }

    /**
     * @param multiGrille the multiGrille to set
     */
    public void setMultiGrille(Grille[] multiGrille) {
        this.multiGrille = multiGrille;
    }
    
    /*public MultiGrille(Grille[] multiGrille) {
        this.multiGrille = new Grille[3];
        this.multiGrille[0] = multiGrille[0];
        this.multiGrille[1] = multiGrille[1];
        this.multiGrille[2] = multiGrille[2];
    }  */    
    
    @Override
    public String toString() {
        int nb = 0;
        int[][] tableau = new int[TAILLE][TAILLE*3];
        for (int k = 0; k < 3; k++) {
            for (Case c : this.getMultiGrille()[k].getGrille()) {
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
    
    /*@Override
    public boolean equals(Object obj) {
        if (obj instanceof MultiGrille) {
            MultiGrille m = (MultiGrille) obj;
            return (this.getMultiGrille()[0].getGrille().equals(m.getMultiGrille()[0].getGrille()) &&
                    this.getMultiGrille()[1].getGrille().equals(m.getMultiGrille()[1].getGrille()) && 
                    this.getMultiGrille()[2].getGrille().equals(m.getMultiGrille()[2].getGrille()));
        } else {
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return this.getMultiGrille()[0].getGrille().size() * 7 + 
                this.getMultiGrille()[1].getGrille().size() * 13 +
                this.getMultiGrille()[2].getGrille().size() * 17;
    }*/
    
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
    
    // converti le hash en un tableau de case
    public Case[][] convertHash(Grille g) {
        HashSet<Case> set = g.getGrille();
        Case[][] convert = new Case[3][3];
        for (Case c : set) {
            convert[c.getX()][c.getY()] = c;
        }
        return convert;
    }
    
    public boolean teleportationEmptyCase(Grille left, Grille right, int compteur) {
        boolean b = false;
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] == null) { 
                        Case fusion = new Case(r[x][y].getX(), r[x][y].getY(), r[x][y].getValeur(),  r[x][y].getNumGrille() + compteur, r[x][y].getNumGrille());
                        fusion.setGrille(left);
                        right.getGrille().remove(r[x][y]);
                        left.getGrille().add(fusion);
                        b = true;
                    }
                }
            }
        }
        return b;
    }
    
    public boolean teleportationSameCase(Grille left, Grille right, int compteur) {
        boolean b = false;
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] != null) { // si ma case dans la grille droite existe alors
                        if (l[x][y].valeurEgale(r[x][y])) { // si ces cases ont la mêmes valeurs alors je fusionne
                            // création d'une nouvelle case
                            Case fusion = new Case(l[x][y].getX(), l[x][y].getY(), l[x][y].getValeur()*2, l[x][y].getNumGrille(), r[x][y].getNumGrille());
                            fusion.setGrille(left);
                            
                            // supprime les anciennes cases qui vont être mis-à-jour
                            left.getGrille().remove(l[x][y]);
                            right.getGrille().remove(r[x][y]);
                            // ajoute la nouvelle case
                            left.getGrille().add(fusion);
                            b = true;
                        } 
                    } else { // la position est disponible donc je tp la case
                        Case fusion = new Case(r[x][y].getX(), r[x][y].getY(), r[x][y].getValeur(),  r[x][y].getNumGrille() + compteur, r[x][y].getNumGrille());
                        fusion.setGrille(left);
                        right.getGrille().remove(r[x][y]);
                        left.getGrille().add(fusion);
                        b = true;
                    }
                }
            }
        }
        return b;
    }
    
    // Autre méthode pour la téléportation
    /*
    public boolean fusionSameCase(Grille right) {        
        HashSet<Case> rightClone = new HashSet<>(); 
        
        // Parcours la grille de DROITE
        right.getGrille().stream().filter((cRight) -> (this.getGrille().contains(cRight))).forEachOrdered((cRight) -> {
            // parcours la grille de GAUCHE
            this.getGrille().stream().filter((cLeft) -> (cLeft.equals(cRight) && cLeft.getValeur() == cRight.getValeur())).forEachOrdered((cLeft) -> {
                rightClone.add(cRight);
                cLeft.setValeur(cLeft.getValeur() * 2);
            }); // une fois trouvé je regarde si elle sont identiques
        }); // si cette case appartient à l'autre grille alors je cherche la case correspondante
        if (!rightClone.isEmpty()) {
            right.getGrille().removeAll(rightClone);
            rightClone.clear();
        } else {
            return false;
        }
        return true;
    }
    
    public boolean fusionEmptyCase(Grille right) throws CloneNotSupportedException {   
        // hashSet pour supprimer par la suite les cases de la grille de droite
        HashSet<Case> rightClone = new HashSet<>();         
        for (Case c : right.getGrille()) { // parcours la grille situé a droite
            // this équivaut à la grille de gauche
            if (!this.getGrille().contains(c)) { // si la case de droite n'est pas dans la grille de gauche en comparant juste le x et le y alors je déplace la case
                // création du clone
                
                c.setGrille(this);
                Case cloned = (Case) c.clone();
                // ajoute la case au nouveau hash
                rightClone.add(c);
                // ajout du clone
                this.getGrille().add(cloned);
                
            }
        } 
        
        if (!rightClone.isEmpty()) {
            right.getGrille().removeAll(rightClone);
            rightClone.clear();
        } else {
            return false;
        }
        return true;
    }
    */
    
    public boolean testFusionSameCase(Grille left, Grille right) {
        Case[][] l = this.convertHash(left);
        Case[][] r = this.convertHash(right);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] != null) { // si ma case dans la grille droite existe alors
                        if (l[x][y].valeurEgale(r[x][y])) { // si ces cases ont la mêmes valeures alors je fusionne
                            return true;
                        } 
                    } else { // la position est disponible donc je tp la case
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    public boolean fusionGauche() {
        boolean b1 = this.teleportationSameCase(this.getMultiGrille()[0], this.getMultiGrille()[1], -1);
        boolean b2 = this.teleportationSameCase(this.getMultiGrille()[1], this.getMultiGrille()[2], -1);
        
        boolean b3 = this.teleportationEmptyCase(this.getMultiGrille()[0], this.getMultiGrille()[1], -1);
        boolean b4 = this.teleportationEmptyCase(this.getMultiGrille()[1], this.getMultiGrille()[2], -1);
        
        return b1 || b2 || b3 || b4;
    }
    
    public boolean fusionDroite() {
        boolean b1 = this.teleportationSameCase(this.getMultiGrille()[2], this.getMultiGrille()[1], 1);
        boolean b2 = this.teleportationSameCase(this.getMultiGrille()[1], this.getMultiGrille()[0], 1);
        
        boolean b3 = this.teleportationEmptyCase(this.getMultiGrille()[2], this.getMultiGrille()[1], 1);
        boolean b4 = this.teleportationEmptyCase(this.getMultiGrille()[1], this.getMultiGrille()[0], 1);
        
        return b1 || b2 || b3 || b4;
    }
    
    public boolean partieFinie() {
        if (this.getMultiGrille()[0].getGrille().size() < TAILLE * TAILLE || 
                this.getMultiGrille()[1].getGrille().size() < TAILLE * TAILLE || 
                this.getMultiGrille()[2].getGrille().size() < TAILLE * TAILLE) {
            return false;
        } else {
            System.out.println(this.testFusionSameCase(this.getMultiGrille()[0], this.getMultiGrille()[1]) &&
                    this.testFusionSameCase(this.getMultiGrille()[1], this.getMultiGrille()[2]));
            return !this.testFusionSameCase(this.getMultiGrille()[0], this.getMultiGrille()[1]) &&
                    !this.testFusionSameCase(this.getMultiGrille()[1], this.getMultiGrille()[2]);  
            // si je peux le faire d'un côté alors je peux le faire de l'autre
        }
    }
    
    public int valeurMax() {
        return Math.max(this.getMultiGrille()[0].getValeurMax(), 
                Math.max(this.getMultiGrille()[1].getValeurMax(), this.getMultiGrille()[2].getValeurMax()));
    }
    
    public void gameOver() {
        System.out.println("La partie est finie. Votre score est " + this.valeurMax());
        System.exit(1);
    }
    
    public void victory() {
        System.out.println("Bravo ! Vous avez atteint " + this.valeurMax());
        System.exit(0);
    }
}