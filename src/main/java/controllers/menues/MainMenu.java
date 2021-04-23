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

    public MainMenu(User currUser) {
        this.currUser=currUser;
        String command = UserInterface.getUserInput();
        while(!command.equals("menu exit")){
            if(command.startsWith("Menu enter")) changeMenu(Regex.getMatcher(command, Regex.menuEnter),command);
            else if(command.equals("user logout")) logoutUser();
            else if(command.equals("menu show-current")) UserInterface.printResponse("Main Menu");
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }
    private void changeMenu(Matcher matcher, String command){
        if(matcher.find()) {
            switch (matcher.group(1)) {
                case "Duel":
                    ProgramController.currentMenu = Menu.DUEL_MENU;
                    new DuelMenu().runDuelMenu(command);
                    break;
                case "Shop":
                    ProgramController.currentMenu = Menu.SHOP_MENU;
                    break;
                case "Deck":
                    ProgramController.currentMenu = Menu.DECK_MENU;
                    new DeckMenu(currUser).runDeckMenu(command);
                    break;
                case "ScoreBoard":
                    ProgramController.currentMenu = Menu.SCOREBOARD_MENU;
                    break;
                case "Profile":
                    ProgramController.currentMenu = Menu.PROFILE_MENU;
                    new ProfileMenu().runProfileMenu(command);
                    break;
            }
        }
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }
    private void logoutUser(){
        ProgramController.setLoggedOutUsers(currUser);
        currUser.setIsLoggedIn(false);
        UserInterface.printResponse(Responses.LOGOUT_SUCCESS);
    }
}
