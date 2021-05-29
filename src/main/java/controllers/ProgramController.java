package controllers;

import controllers.Constants.Initialize;
import javafx.stage.Stage;
import models.User;
import view.WelcomeMenu;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;
    public static User currUser;

    public static void run(Stage stage) {
        //load data from dataBase
        Initialize.init();
        //start from login menu
        new WelcomeMenu().start(stage);
    }

    public static void setLoggedInUsers(User user) {
        currUser = user;
    }

    public static void setLoggedOutUsers() {
        currUser = null;
    }
}
