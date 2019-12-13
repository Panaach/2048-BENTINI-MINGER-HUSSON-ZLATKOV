/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Cube;
import java.util.EnumSet;

/**
 *
 * @author Panach
 */
public class Originator {
    private EnumSet<Cube> state;

    public void setState(EnumSet<Cube> state) {      
        this.state = state;
    }

    public EnumSet<Cube> getState() {
         return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
}