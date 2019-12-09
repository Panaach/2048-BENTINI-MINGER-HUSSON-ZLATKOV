/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Grille;
import Model.Cube;
import java.util.EnumSet;

/**
 *
 * @author Panach
 */
public class Memento {
    private EnumSet<Cube> state;

    public Memento(EnumSet<Cube> state){
        this.state = state;
    }

    public EnumSet<Cube> getState(){
        return state;
    }	
}