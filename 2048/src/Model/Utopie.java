package Model;

import application.FXMLControllerJeu;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Utopie AI name
 * @author Panach
 */
public class Utopie extends FXMLControllerJeu implements Parametres{ 
    
    private Cube cube;
    private ArrayList<Integer> tbRandom = new ArrayList<>();
        
    public Utopie (Pane fond) {
        super();
        this.cube = Cube.getInstance();
    }
    
    public void launchUtopie() {
        System.out.println("UTOPIE");
    }
    
    public void initTb() {        
        this.tbRandom.clear();
        this.tbRandom.add(BAS);
        this.tbRandom.add(HAUT);
        this.tbRandom.add(DROITE);
        this.tbRandom.add(GAUCHE);
        this.tbRandom.add(SUPERIEUR);
        this.tbRandom.add(INFERIEUR);
    }
}
