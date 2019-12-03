package IA;

import Model.MultiGrille;
import Model.Parametres;
import application.FXMLControllerJeu;
import Pattern.Composite.Tuile2048;
import Pattern.Composite.TuileComposite;
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
    
    private MultiGrille mGrille;
    private Pane fond;
    ArrayList<Integer> tbRandom = new ArrayList<>();
        
    public Utopie (MultiGrille mGrille, Pane fond) {
        this.mGrille = mGrille;
        this.fond = fond;
    }
    
    public void launchUtopie() {
        for (int i = 0; i < 5; i++) {
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
        this.tbRandom.add(FULLRIGHT);
        this.tbRandom.add(FULLLEFT);
    }
    
    private synchronized int randomDeplacement() {
        //System.out.println(tbRandom);
        ArrayList<Integer> cloned = (ArrayList<Integer>) tbRandom.clone();
        System.out.println(cloned.hashCode());
        int nb = (int)(Math.random() * tbRandom.size());
        //System.out.println(nb);
        return tbRandom.get(nb);
    }
    
    private synchronized void deplacementRandom() {
        Thread thread = new Thread(){
            @Override
            public void run(){
                initTb();
                ArrayList<Integer> cloned = (ArrayList<Integer>) tbRandom.clone();
                boolean movementSuccess = false;
                while (!movementSuccess && !cloned.isEmpty()) {
                    //System.out.println(mGrille); 
                    
                    /*try {
                        Thread.sleep(2500);
                        Thread.yield();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Utopie.class.getName()).log(Level.SEVERE, null, ex);
                    }*/

                    int random = randomDeplacement(); // Déplacement random
                    if (random < 4) { // mouvement normaux
                        TuileComposite t = new TuileComposite();
                        boolean b1 = mGrille.getMultiGrille()[0].lanceurDeplacerCases(random);
                        boolean b2 = mGrille.getMultiGrille()[1].lanceurDeplacerCases(random);
                        boolean b3 = mGrille.getMultiGrille()[2].lanceurDeplacerCases(random);

                        //System.out.println(tbRandom);
                        if (b1 || b2 || b3) {  
                            System.out.println("Move normal réussi" + random);
                            t.add(new Tuile2048(mGrille.getMultiGrille()[0]));
                            t.add(new Tuile2048(mGrille.getMultiGrille()[1]));
                            t.add(new Tuile2048(mGrille.getMultiGrille()[2]));
                            t.threadMovement();
                            movementSuccess = true;
                            //t.threadMovementCaseDead(fond);
                            //this.nouvelleCase(); // JFX
                            //return true; // déplacement réussi 
                        } else {
                            System.out.println("Move normal pas réussi suppression" + random);
                            cloned.remove(Integer.valueOf(random));
                        }
                    } else { // téléportation
                        TuileComposite t = new TuileComposite();
                        boolean fusionSuccess;
                        if (random == FULLLEFT) fusionSuccess = mGrille.fusionGauche();  
                            else fusionSuccess = mGrille.fusionDroite();  

                        //System.out.println(tbRandom);
                        if (fusionSuccess) {      
                            System.out.println("Move fusion réussi"+random);                  
                            t.add(new Tuile2048(mGrille.getMultiGrille()[0]));
                            t.add(new Tuile2048(mGrille.getMultiGrille()[1]));
                            t.add(new Tuile2048(mGrille.getMultiGrille()[2]));
                            t.threadMovement();
                            movementSuccess = true;
                            //t.threadMovementCaseDead(fond);
                            //this.nouvelleCase(); // JFX   
                            //return true; // déplacement réussi  
                        } else {
                            System.out.println("Move fusion pas réussi suppression" + random);
                            cloned.remove(Integer.valueOf(random));
                        }
                    }
                } // end while
                //return false;
                //System.out.println(movementSuccess);
                System.out.println(mGrille);
            }
        };
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        /*try {
            thread.join(32);
        } catch (InterruptedException i) {
            System.out.println("Error tread : " + i);
        }*/
    }
}
