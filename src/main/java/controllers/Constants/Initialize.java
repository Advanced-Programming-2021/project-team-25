package controllers.Constants;

import controllers.Database.DataBase;
import models.Deck;
import models.User;

import java.io.IOException;

public class Initialize {

    public static void init() {
        initUserList();
        Deck.allDecks = DataBase.restoreDecks();
        try {
            DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initUserList(){
        new User("admin","admin","Game");
        User.setUsers(DataBase.loadTheList());
    }

}
