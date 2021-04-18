package controllers.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Deck;
import models.User;

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
}
