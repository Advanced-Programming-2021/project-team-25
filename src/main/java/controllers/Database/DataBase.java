package controllers.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Deck;
import models.User;
import models.Card;

import java.io.*;
import java.util.*;

import static models.Deck.allDecks;

public class DataBase {

    public static final String savedArrayListName = "savedList.list";

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

    static public void storeData(){
        try {
            File myObjUser = new File("allUsers.txt");

            FileWriter myWriterUser = new FileWriter(myObjUser);
            myWriterUser.write(new Gson().toJson(User.getUsers()));
            myWriterUser.close();

            File myObjDeck = new File("allDecks.txt");

            FileWriter myWriterDeck = new FileWriter(myObjDeck);
            myWriterDeck.write(new Gson().toJson(allDecks));
            myWriterDeck.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<Deck> restoreDecks(){
        File myObj = new File("allDecks.txt");
        if(myObj.exists())
            return new Gson().fromJson(getFileAsString(myObj), new TypeToken<ArrayList<Deck>>() {}.getType());
        return null;
    }

    static public ArrayList<User> restoreUsers(){
        File myObj = new File("allUsers.txt");
        if(myObj.exists())
            return new Gson().fromJson(getFileAsString(myObj), new TypeToken<ArrayList<User>>() {}.getType());
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

    public static ArrayList<Card> loadMonsters() throws IOException {
        ArrayList<Card> temp = new ArrayList<>();
        String line ;
        int i =0;
        BufferedReader br = new BufferedReader(new FileReader("Monster.csv"));
        while ((line = br.readLine()) != null) {
            if(i==0){
                i++;
                continue;
            }
            String[] cardInfo = line.split(",");

             temp.add(new Card(cardInfo[0],
                        Integer.parseInt(cardInfo[1]),
                        cardInfo[2],
                        cardInfo[3],
                        cardInfo[4],
                        Integer.parseInt(cardInfo[5]),
                        Integer.parseInt(cardInfo[6]),
                        cardInfo[7],
                        Integer.parseInt(cardInfo[8])
                ));
//            else {
//                switch (cardInfo[1]) {
//                    case "Card Destruction" -> temp.add(new CardDestruction(cardInfo[1], cardInfo[2]));
//                    case "Change Of Heart" -> temp.add(new ChangeOfHeart(cardInfo[1], cardInfo[2]));
//                    case "Dark Hole" -> temp.add(new DarkHole(cardInfo[1], cardInfo[2]));
//                    case "Graceful Dice" -> temp.add(new GracefulDice(cardInfo[1], cardInfo[2]));
//                    case "Harpie's Feather Duster" -> temp.add(new HarpieFeatherDuster(cardInfo[1], cardInfo[2]));
//                    case "Heavy Storm" -> temp.add(new HeavyStorm(cardInfo[1], cardInfo[2]));
//                    case "Mage Power" -> temp.add(new MagePower(cardInfo[1], cardInfo[2]));
//                    case "Monster Reborn" -> temp.add(new MonsterReborn(cardInfo[1], cardInfo[2]));
//                    case "Pot of Greed" -> temp.add(new PotOfGreed(cardInfo[1], cardInfo[2]));
//                    case "Raigeki" -> temp.add(new Raigeki(cardInfo[1], cardInfo[2]));
//                    default ->
//                }
        }
        br.close();
        return (temp);
    }
}



