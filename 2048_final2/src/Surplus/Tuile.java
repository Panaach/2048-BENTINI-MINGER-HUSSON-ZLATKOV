/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Surplus;

import javafx.scene.layout.Pane;

/**
 *
 * @author Panach
 */
public interface Tuile {
    public void threadMovement();
    
    public void threadMovementCaseDead(Pane fond);
}
