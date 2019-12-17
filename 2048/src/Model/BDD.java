/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * Cette classe gère les interactions avec la BDD
 * @author Nathan Minger
 */
public class BDD {
    
    private BDD(){}
    
    private static BDD INSTANCE = null;
    
    public static synchronized BDD getInstance(){
        if (INSTANCE == null){
            INSTANCE = new BDD();
        }
        return INSTANCE;
    }
    
    String DBName = "jdbc:mysql://localhost:3306/2048";
    String user = "root";
    String password = "";
    
    private int userID;
    
    public int getID(){
        return this.userID;
    }
    
    /**
     * Inscription d'un nouvel utilisateur
     * @param pseudo le nom que l'utilisateur voudra utiliser pour jouer 
     * @param mdp le mot de passe pour pouvoir se connecter
     * @param mail mail de l'utilisateur
     * @return retourne un booléen pour savoir si l'inscription est valide ou pas 
     */
    public boolean signUp(String pseudo, String mdp, String mail){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DBName, user, password);
            Statement st = conn.createStatement();
            
            st.executeUpdate("INSERT INTO `utilisateurs`(`Pseudo`, `MotDePasse`, `Mail`) VALUES ('" + pseudo + "','"
                    + mdp + "','" + mail + "')");
            
            conn.close();
            
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Connexion d'un utilisateur
     * @param pseudo le pseudo permet de s'identifier lors de la connection
     * @param mdp le mdp permet de s'identifier lors de la connection
     * @return retourne un booléen qui nous dit si la connection est réussie ou non
     */
    public boolean signIn(String pseudo, String mdp){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DBName, user, password);
            Statement st = conn.createStatement();
            
            ResultSet result = st.executeQuery("Select * from `utilisateurs` WHERE `Pseudo` = '" + pseudo + "'");
            
            // Les lignes suivantes servent à vérifier que l'utilisateur rentre un pseudo existant
            // Ou s'il y a plusieurs utilisateurs avec ce pseudo (normalement impossible, mais on sait jamais)
            int i = 0;
            while(result.next()){
                i++;
            }
            
            result.first();
            
            switch(i){
                // 0 -> pseudo inexistant, l'utilisateur s'est trompé ou n'a pas/plus de compte
                case 0:
                    System.out.println("Pseudo incorrect");
                    return false;
                // 1 -> bonne entrée, il faut vérifier le mdp pour connecter l'utilisateur
                case 1:
                    if(result.getObject(3).toString().equals(mdp)){
                        userID = parseInt(result.getObject(1).toString());
                    } else {
                        System.out.println("Mot de passe incorrect");
                        return false;
                    }
                    break;
                default:
                   System.out.println("plusieurs utilisateurs ont ce pseudo");        
            }
            
            result.close();
            conn.close();
            
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Supprime un joueur en fonction du pseudo (penser à faire vérifier le mdp)
     * @return TRUE si la manip SQL n'a pas généré d'erreur
     */
    public boolean deleteUser(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DBName, user, password);
            Statement st = conn.createStatement();
            
            st.executeUpdate("DELETE from `utilisateurs` WHERE `ID` = '" + userID + "'");
            
            conn.close();
            
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * Met à jour le score du joueur après une partie
     * @param score score realisé pendant la partie
     * @param type type de score utilisé (TRUE: temps/ FALSE:nombre de coups)
     * @return TRUE si la manip SQL n'a pas généré d'erreur
     */
    public boolean updateScore(int score, boolean type){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DBName, user, password);
            Statement st = conn.createStatement();
            
            ResultSet result = st.executeQuery("Select * from `utilisateurs` WHERE `ID` = '" + userID + "'");
            
            int i = 0;
            while(result.next()){
                i++;
            }
            result.first();
            
            if(i==1){
                if (type){
                    if(Integer.parseInt(result.getObject(5).toString()) < score){
                        st.executeUpdate("Update `utilisateurs` set `ScoreTps` = " + score + "WHERE `ID` =" + userID);
                    }
                } else {
                    if(Integer.parseInt(result.getObject(6).toString()) < score){
                        st.executeUpdate("Update `utilisateur` set `ScoreNbCoups` = " + score + "WHERE `ID` =" + userID);
                    }
                }    
            }
            
            result.close();
            conn.close();
            
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean updateUser(String pseudo, String mdp, String mail){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DBName, user, password);
            Statement st = conn.createStatement();
            String query = "Update `utilisateurs` set";
            
            boolean champsPrec = false;
            if(!(pseudo.equals(""))){
                query += "`Pseudo`='" + pseudo + "'";
                champsPrec = true;
            }

            if(!(mdp.equals(""))){
                if(champsPrec){
                    query += ",";
                }
                query += "`MotDePasse`='" + mdp + "'";
                champsPrec = true;
            }

            if(!(mail.equals(""))){
                if(champsPrec){
                    query += ",";
                }
                query += "`Mail`='" + mail + "'";
            }
            query += "WHERE `ID`=" + userID;

            st.executeUpdate(query);
            st.close();
            conn.close();

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
