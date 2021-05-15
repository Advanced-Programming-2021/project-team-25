package controllers;

import controllers.Constants.Initialize;
import controllers.menues.*;
import models.User;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;
    public static User currUser;

    public static void run() {
        //load data from dataBase
        Initialize.init();
        //start from login menu
        LoginMenu.runLoginMenu();
    }

    public static void setLoggedInUsers(User user) {
        currUser = user;
    }

    public static void setLoggedOutUsers() {
        currUser = null;
    }
}
