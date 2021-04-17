package controllers;

import com.google.gson.Gson;
import models.Deck;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class DataBase {

    static public void addDeck(Deck deck){
        try {
            File myObj = new File(deck.getId() + ".txt");

            FileWriter myWriter = new FileWriter(myObj);
            myWriter.write(new Gson().toJson(deck));
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void addUser(User user){
        try {
            File myObj = new File(user.getUsername() + ".txt");

            FileWriter myWriter = new FileWriter(myObj);
            myWriter.write(new Gson().toJson(user));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Deck getDeckByName(String name) {
        for (int i = 1; i <= 10000 ; i++) {
            File myObj = new File(i + ".txt");
            if(myObj.exists()) {
                getFileAsString(myObj);
                Deck deck = new Gson().fromJson(myObj.toString(), Deck.class);
                if (deck.getDeckName().equals(name)) return deck;
            }
        }
        return null;
    }

    static public User getUserByName(String name) {
        File myObj = new File(name + ".txt");
        if(myObj.exists()) return new Gson().fromJson(getFileAsString(myObj), User.class);
        return null;
    }

    private static String getFileAsString(File myObj) {
        Scanner myReader = null;
        String data = "";
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (Objects.requireNonNull(myReader).hasNextLine())
            data = data.concat(myReader.nextLine());

        myReader.close();
        return data;
    }

    static public void deleteDeck(String name){
        for (int i = 1; i <= Deck.getIdCounter() ; i++) {
            File myObj = new File(i + ".txt");
            getFileAsString(myObj);
            Deck deck = new Gson().fromJson(myObj.toString(), Deck.class);
            if(deck.getDeckName().equals(name)) myObj.delete();
        }
    }
}
