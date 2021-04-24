package controllers;

import controllers.Constants.Initialize;
import controllers.Database.DataBase;
import controllers.menues.*;
import models.Card;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;

      public static User currUser;

    public static void run() {

        Initialize.init();

        LoginMenu.runLoginMenu(UserInterface.getUserInput());
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
