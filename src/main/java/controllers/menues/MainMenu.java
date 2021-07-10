package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class MainMenu {

    private final User currUser;
    private boolean isUserLoggedOut = false;

    public MainMenu(User currUser) {
        this.currUser=currUser;
        String command = UserInterface.getUserInput();
        while(!command.equals("menu exit") && !isUserLoggedOut){

            if(command.startsWith("menu enter")) changeMenu(Regex.getMatcher(command, Regex.menuEnter),currUser);
            else if(command.equals("user logout")) logoutUser();
            else if(command.equals("menu Show Current")) UserInterface.printResponse("view.Main Menu");
            else UserInterface.printResponse(Responses.INVALID_COMMAND);

            if(!isUserLoggedOut) command = UserInterface.getUserInput();
        }
    }

    static void changeMenu(Matcher matcher, User currUser){
        if(matcher.find()) {
            //DeckMenu.getInstance(currUser).runDeckMenu();
            switch (matcher.group(1)) {
                case "Duel" -> {
                    ProgramController.currentMenu = Menu.DUEL_MENU;
                    DuelMenuController.getInstance(currUser).runDuelMenu();
                }
                case "Shop" -> {
                    ProgramController.currentMenu = Menu.SHOP_MENU;
                    ShopMenu.getInstance(currUser).runShopMenu(currUser);
                }
                case "Deck" -> ProgramController.currentMenu = Menu.DECK_MENU;
                case "Profile" -> {
                    ProgramController.currentMenu = Menu.PROFILE_MENU;
                    ProfileMenu.getInstance(currUser).runProfileMenu(currUser);
                }
                case "Scoreboard" -> {
                    ProgramController.currentMenu = Menu.SCOREBOARD_MENU;
                    ScoreBoardMenu.getInstance().runScoreBoardMenu();
                }
                case "Import/Export" -> {
                    ProgramController.currentMenu = Menu.IMPORT_EXPORT_MENU;
                    ImportExportMenu.getInstance().runIMPORTEXPORTMenu();
                }
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
