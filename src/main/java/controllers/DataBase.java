package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Deck;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static models.Deck.allDecks;
import static models.User.allUsers;

public class DataBase {

    static public void storeData(){
        try {
            File myObjUser = new File(  "allUsers.txt");

            FileWriter myWriterUser = new FileWriter(myObjUser);
            myWriterUser.write(new Gson().toJson(allUsers));
            myWriterUser.close();

            File myObjDeck = new File(  "allDecks.txt");

            FileWriter myWriterDeck = new FileWriter(myObjDeck);
            myWriterDeck.write(new Gson().toJson(allDecks));
            myWriterDeck.close();
        } catch (IOException e) {
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
