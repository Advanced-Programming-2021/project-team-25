package controllers.Constants;

import controllers.Database.DataBase;
import models.User;

public class Initialize {

    public static void init() {
        User.setUsers(DataBase.loadTheList());
    }
}
