/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * La classe qui définie ce qu'est une casse, l'entité la plus petite de notre jeu
 * @author Sylvain
 */
public class Case implements Parametres, Cloneable, java.io.Serializable {

    /* Elements FXML utilisé seulement pour le FX (créé un lien entre la tuile
    et la case) */
    @FXML
    private transient Pane pane;
    @FXML
    private transient Label label;
    
    private int x, y, valeur, numGrille, lastX, lastY, lastGrille;
    private Grille grille;

    public Case(int abs, int ord, int v, int numGrille) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
        this.numGrille = numGrille;
        // Côté JFX : valeur par défaut à l'initialisation
        this.lastX = abs;
        this.lastY = ord;
        this.lastGrille = numGrille;
    }

    public void setGrille(Grille g) {
        this.grille = g;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur() {
        return this.valeur;
    }

    /**
     * @return the numGrille
     */
    public int getNumGrille() {
        return numGrille;
    }

    /**
     * @param numGrille the numGrille to set
     */
    public void setNumGrille(int numGrille) {
        this.numGrille = numGrille;
    }

    /**
     * @return the lastX
     */
    public int getLastX() {
        return lastX;
    }

    /**
     * @param lastX the lastX to set
     */
    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    /**
     * @return the lastY
     */
    public int getLastY() {
        return lastY;
    }

    /**
     * @param lastY the lastY to set
     */
    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    /**
     * @param lastGrille the lastGrille to set
     */
    public void setLastGrille(int lastGrille) {
        this.lastGrille = lastGrille;
    }

    /**
     * @return the lastGrille
     */
    public int getLastGrille() {
        return lastGrille;
    }

    /**
     * @return the p
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * @param p the p to set
     */
    public void setPane(Pane p) {
        this.pane = p;
    }

    /**
     * @return the l
     */
    public Label getLabel() {
        return label;
    }

    /**
     * @param l the l to set
     */
    public void setLabel(Label l) {
        this.label = l;
    }

    /**
     * la méthode equals est utilisée lors de l'ajout d'une case à un ensemble 
     * pour vérifier qu'il n'y a pas de doublons 
     * (teste parmi tous les candidats qui ont le même hashcode)
     * @param obj prend un objet en paramètre et regarde si c'est une casee
     * @return retourne un booléen si l'objet prix en paramètre est égale à une case ou non
     */
    @Override
    public boolean equals(Object obj) { // )
        if (obj instanceof Case) {
            Case c = (Case) obj;
            return (this.x == c.x && this.y == c.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() { // détermine le hashcode
        return this.x * 7 + this.y * 13;
    }
    
    @Override
    public Object clone() { 
        try {
            Case caseClone = (Case) super.clone(); 
            return caseClone;
        } catch (CloneNotSupportedException c) {
            System.out.println("Error clone : " + c);
        }
        return null;
    }
    
    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }

    public synchronized Case getVoisinDirect(int direction) {
        if (direction == HAUT) {
            for (int i = this.y - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == BAS) {
            for (int i = this.y + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == GAUCHE) {
            for (int i = this.x - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        } else if (direction == DROITE) {
            for (int i = this.x + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "Case(X " + this.x + ",Y " + this.y + ",Val " + this.valeur + ", num Grille " + this.numGrille + ")";
        s = s + "last x " + this.getLastX() + ", last y " + this.lastY + ", last grille " + this.lastGrille + ".";
        return s;
    }

}
