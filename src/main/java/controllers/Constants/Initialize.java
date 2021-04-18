package controllers.Constants;

import controllers.DataBase;
import controllers.Database.StoreAndReadData;
import models.User;

public class Initialize {

    public static void init() {
        User.setUsers(StoreAndReadData.loadTheList());
    }
}
