package controllers.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.Deck;
import models.SpellAndTrap.SpellAndTrap;
import models.User;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static String getFileAsString(File myObj) {
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return myReader.nextLine();
    }

    public static HashMap<String,Card> loadMonsters() throws IOException {
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

            if(matcher.find()) {
                temp.put(matcher.group(1).replace("%",",").replace("\"",""),
                        new Monster(
                        matcher.group(1).replace("%",",").replace("\"",""),
                        Type.MONSTER,
                        matcher.group(8).replace("%",",").replace("\"",""),
                        Integer.parseInt(matcher.group(9)),
                        Integer.parseInt(matcher.group(2)),
                        matcher.group(3),
                        matcher.group(4),
                        matcher.group(5),
                        Integer.parseInt(matcher.group(6)),
                        Integer.parseInt(matcher.group(7))
                        ));
            }
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
                if(matcher.group(2).equals("Trap"))
                    temp.put(matcher.group(1).replace("%",",").replace("\"",""),
                            new SpellAndTrap(
                                    matcher.group(1).replace("%",",").replace("\"",""),
                                    Type.TRAP,
                                    matcher.group(4).replace("%",",").replace("\"",""),
                                    Integer.parseInt(matcher.group(6)),
                                    matcher.group(3),
                                    matcher.group(5)
                            ));
                else
                    temp.put(matcher.group(1).replace("%",",").replace("\"",""),
                            new SpellAndTrap(
                                    matcher.group(1).replace("%",",").replace("\"",""),
                                    Type.SPELL,
                                    matcher.group(4).replace("%",",").replace("\"",""),
                                    Integer.parseInt(matcher.group(6)),
                                    matcher.group(3),
                                    matcher.group(5)
                            ));
            }
        }
        br1.close();
        return (temp);
    }

}



