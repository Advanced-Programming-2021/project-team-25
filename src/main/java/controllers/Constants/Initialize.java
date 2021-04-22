package controllers.Constants;

import controllers.Database.DataBase;
import models.Card;
import models.Deck;
import models.User;

import java.io.File;
import java.io.IOException;

public class Initialize {

    public static void init() {
        initUserList();
        try {
            Card.allCards = DataBase.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Deck.allDecks = DataBase.restoreDecks();
    }

    public static void initUserList(){
        File tmpDir = new File("savedList.list");
        if(tmpDir.exists()) User.setUsers(DataBase.loadTheList());
        else new User("admin","admin","Game");
    }

}
