/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.scene.layout.Pane;

/**
 *
 * @author Antoine le meilleur
 */
public class TuileComposite implements Tuile{
    private ArrayList<Tuile> tuileComposite = new ArrayList<>();

    @Override
    public void threadMovement() {
        for(Tuile t : tuileComposite) { 
            t.threadMovement(); 
        }
    }
    
    @Override
    public void threadMovementCaseDead(Pane fond) {
        for(Tuile t : tuileComposite) { 
            t.threadMovementCaseDead(fond); 
        }
    }
    
    @Override
    public void threadMovementFusion() {
        for(Tuile t : tuileComposite) { 
            t.threadMovementFusion(); 
        }
    }
    
    public void add(Tuile t) { 
        tuileComposite.add(t); 
    } 
    
    public void remove(Tuile t) { 
        tuileComposite.remove(t); 
    } 
    
}
