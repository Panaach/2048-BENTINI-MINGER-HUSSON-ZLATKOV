/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pattern.Memento;

import Pattern.Memento.Memento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Panach
 */
public class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento state){
        if (!mementoList.isEmpty()) {mementoList.clear();}
        mementoList.add(state);
    }

    public Memento get(int index){
        return mementoList.get(index);
    }
}