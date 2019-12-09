package Model;

import Model.Cube;
import Model.Parametres;
import application.FXMLControllerJeu;
import Surplus.Tuile2048;
import Surplus.TuileComposite;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
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
        for (int i = 0; i < 1; i++) {
            System.out.println(i);
            this.deplacementRandom();
            System.out.println("dans le boucle for : " + i);
            try {
                Thread.sleep(2500);
                Thread.yield();
            } catch (InterruptedException ex) {
                Logger.getLogger(Utopie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    
    private synchronized int randomDeplacement() {
        int nb = (int)(Math.random() * tbRandom.size());
        return tbRandom.get(nb);
    }
    
    private synchronized void deplacementRandom() {
        /*System.out.println(cube);
        Thread thread = new Thread(){
            @Override
            public void run(){
                initTb();
                //ArrayList<Integer> cloned = (ArrayList<Integer>) tbRandom.clone();
                boolean movementSuccess = false;
                while (!movementSuccess && !tbRandom.isEmpty()) {
                    //System.out.println(cube); 

                    int random = randomDeplacement(); // Déplacement random
                    if (random < 4) { // mouvement normaux
                        boolean b = cube.deplacement(random);

                        //System.out.println(tbRandom);
                        if (b) {  
                            //System.out.println("Move normal réussi" + random);
                            threadMouvement(cube);
                            movementSuccess = true;
                            //t.threadMovementCaseDead(fond);
                            //this.nouvelleCase(); // JFX
                            //return true; // déplacement réussi 
                        } else {
                            System.out.println("Move normal pas réussi suppression" + random);
                            tbRandom.remove(Integer.valueOf(random));
                        }
                    } else { // téléportation
                        //TuileComposite t = new TuileComposite();
                        boolean fusionSuccess;
                        if (random == INFERIEUR) fusionSuccess = cube.fusionEtageInf();  
                            else fusionSuccess = cube.fusionEtageSup();  

                        //System.out.println(tbRandom);
                        if (fusionSuccess) {      
                            System.out.println("Move fusion réussi"+random);                  
                            threadMouvement(cube);
                            movementSuccess = true;
                            //t.threadMovementCaseDead(fond);
                            //this.nouvelleCase(); // JFX   
                            //return true; // déplacement réussi  
                        } else {
                            System.out.println("Move fusion pas réussi suppression" + random);
                            tbRandom.remove(Integer.valueOf(random));
                        }
                    }
                } // end while
                //return false;
                //System.out.println(movementSuccess);
                System.out.println(cube);
            }
        };
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();*/
    }
}
