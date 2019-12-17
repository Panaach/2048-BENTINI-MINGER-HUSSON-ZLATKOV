/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Antoine le meilleur
 */
public class Etat implements Parametres{
    private int mouvement;
    private Cube cube;
    
    public Etat(int mouvement, Cube cube) {
        this.mouvement = mouvement;
        this.cube = cube;
    }
    
    public Etat(Cube cube) {
        this.cube = cube;
    }
    /**
     * @return the mouvement
     */
    public int getMouvement() {
        return mouvement;
    }

    /**
     * @param mouvement the mouvement to set
     */
    public void setMouvement(int mouvement) {
        this.mouvement = mouvement;
    }

    /**
     * @return the cube
     */
    public Cube getCube() {
        return cube;
    }

    /**
     * @param cube the cube to set
     */
    public void setCube(Cube cube) {
        this.cube = cube;
    }
    public String mouvementEcrit(int val) {
        switch (val) {
            case HAUT:
                return "HAUT";
            case BAS:
                return "BAS";
            case DROITE:
                return "DROITE";
            case GAUCHE:
                return "GAUCHE";
            case SUPERIEUR:
                return "Etage supérieur (droite)";
            default:
                return "Etage inférieur (gauche)";            
        }
    }
    
    @Override 
    public String toString() {
        String s =  this.mouvementEcrit(mouvement) + "\n" + String.valueOf(cube);
        return s;
    }
    
}
