package controllers.Constants;

import controllers.Database.DataBase;
import models.User;

import java.io.File;

public class Initialize {

    public static void init() {
        initUserList();
    }
    public static void initUserList(){
        File tmpDir = new File("savedList.list");
        if(tmpDir.exists()) User.setUsers(DataBase.loadTheList());
        else new User("admin","admin","Game");
    }
}
