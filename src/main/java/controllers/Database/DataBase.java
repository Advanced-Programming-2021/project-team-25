package controllers.Database;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import models.Card;
import models.CardStufs.Type;
import models.Monster.*;
import models.Deck;
import models.SpellAndTrap.*;
import models.User;


import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static models.Deck.allDecks;

public class DataBase {

    public static final String savedArrayListName = "savedList.list";
    public static final String savedDeckName = "Decks.list";

    public static void saveTheUserList(ArrayList<User> users) {
        try {
            FileOutputStream fileOut = new FileOutputStream(savedArrayListName, false);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(users);
            oos.close();
            fileOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadTheList() {
        try {
            FileInputStream fin = new FileInputStream(savedArrayListName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            ArrayList<User> myClassObj = (ArrayList<User>) ois.readObject();
            ois.close();
            System.out.println("Loaded Data from file");
            return myClassObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static public void storeDecks(ArrayList<Deck> decks){
        try {
            FileOutputStream fileOut = new FileOutputStream(savedDeckName, false);
            ObjectOutputStream oos = new ObjectOutputStream(fileOut);
            oos.writeObject(decks);
            oos.close();
            fileOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<Deck> restoreDecks(){
        try {
            FileInputStream fin = new FileInputStream(savedDeckName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            ArrayList<Deck> myClassObj = (ArrayList<Deck>) ois.readObject();
            ois.close();
            System.out.println("Loaded Data of Decks from file");
            return myClassObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getFileAsString(File myObj) {
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return myReader.nextLine();
    }

    public static void loadCards() throws IOException {
        HashMap<String,Card> temp = new HashMap<>();
        String line;
        int i = 0;
        BufferedReader br = new BufferedReader(new FileReader("Monster.csv"));
        while ((line = br.readLine()) != null) {
            if(i==0){
                i++;
                continue;
            }
            Matcher matcher = Pattern.compile("(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+),(.+)").matcher(line);

            if(matcher.find()) Monsters(temp, matcher);
        }
        br.close();

        BufferedReader br1 = new BufferedReader(new FileReader("SpellTrap.csv"));
        while ((line = br1.readLine()) != null) {
            if(i==1){
                i++;
                continue;
            }

            Matcher matcher = Pattern.compile("(.+),(.+),(.+),(.+),(.+),(.+)").matcher(line);

            if(matcher.find()) {
                if(matcher.group(2).equals("Trap")) Trap(temp, matcher);
                else Spell(temp, matcher);
            }
        }
        br1.close();
        Card.allCards=temp;
    }

    private static void Monsters(HashMap<String, Card> temp, Matcher matcher) {
        if (matcher.group(1).equals("Yomi Ship")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new YomiShip(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Suijin")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Suijin(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Man-Eater Bug")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new ManEaterBug(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Gate Guardian")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new GateGuardian(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Scanner")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new models.Monster.Scanner(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Marshmallon")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Marshmallon(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Beast King Barbaros")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new BeastKingBarbaros(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Texchanger")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Texchanger(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("The Calculator")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TheCalculator(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Mirage Dragon")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MirageDragon(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Herald of Creation")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new HeraldOfCreation(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Exploder Dragon")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new ExploderDragon(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Terratiger% the Empowered Warrior")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TerratigerTheEmpoweredWarrior(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("The Tricky")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TheTricky(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else if (matcher.group(1).equals("Command Knight")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new CommandKnight(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
        else{
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Monster(
                            matcher.group(1).replace("%",",").replace("\"",""),
                            Type.MONSTER, matcher.group(8).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(9)), Integer.parseInt(matcher.group(2)),
                            matcher.group(3), matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)),
                            Integer.parseInt(matcher.group(7))
                    ));
        }
    }

    private static void Trap(HashMap<String, Card> temp, Matcher matcher) {
        if (matcher.group(1).equals("Trap Hole")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TrapHole(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Mirror Force")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MirrorForce(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Magic Cylinder")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MagicCylinder(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Mind Crush")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MindCrush(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Torrential Tribute")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TorrentialTribute(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Time Seal")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TimeSeal(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Negate Attack")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new NegateAttack(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Solemn Warning")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new SolemnWarning(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Magic Jamamer")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MagicJamamer(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Call of The Haunted")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new CallOfTheHaunted(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Vanity's Emptiness")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new VanitysEmptiness(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Wall of Revealing Light")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new WallOfRevealingLight(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Wall of Revealing Light")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new WallOfRevealingLight(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.TRAP, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
    }

    private static void Spell(HashMap<String, Card> temp, Matcher matcher) {
        if (matcher.group(1).equals("Monster Reborn")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MonsterReborn(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Terraforming")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Terraforming(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Pot of Greed")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new PotOfGreed(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Raigeki")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Raigeki(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Change of Heart")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new ChangeOfHeart(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Swords of Revealing Light")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new SwordsOfRevealingLight(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Harpie's Feather Duster")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new HarpiesFeatherDuster(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Dark Hole")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new DarkHole(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Supply Squad")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new SupplySquad(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Spell Absorption")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new SpellAbsorption(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Messenger of peace")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MessengerOfPeace(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Twin Twisters")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new TwinTwisters(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Mystical space typhoon")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MysticalSpaceTyphoon(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Ring of defense")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new RingOfDefense(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Yami")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Yami(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Forest")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Forest(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Closed Forest")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new ClosedForest(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Umiiruka")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new Umiiruka(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Sword of dark destruction")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new SwordOfDarkDestruction(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Black Pendant")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new BlackPendant(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("United We Stand")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new UnitedWeStand(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Magnum Shield")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new MagnumShield(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
        else if (matcher.group(1).equals("Advanced Ritual Art")){
            temp.put(matcher.group(1).replace("%", ",").replace("\"", ""),
                    new AdvancedRitualArt(
                            matcher.group(1).replace("%", ",").replace("\"", ""),
                            Type.SPELL, matcher.group(4).replace("%", ",").replace("\"", ""),
                            Integer.parseInt(matcher.group(6)), matcher.group(3), matcher.group(5)
                    ));
        }
    }
}



