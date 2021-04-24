package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class MainMenu {
    private User currUser;
    private boolean isUserLoggedOut = false;
    public MainMenu(User currUser) {
        this.currUser=currUser;
        String command = UserInterface.getUserInput();
        while(!command.equals("menu exit") && !isUserLoggedOut){
            if(command.startsWith("Menu enter")) changeMenu(Regex.getMatcher(command, Regex.menuEnter),command,currUser);
            else if(command.equals("user logout")) logoutUser();
            else if(command.equals("scoreboard show")) ScoreBoardMenu.showScoreBoard();
            else if(command.equals("menu show-current")) UserInterface.printResponse("Main Menu");
            else UserInterface.printResponse(Responses.INVALID_COMMAND);

            if(!isUserLoggedOut) command = UserInterface.getUserInput();
        }
    }
    static void changeMenu(Matcher matcher, String command, User currUser){
        if(matcher.find()) {
            switch (matcher.group(1)) {
                case "Duel" :
                    ProgramController.currentMenu = Menu.DUEL_MENU;
                    DuelMenu.getInstance(currUser);
                    break;
                case "Shop" :
                    ProgramController.currentMenu = Menu.SHOP_MENU;
                    /*run your menu by function*/
                    break;
                case "Deck" :
                    ProgramController.currentMenu = Menu.DECK_MENU;
                    /*run your menu by function*/
                    break;
                case "Profile" :
                    ProgramController.currentMenu = Menu.PROFILE_MENU;
                    ProfileMenu.runProfileMenu(currUser);
                    break;
            }
        }
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }
    private void logoutUser(){
        ProgramController.setLoggedOutUsers();
        currUser.setIsLoggedIn(false);
        UserInterface.printResponse(Responses.LOGOUT_SUCCESS);
        isUserLoggedOut = true;
    }
}
