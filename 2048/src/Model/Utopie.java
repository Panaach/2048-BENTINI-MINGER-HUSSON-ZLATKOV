package Model;

import application.FXMLControllerJeu;
import java.util.ArrayList;
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
    
    private ArrayList<Integer> listeMouvement = new ArrayList<>();
    private ArrayList<Etat> listeEtat = new ArrayList<>();
        
    public Utopie (Pane fond) {
        super();
        // initialise le tb de mouvement
        this.listeMouvement.add(BAS);
        this.listeMouvement.add(HAUT);
        this.listeMouvement.add(DROITE);
        this.listeMouvement.add(GAUCHE);
        this.listeMouvement.add(SUPERIEUR);
        this.listeMouvement.add(INFERIEUR);
        // prend en premier l'instance du cube
        this.listeEtat.add(new Etat(this.cube));
    }
    
    public void launchUtopie() {
        System.out.println("UTOPIE");
        this.init(this.listeEtat, 0);
    }
    
    public void init(ArrayList<Etat> listeEtat, int compteur) {
        Cube cube = listeEtat.get(0).getCube();
        // clone
        cube.init(new Grille[]{(Grille) cube.getMultiGrille()[0].clone(), (Grille) cube.getMultiGrille()[1].clone(), (Grille) cube.getMultiGrille()[2].clone()});
        
        for (int i = compteur; i < 5; i++) {
            for (int j = 0; j < 6; j++) { // les 6 mouvements
                boolean b = cube.deplacement(listeMouvement.get(j));
                if (b) {
                    Etat e = new Etat(listeMouvement.get(j), cube);
                    listeEtat.add(e);
                }
                System.out.println(cube);
            }
            listeEtat.remove(0);
            init(listeEtat, compteur +1);
        }
        
        // comme le clonage ne fonctionne pas nous n'avons pas eu le temps d'écrire la suite
    }
}
