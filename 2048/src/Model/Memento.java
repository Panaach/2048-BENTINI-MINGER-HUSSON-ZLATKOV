/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Panach
 */
public class Memento {
   private MultiGrille state;

   public Memento(MultiGrille state){
      this.state = state;
   }

   public MultiGrille getState(){
      return state;
   }	
}