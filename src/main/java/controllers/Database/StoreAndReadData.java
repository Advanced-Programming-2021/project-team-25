package controllers.Database;
import models.User;

import java.io.*;
import java.util.*;
public class StoreAndReadData {
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
                System.out.println("Loaded Data from file");
                FileInputStream fin = new FileInputStream(savedArrayListName);
                ObjectInputStream ois = new ObjectInputStream(fin);

                ArrayList<User> myClassObj = (ArrayList<User>) ois.readObject();
                ois.close();
                return myClassObj;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
