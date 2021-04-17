package controllers;

import models.Deck;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataBase {

    static public  void addDeck(Deck deck){
        String deckName = deck.getDeckName();
        try {
            File myObj = new File(deckName + ".txt");
            if (myObj.createNewFile()) System.out.println("File created: " + myObj.getName());
            else System.out.println("File already exists.");

            FileWriter myWriter = new FileWriter(myObj);
            myWriter.write("be tricky, but it is fun enough!");
            myWriter.close();

        } catch (
                IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static public Deck getDeckByName(String name) {
        File myObj = new File(name + ".txt");
        if (myObj.exists()){
            System.out.println("File created: " + myObj.getName());
        }
        return null;
    }
}
