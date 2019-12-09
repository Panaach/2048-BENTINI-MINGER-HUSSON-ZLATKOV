/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashSet;

public enum Cube implements Parametres, Cloneable, java.io.Serializable{
    
    INSTANCE;
    
    private Grille[] multiGrille;
    
    /**
     * <h3>Singleton</h3>
     */
    private Cube() {
        multiGrille = new Grille[3];
    } // protection

    
    /**
    * @param mGrille pour initialiser le singleton (Tableau de 3 Grille) 
    */
    public void init(Grille[] mGrille) {
        this.multiGrille[0] = mGrille[0];
        this.multiGrille[1] = mGrille[1];
        this.multiGrille[2] = mGrille[2];
    }
    
    public static Cube getInstance() {
        return INSTANCE;
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
    
    /**
     * <h3>Converti HashSet en tableau de Case à deux dimensions</h3>
     * 
     * @param g prend en paramètre une grille
     * @return un tableau de Case à deux dimensions
     */
    private Case[][] convertHash(Grille g) {
        HashSet<Case> set = g.getGrille();
        Case[][] convert = new Case[3][3];
        for (Case c : set) {
            convert[c.getX()][c.getY()] = c;
        }
        return convert;
    }    
    
    /**
     * Contrôle une seconde fois s'il n'est pas possible de déplacer une case dans
     * une case de la grille vide
     * 
     * @param etageInf 
     * @param etageSup
     * @param compteur
     * @return 
     */
    private boolean verificationDeplacementEtage(Grille etageInf, Grille etageSup, int compteur) { // de base se fait de la droite vers la gauche (<-)
        boolean b = false;
        Case[][] l = this.convertHash(etageInf);
        Case[][] r = this.convertHash(etageSup);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] == null) {                         
                        Case fusion = (Case) r[x][y].clone();
                        fusion.setNumGrille(fusion.getNumGrille() + compteur);
                        fusion.setGrille(etageInf);
                        fusion.setLastX(fusion.getX());
                        fusion.setLastY(fusion.getY());
                        // ne peas mettre lastGrille : sinon le mouvement se fera avec une grille en moins !
                        etageInf.getGrille().add(fusion); // ajout de la nouvelle case
                                              
                        etageSup.getGrille().remove(r[x][y]);
                        b = true;
                    } 
                }
            }
        }
        return b;
    }
    
    /**
     * <h3>Déplacement d'étage du cube</h3>
     * Permet de se déplacer entre deux étages du cube (fusionne la partie 
     * supérieure dans la partie inférieure)
     * 
     * @param etageInf
     * @param etageSup
     * @param compteur
     * @return 
     */
    private boolean deplacementEtage(Grille etageInf, Grille etageSup, int compteur) { // de base se fait de la droite vers la gauche (<-)
        boolean b = false;
        Case[][] l = this.convertHash(etageInf);
        Case[][] r = this.convertHash(etageSup);
        
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (r[x][y] != null) { // si ma case dans la grille droite existe alors
                    if (l[x][y] != null) { // si ma case dans la grille droite existe alors
                        if (l[x][y].valeurEgale(r[x][y])) { // si ces cases ont la mêmes valeurs alors je fusionne
                            Case fusion = (Case) l[x][y].clone();
                            fusion.setValeur(fusion.getValeur()*2); // Fusion
                            fusion.setGrille(etageInf);
                            fusion.setLastGrille(r[x][y].getNumGrille()); // NEW
                            fusion.setLastX(fusion.getX());
                            fusion.setLastY(fusion.getY());

                            etageSup.getCasesDestroy().add(r[x][y]);
                            // supprime les anciennes cases qui vont être mis-à-jour
                            etageInf.getGrille().remove(l[x][y]);
                            etageSup.getGrille().remove(r[x][y]);
                            // ajoute la nouvelle case
                            etageInf.getGrille().add(fusion);
                            b = true;
                        }  else { // Nécessaire pour éviter les mouvements de vielles coordonnées, il faut donc les mettre à jour
                            l[x][y].setLastX(l[x][y].getX());
                            l[x][y].setLastY(l[x][y].getY());  
                            
                            r[x][y].setLastX(r[x][y].getX());
                            r[x][y].setLastY(r[x][y].getY()); 
                        }
                    } else { // la position est disponible donc je tp la case
                        Case fusion = (Case) r[x][y].clone();
                        fusion.setNumGrille(fusion.getNumGrille() + compteur); // change le numéro de la grille en fonction du mouvement
                        fusion.setLastGrille(r[x][y].getNumGrille());
                        fusion.setGrille(etageInf);
                        fusion.setLastX(fusion.getX());
                        fusion.setLastY(fusion.getY());
                        
                        etageSup.getGrille().remove(r[x][y]);
                        etageInf.getGrille().add(fusion);
                        b = true;
                    }
                } else if (l[x][y] != null) { // Nécessaire pour éviter les mouvements de vielles coordonnées, il faut donc les mettre à jour
                    l[x][y].setLastX(l[x][y].getX());
                    l[x][y].setLastY(l[x][y].getY());                            
                }
            }
        }
        return b;
    }
    
    /**
     * <h3>Test de déplacement entre étages</h3>
     * Renvoie true s'il est possible de se déplacer entre les étages
     * 
     * @param etageInf
     * @param etageSup
     * @return 
     */
    public boolean testDeplacementEtage(Grille etageInf, Grille etageSup) {
        Case[][] l = this.convertHash(etageInf);
        Case[][] r = this.convertHash(etageSup);
        
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
    
    /**
     * Fusion des étages en fonction de la direction
     * @param dir
     * @return 
     */
    private boolean fusionEtage(int dir) {
        if (dir == SUPERIEUR) {
            boolean b1 = this.deplacementEtage(this.getMultiGrille()[2], this.getMultiGrille()[1], 1);
            boolean b2 = this.deplacementEtage(this.getMultiGrille()[1], this.getMultiGrille()[0], 1);

            boolean b3 = this.verificationDeplacementEtage(this.getMultiGrille()[2], this.getMultiGrille()[1], 1);
            boolean b4 = this.verificationDeplacementEtage(this.getMultiGrille()[1], this.getMultiGrille()[0], 1);

            return b1 || b2 || b3 || b4;
        } else {
            boolean b1 = this.deplacementEtage(this.getMultiGrille()[0], this.getMultiGrille()[1], -1);
            boolean b2 = this.deplacementEtage(this.getMultiGrille()[1], this.getMultiGrille()[2], -1);

            boolean b3 = this.verificationDeplacementEtage(this.getMultiGrille()[0], this.getMultiGrille()[1], -1);
            boolean b4 = this.verificationDeplacementEtage(this.getMultiGrille()[1], this.getMultiGrille()[2], -1);

            return b1 || b2 || b3 || b4;
        }
    }
    
    /**
     * <h3>Déplacement</h3>
     * Déplace les cases de chacunes des grilles en fonction de la direction
     * (fusion des étages ou mouvement HBDG)
     * 
     * @param direction
     * @return 
     */
    public boolean deplacement(int direction) { 
        if (direction == SUPERIEUR || direction == INFERIEUR) {
            return this.fusionEtage(direction);
        } else {
            boolean b0 = multiGrille[0].lanceurDeplacerCases(direction);
            boolean b1 = multiGrille[1].lanceurDeplacerCases(direction);
            boolean b2 = multiGrille[2].lanceurDeplacerCases(direction);

            return b0 || b1 || b2;
        }
    }
    /**
     * Créer une nouvelle case dans la cube
     */
    public void nouvelleCase() {
        ArrayList<Integer> grillePossible = new ArrayList<>();
        grillePossible.add(0); grillePossible.add(1); grillePossible.add(2);
        // si le tableau est vide cela signifie qu'on ne peut ajouter aucune case dans les grilles
        while (!grillePossible.isEmpty()) {
            int random = (int) (Math.random() * grillePossible.size());
            boolean newCase = this.getMultiGrille()[grillePossible.get(random)].nouvelleCase();

            if (!newCase) {
                grillePossible.remove(random);
            } else {
                break;
            }
        }
    }
    
    /**
     * Renvoie un booléen vrai s'il n'est plus possible de faire un seul mouvement
     * 
     * @return 
     */
    public boolean partieFinie() {
        if (this.getMultiGrille()[0].getGrille().size() < TAILLE * TAILLE || 
                this.getMultiGrille()[1].getGrille().size() < TAILLE * TAILLE || 
                this.getMultiGrille()[2].getGrille().size() < TAILLE * TAILLE) {
            return false;
        } else {
            System.out.println(this.testDeplacementEtage(this.getMultiGrille()[0], this.getMultiGrille()[1]) &&
                    this.testDeplacementEtage(this.getMultiGrille()[1], this.getMultiGrille()[2]));
            return !this.testDeplacementEtage(this.getMultiGrille()[0], this.getMultiGrille()[1]) &&
                    !this.testDeplacementEtage(this.getMultiGrille()[1], this.getMultiGrille()[2]);  
            // si je peux le faire d'un côté alors je peux le faire de l'autre
        }
    }
    
    /**
     * Renvoie la valeur maximale de toute les grilles
     * 
     * @return 
     */
    public int valeurMax() {
        return Math.max(this.getMultiGrille()[0].getValeurMax(), 
                Math.max(this.getMultiGrille()[1].getValeurMax(), this.getMultiGrille()[2].getValeurMax()));
    }
    
    /**
     * <h2>Perdu</h2>
     * Fin de la partie
     */
    public void gameOver() {
        System.out.println("La partie est finie. Votre score est " + this.valeurMax());
        System.exit(1);
    }
    
    /**
     * <h2>Gagné</h2>
     * Fin de la partie
     */
    public void victory() {
        System.out.println("Bravo ! Vous avez atteint " + this.valeurMax());
        System.exit(0);
    }    
    
    /**
     * Renvoie true si le cube est vide
     * @return 
     */
    public boolean isEmpty() {
        return multiGrille[0].getGrille().isEmpty() && 
                multiGrille[1].getGrille().isEmpty() && 
                multiGrille[2].getGrille().isEmpty();
    }
}