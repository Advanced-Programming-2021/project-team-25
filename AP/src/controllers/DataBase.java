package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataBase {
    public void Database() {
        String deckName = "";
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
}
