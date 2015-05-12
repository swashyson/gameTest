/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStorage;

import Creature.*;

/**
 *
 * @author Jonni
 */
public class FightingDataStorage {
    
    private static FightingDataStorage fightingDataStorage;
    private Enemy enemy1;
    
    

    public static FightingDataStorage getInstance() {
        if (fightingDataStorage == null) {
            fightingDataStorage = new FightingDataStorage();
        }
        
        return fightingDataStorage;
    }
    public void setEnemy1(Enemy enemy){
        this.enemy1 = enemy;
    }
    public Enemy getEnemy1(){
        return enemy1;
    }
}
