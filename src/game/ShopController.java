/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import Items.Weapon;
import Items.Armor;
import DataStorage.HeroDataStorage;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.Effect;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Mattias, Jonathan, Johan, Fredrik, Mohini
 */
public class ShopController implements Initializable {

    @FXML
    private Button backToCity;

    @FXML
    private AnchorPane pane;

    @FXML
    private ListView current;
    @FXML
    private ListView buy;

    private final Button weapon1 = new Button();
    private final Button weapon2 = new Button();
    private final Button weapon3 = new Button();
    private final Button armor1 = new Button();
    private final Button armor2 = new Button();
    private final Button armor3 = new Button();

    private Weapon weapon;
    private Armor armor;

    private ArrayList<Weapon> weaponList;
    private ArrayList<Armor> armorList;

    private final Button[] array = new Button[6];
    private final ArrayList<Object> currentItems = new ArrayList();

    private final int getclass = HeroDataStorage.getInstance().getHero().getHeroType();

    @FXML
    public void goToCity(ActionEvent event) {

        SwitchScene sc = new SwitchScene();
        sc.change(event, "City");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        startMethodWithBinds();

        int i = 1;
        for (Button array1 : array) {
            HoverMouse.getInstance().inHoverSize(array1, i, buy);
            HoverMouse.getInstance().outHoverSize(array1, buy);
            i = i + 1;
        }

        if (getclass == 1) {
            spawnItems("Sword1", "Sword2", "Sword3", "Armor1O", "Armor2O", "Armor3O");

        } else if (getclass == 2) {
            spawnItems("Bow1", "Bow2", "Bow3", "Armor1O", "Armor2O", "Armor3O");

        } else if (getclass == 3) {
            spawnItems("Staff1", "Staff2", "Staff3", "Armor1O", "Armor2O", "Armor3O");
        }
    }

    public void spawnItems(String itemName1, String itemName2, String itemName3, String itemName4, String itemName5, String itemName6) {

        createDisplayItem(weapon1, 50, 100, "Recourses/" + itemName1 + ".png");
        createDisplayItem(weapon2, 150, 100, "Recourses/" + itemName2 + ".png");
        createDisplayItem(weapon3, 250, 100, "Recourses/" + itemName3 + ".png");
        createDisplayItem(armor1, 400, 150, "Recourses/" + itemName4 + ".png");
        createDisplayItem(armor2, 500, 150, "Recourses/" + itemName5 + ".png");
        createDisplayItem(armor3, 600, 150, "Recourses/" + itemName6 + ".png");

    }

    public void createDisplayItem(Button item, int x, int y, String URL) {

        item.setLayoutX(x);
        item.setLayoutY(y);

        Image image = new Image(getClass().getResourceAsStream(URL));

        Effect effect = new ImageInput(image);

        item.setEffect(effect);
        pane.getChildren().add(item);

    }

    public void buyWeapon(Button button, int buttonID) {

        button.setOnMouseClicked((MouseEvent event) -> {
            try {
                weaponList = new ArrayList();
                DBConnect.connect();
                ResultSet rs = DBConnect.CreateSelectStatement("select * from weapon where weapontype = '" + getclass + "';");

                while (rs.next()) {

                    String weaponName = rs.getString("weaponName");
                    int weaponMinDamage = rs.getInt("weaponMinDamage");
                    int weaponMaxDamage = rs.getInt("weaponMaxDamage");
                    int weaponSpeed = rs.getInt("weaponSpeed");
                    int weaponlevel = rs.getInt("weaponLevel");
                    int weaponType = rs.getInt("weaponType");
                    int weaponID = rs.getInt("weaponID");

                    weapon = new Weapon(weaponName, weaponID, weaponMinDamage, weaponMaxDamage, weaponSpeed, weaponlevel, weaponType);
                    weaponList.add(weapon);

                }
                if (buttonID == 1 && levelReq(weaponList.get(0).getWeaponLevel()) == true) {
                    setWeaponToHero(weaponList.get(0));
                    removeWeapon(button, button, button, buttonID);

                } else if (buttonID == 2 && levelReq(weaponList.get(1).getWeaponLevel()) == true) {

                    setWeaponToHero(weaponList.get(1));
                    removeWeapon(button, button, button, buttonID);
                    removeWeaponRequest();
                } else if (buttonID == 3 && levelReq(weaponList.get(2).getWeaponLevel()) == true) {

                    setWeaponToHero(weaponList.get(2));
                    removeWeapon(button, button, button, buttonID);
                    removeWeaponRequest();
                }
                else{
                    System.out.println("För låg level blablabla");
                }
                listViewGetCurrentItems();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DBConnect.close();
            }
        });

    }

    public void buyArmor(Button button, int buttonID) {

        button.setOnMouseClicked((MouseEvent event) -> {
            try {
                armorList = new ArrayList();
                DBConnect.connect();
                ResultSet rs = DBConnect.CreateSelectStatement("select * from game.armor where armorType = '" + getclass + "'");

                while (rs.next()) {

                    String armorName = rs.getString("armorName");
                    int localArmor = rs.getInt("armor");
                    int armorType = rs.getInt("armorType");
                    int armorLevel = rs.getInt("armorLevel");
                    int armorSpeed = rs.getInt("armorSpeed");
                    int armorID = rs.getInt("armorID");

                    armor = new Armor(armorName, armorID, localArmor, armorType, armorLevel, armorSpeed);
                    armorList.add(armor);

                }
                if (buttonID == 4 && levelReq(armorList.get(0).getArmorLevel()) == true) {
                    setArmorToHero(armorList.get(0));
                    removeArmor(button, button, button, buttonID);

                } else if (buttonID == 5 && levelReq(armorList.get(1).getArmorLevel()) == true) {
                    setArmorToHero(armorList.get(1));
                    removeArmor(button, button, button, buttonID);
                    removeArmorRequest();

                } else if (buttonID == 6 && levelReq(armorList.get(2).getArmorLevel()) == true) {
                    setArmorToHero(armorList.get(2));
                    removeArmor(button, button, button, buttonID);
                    removeArmorRequest();
                }
                else{
                    System.out.println("Du är för låg level");
                }
                
                listViewGetCurrentItems();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DBConnect.close();
            }
        });

    }

    public void setWeaponToHero(Weapon weapon) {

        DBConnect.connect();

        HeroDataStorage.getInstance().setWeapon(weapon);
        int LocalWeaponID = HeroDataStorage.getInstance().getWeapon().getWeaponID();
        int heroID = HeroDataStorage.getInstance().getHero().getHeroID();

        try {
            ResultSet rs = DBConnect.CreateSelectStatement("select * from hero_has_weapon where hero_idHero = '" + heroID + "';");
            if (rs.next()) {
                DBConnect.CreateAlterStatement("UPDATE game.hero_has_weapon SET weapon_weaponID='" + LocalWeaponID + "' WHERE hero_idHero='" + heroID + "';");
                System.out.println("Du har lyckats alter statement i mysql för att lägga till ett vapen");

            } else {
                DBConnect.CreateInsertStatement("INSERT INTO game.hero_has_weapon (hero_idHero, weapon_weaponID) VALUES ('" + heroID + "', '" + LocalWeaponID + "');", null, null); //fel label och fel text = null null
                System.out.println("INSERT INTO 'game'.'hero_has_weapon' ('hero_idHero', 'weapon_weaponID') VALUES ('" + heroID + "', '" + LocalWeaponID + "');");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBConnect.close();
        }

    }

    public void setArmorToHero(Armor armor) {

        DBConnect.connect();

        HeroDataStorage.getInstance().setArmor(armor);
        int localArmorID = HeroDataStorage.getInstance().getArmor().getArmorID();
        int heroID = HeroDataStorage.getInstance().getHero().getHeroID();

        try {
            ResultSet rs = DBConnect.CreateSelectStatement("select * from hero_has_armor where hero_idHero = '" + heroID + "';");
            if (rs.next()) {
                DBConnect.CreateAlterStatement("UPDATE game.hero_has_armor SET armor_armorID='" + localArmorID + "' WHERE hero_idHero='" + heroID + "';");
                System.out.println("Du har lyckats alter statement i mysql för att lägga till en armor");

            } else {
                DBConnect.CreateInsertStatement("INSERT INTO game.hero_has_armor (hero_idHero, armor_armorID) VALUES ('" + heroID + "', '" + localArmorID + "');", null, null); //fel label och fel text = null null
                System.out.println("INSERT INTO 'game'.'hero_has_armor' ('hero_idHero', 'armor_armorID') VALUES ('" + heroID + "', '" + localArmorID + "');");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBConnect.close();
        }
    }

    public void removeWeapon(Button button, Button button2, Button button3, int ButtonID) {

        if (HeroDataStorage.getInstance().getWeapon() != null) {

            System.out.println("Du har ett vapen");

            if (ButtonID == 1) {

                button.setVisible(false);

            } else if (ButtonID == 2) {

                button.setVisible(false);
                button2.setVisible(false);

            } else if (ButtonID == 3) {
                button.setVisible(false);
                button2.setVisible(false);
                button3.setVisible(false);
            }
        }
    }

    public void removeArmor(Button button, Button button2, Button button3, int ButtonID) {

        if (HeroDataStorage.getInstance().getArmor() != null) {

            System.out.println("Du har en armor");

            if (ButtonID == 4) {

                button.setVisible(false);

            } else if (ButtonID == 5) {

                button.setVisible(false);
                button2.setVisible(false);

            } else if (ButtonID == 6) {

                button.setVisible(false);
                button2.setVisible(false);
                button3.setVisible(false);
            }
        }
    }

    public void startMethodWithBinds() {

        removeWeaponRequest();
        removeArmorRequest();

        buyWeapon(weapon1, 1);
        buyWeapon(weapon2, 2);
        buyWeapon(weapon3, 3);
        buyArmor(armor1, 4);
        buyArmor(armor2, 5);
        buyArmor(armor3, 6);

        array[0] = weapon1;
        array[1] = weapon2;
        array[2] = weapon3;
        array[3] = armor1;
        array[4] = armor2;
        array[5] = armor3;

        listViewGetCurrentItems();

    }

    public void listViewGetCurrentItems() {

        if (HeroDataStorage.getInstance().getWeapon() != null) {
            currentItems.add("Your Weapon");
            currentItems.add("____________________________________");
            currentItems.add("Weapon Name: " + HeroDataStorage.getInstance().getWeapon().getName());
            currentItems.add("Weapon Min Damage: " + HeroDataStorage.getInstance().getWeapon().getWeaponMinDamage());
            currentItems.add("Weapon Max Damage: " + HeroDataStorage.getInstance().getWeapon().getWeaponMaxDamage());
            currentItems.add("Weapon Speed: " + HeroDataStorage.getInstance().getWeapon().getWeaponSpeed());
        } else {
            currentItems.add("You dont have a weapon");
        }
        if (HeroDataStorage.getInstance().getArmor() != null) {

            currentItems.add("Your Armor");
            currentItems.add("____________________________________");
            currentItems.add("Armor Name: " + HeroDataStorage.getInstance().getArmor().getName());
            currentItems.add("Armor Value: " + HeroDataStorage.getInstance().getArmor().getArmor());
            currentItems.add("Armor Speed: " + HeroDataStorage.getInstance().getArmor().getArmorSpeed());

        } else {
            currentItems.add("You dont have a armor set");
        }
        ObservableList<Object> OL = FXCollections.observableArrayList(currentItems);
        current.setItems(OL);
        currentItems.removeAll(currentItems);

    }

    public void removeWeaponRequest() {
        if (HeroDataStorage.getInstance().getWeapon() != null) {
            DBConnect.connect();
            ResultSet RS = DBConnect.CreateSelectStatement("select * from weapon where weapontype = '" + getclass + "';");
            int heroWeaponID = HeroDataStorage.getInstance().getWeapon().getWeaponID();

            int counter = 0;

            try {
                while (RS.next()) {
                    int databaseWeaponID = RS.getInt("weaponID");
                    if (heroWeaponID >= databaseWeaponID) {
                        counter = counter + 1;
                        System.out.println(counter);

                        if (counter == 1) {
                            weapon1.setVisible(false);

                        } else if (counter == 2) {
                            weapon2.setVisible(false);

                        } else if (counter == 3) {
                            weapon3.setVisible(false);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DBConnect.close();
            }

        }
    }

    public void removeArmorRequest() {
        if (HeroDataStorage.getInstance().getArmor() != null) {
            DBConnect.connect();
            ResultSet RS = DBConnect.CreateSelectStatement("select * from armor where armortype = '" + getclass + "';");
            int heroArmorID = HeroDataStorage.getInstance().getArmor().getArmorID();

            int counter = 0;

            try {
                while (RS.next()) {
                    int databaseArmorID = RS.getInt("ArmorID");
                    if (heroArmorID >= databaseArmorID) {
                        counter = counter + 1;
                        System.out.println(counter);

                        if (counter == 1) {
                            armor1.setVisible(false);

                        } else if (counter == 2) {
                            armor2.setVisible(false);

                        } else if (counter == 3) {
                            armor3.setVisible(false);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                DBConnect.close();
            }

        }
    }

    public boolean levelReq(int itemLevel) {

        if (HeroDataStorage.getInstance().getHero().getLevel() >= itemLevel) {
            return true;
        }
        return false;

    }

}
