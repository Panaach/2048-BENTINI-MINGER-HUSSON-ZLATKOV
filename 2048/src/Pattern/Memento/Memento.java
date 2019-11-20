/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pattern.Memento;

import Model.Grille;
import Model.MultiGrille;
import java.util.EnumSet;

/**
 *
 * @author Panach
 */
public class Memento {
    private EnumSet<MultiGrille> state;

    public Memento(EnumSet<MultiGrille> state){
        this.state = state;
    }

    public EnumSet<MultiGrille> getState(){
        return state;
    }	
}