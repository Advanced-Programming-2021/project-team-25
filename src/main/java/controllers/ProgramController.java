package controllers;

import controllers.Constants.Initialize;
import controllers.menues.*;
import models.User;
import view.UserInterface;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;
    public static User currUser;

    public static void run() {
        //load data from dataBase
        Initialize.init();
        //start from login menu
        LoginMenu.runLoginMenu();
    }

    private static void exitMenu(){
        if(currentMenu == Menu.LOGIN_MENU ) currentMenu = Menu.EXIT;
        else if(currentMenu == Menu.MAIN_MENU ) currentMenu = Menu.LOGIN_MENU;
        else currentMenu = Menu.MAIN_MENU;
    }

    public static void setLoggedInUsers(User user) {
        currUser = user;
    }

    public static void setLoggedOutUsers() {
        currUser = null;
    }
}
