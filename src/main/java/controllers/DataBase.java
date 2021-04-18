package controllers;

import com.google.gson.Gson;
import models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataBase {

    public void createJsonForUser(User user) {
        try {
            String fileName="/userDatabase/"+user.getUsername() + ".json";
            File myFile = new File(fileName);
            if (myFile.createNewFile()) System.out.println("File created: " + myFile.getName());
            else System.out.println("File already exists.");

            // create Gson instance
            Gson gson = new Gson();

            // create a writer
            Writer writer = Files.newBufferedWriter(Paths.get(fileName));

            // convert book object to JSON file
            gson.toJson(user, writer);

            writer.close();

        } catch (
                IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
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
