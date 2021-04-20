package controllers.Constants;

import controllers.Database.DataBase;
import models.Cards.Monsters.Monster;
import models.User;

import java.io.File;
import java.io.IOException;

public class Initialize {

    public static void init() {
        initUserList();
        try {
            Monster.allMonsters = DataBase.loadMonsters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void initUserList(){
        File tmpDir = new File("savedList.list");
        if(tmpDir.exists()) User.setUsers(DataBase.loadTheList());
        else new User("admin","admin","Game");
    }
}
