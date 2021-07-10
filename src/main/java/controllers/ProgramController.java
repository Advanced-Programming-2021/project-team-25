package controllers;

import models.User;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;
    public static User currUser;

    public static void run() {

    }

    public static void setLoggedInUsers(User user) {
        currUser = user;
    }

    public static void setLoggedOutUsers() {
        currUser = null;
    }
}
