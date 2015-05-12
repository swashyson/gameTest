/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;


import Creature.Scorpion;
import Creature.Wolf;
import Creature.*;
import DataStorage.HeroDataStorage;
import DataStorage.EnemyDataStorage;
import DataStorage.FightingDataStorage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Mattias, Jonathan, Johan, Fredrik, Mohini
 */
public class FightController implements Initializable {

    @FXML
    Button backToCity;
    @FXML
    private ImageView XP;

    @FXML
    private AnchorPane pane;

    @FXML

    private int heroEXP;
    public int timerCounter = 0;
    public String[] fightOrder;
    private Hero heroChar;
    private Enemy enemy;
    private Enemy[] enemys;
    private Bear bear;
    private Scorpion scorpion;
    private Snake snake;
    private Spider spider;
    private Wolf wolf;
    AnchorPane creaturePane1;
    AnchorPane creaturePane2;
    AnchorPane creaturePane3;

    @FXML
    public void goToCity(ActionEvent event) {

        heroChar.heroTimeStop();

        SwitchScene sc = new SwitchScene();
        sc.change(event, "City");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        createEnemy();
        loadHeroStatsFromDataStorage();
        XPBAR();
        whatHeroToLoad();
        fight();
        worldTime();

    }

    public void XPBAR() {

        XP.setScaleX(heroEXP);
        XP.setX(XP.getScaleX() / 2);

    }

    public void spawnCreature(String URL, int creaturePaneWitdh, int creaturePaneHeight, int creaturePaneX, int creaturePaneY, String ID) {

        ImageView creature = new ImageView();
        creature.setId(ID);
        Image creatureDisplay = new Image(getClass().getResourceAsStream(URL));
        creature.setImage(creatureDisplay);

        createCreaturePane(creature, creaturePaneWitdh, creaturePaneHeight, creaturePaneX, creaturePaneY, ID, null);
        //pane.getChildren().add(creatureAnchor);

    }

    public void loadHeroStatsFromDataStorage() {

        heroChar = HeroDataStorage.getInstance().getHero();
        heroEXP = HeroDataStorage.getInstance().getHero().getEXP();

    }

    public void loadEnemyStatsFromDataStorage() {

       bear = EnemyDataStorage.getInstance().getBear();
       scorpion = EnemyDataStorage.getInstance().getScorpion();
       snake = EnemyDataStorage.getInstance().getSnake();
       spider = EnemyDataStorage.getInstance().getspider();
       wolf = EnemyDataStorage.getInstance().getWolf();
    }

    public void fight() {
        heroChar.fightMonster(heroChar, enemy);
        heroChar.heroTimeStart();

    }

    public void createEnemy() {

        // Ska vara en random generator här beroende på vilken lvl osv man är
        
        enemy = new Bear("Bear",10,1,1,1);
        FightingDataStorage.getInstance().setEnemy1(enemy);
        spawnCreature("Recourses/Bear.png", 40, 60, 730, 500); // spawna en fiende på dessa positionerna med en pane
//        Random rand = new Random();
//        int numberCreature = rand.nextInt(2)+1;
//        System.out.print("antal djur" + numberCreature);
//        for(int i =0; i <numberCreature;i++){
//            int whatCreature = rand.nextInt(4)+1;
//            
//            
//                if(whatCreature == 1 ){
//                    enemy = new Bear("Bear",1,1,1,1);
//                    spawnCreature("Recourses/Bear.png", 40, 60, 730, 500); // spawna en fiende på dessa positionerna med en pane
//                    System.out.printf("skapar en " + whatCreature);
//                }
//                else if(whatCreature == 2 ){
//                    enemy = new Scorpion("Scorpion",1,1,1,1);
//                    spawnCreature("Recourses/Warrior.png", 40, 60, 730, 500); // spawna en fiende på dessa positionerna med en pane
//                    System.out.printf("skapar en " + whatCreature);
//                }
//                else if(whatCreature == 3 ){
//                    enemy = new Snake("Snake",1,1,1,1);
//                    spawnCreature("Recourses/RangerChar.png", 40, 60, 730, 500); // spawna en fiende på dessa positionerna med en pane
//                    System.out.printf("skapar en " + whatCreature);
//                }
//                else if(whatCreature == 4 ){
//                    enemy = new Spider("Spider",1,1,1,1);
//                    spawnCreature("Recourses/Bear.png", 40, 60, 730, 500); // spawna en fiende på dessa positionerna med en pane
//                    System.out.printf("skapar en " + whatCreature);
//                }
//                else if(whatCreature == 5 ){
//                    enemy = new Wolf("Wolf",1,1,1,1);
//                    spawnCreature("Recourses/Bear.png", 40, 60, 730, 500); // spawna en fiende på dessa positionerna med en pane
//                    System.out.printf("skapar en " + whatCreature);
//                }
//        }
        
        
    }

    public void createCreaturePane(ImageView creature, int creaturePaneWitdh, int creaturePaneHeight, int creaturePaneX, int creaturePaneY, String ID, ImageView image) {

        switch (ID) {
            case "Hero": {
                AnchorPane creaturePane1 = new AnchorPane();
                creaturePane1.prefWidth(creaturePaneWitdh);
                creaturePane1.prefHeight(creaturePaneHeight); //Storlek på pane
                creaturePane1.setLayoutX(creaturePaneX);
                creaturePane1.setLayoutY(creaturePaneY);
                ImageView hpBarCreature1 = new ImageView();
                Image imageHealthCreature1 = new Image(getClass().getResourceAsStream("Recourses/HealthBar.png"));
                hpBarCreature1.setImage(imageHealthCreature1);
                pane.getChildren().add(creaturePane1);
                creaturePane1.getChildren().add(hpBarCreature1);
                creaturePane1.getChildren().add(creature);

                hpBarCreature1.setScaleX(healthPaneHeroScaler());
                hpBarCreature1.setX(healthPaneHeroScaler() / 2);
                break;
            }
            case "Bear": {
                AnchorPane creaturePane1 = new AnchorPane();
                creaturePane1.prefWidth(creaturePaneWitdh);
                creaturePane1.prefHeight(creaturePaneHeight); //Storlek på pane
                creaturePane1.setLayoutX(creaturePaneX);
                creaturePane1.setLayoutY(creaturePaneY);
                ImageView hpBarCreature1 = new ImageView();
                Image imageHealthCreature1 = new Image(getClass().getResourceAsStream("Recourses/HealthBar.png"));
                hpBarCreature1.setImage(imageHealthCreature1);
                pane.getChildren().add(creaturePane1);
                creaturePane1.getChildren().add(hpBarCreature1);
                creaturePane1.getChildren().add(creature);

                hpBarCreature1.setScaleX(healthPaneEnemyScaler());
                hpBarCreature1.setX(healthPaneEnemyScaler() / 2);

                test.add(hpBarCreature1);

                break;
            }
        }

    }

    public int healthPaneHeroScaler() {

        int currentHP = HeroDataStorage.getInstance().getHero().getHeroCurrentHP();
        int maxHP = HeroDataStorage.getInstance().getHero().getHp();
        int maxImageView = 50;

        int calculate;

        calculate = (currentHP * maxImageView) / maxHP; // Fullt fungerande, bara till alla kalla metoden varje tick
        return calculate;

    }

    public int healthPaneEnemyScaler() {

        int maxHP = DataStorage.EnemyDataStorage.getInstance().getBear().getHp();
        int currentHP = DataStorage.EnemyDataStorage.getInstance().getBear().getHp();
        int maxImageView = 50;

        int calculate;

        calculate = maxHP - 5; // Fullt fungerande, bara till alla kalla metoden varje tick
        System.out.println("Pane size" + calculate);
        return calculate;

    }

    public void healthPaneSetPosX() {

        //Ska flytta scaleX hit från createCreaturePan
    }

    public void whatHeroToLoad() {

        if (HeroDataStorage.getInstance().getHero().getHeroType() == 1) {

            spawnCreature("Recourses/WarriorChar.png", 40, 60, 30, 500, "Hero");

        } else if (HeroDataStorage.getInstance().getHero().getHeroType() == 2) {

            spawnCreature("Recourses/RangerChar.png", 40, 60, 30, 500, "Hero");

        } else if (HeroDataStorage.getInstance().getHero().getHeroType() == 3) {

            spawnCreature("Recourses/MageChar.png", 40, 60, 30, 500, "Hero");

        }

    }

    public void refreshValues() {

        test.get(0).setScaleX(healthPaneEnemyScaler());
        test.get(0).setX(healthPaneEnemyScaler() / 2);

        test.get(1).setScaleX(healthPaneEnemyScaler());
        test.get(1).setX(healthPaneEnemyScaler() / 2); // det finns två björnar på varandra....????
    }

    public void doSomething() {
        System.out.println("World time Tic Toc");
        refreshValues();

        System.out.println("Number of bears = " + test.size());
    }

    public void worldTime() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),
                ae -> doSomething()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}
