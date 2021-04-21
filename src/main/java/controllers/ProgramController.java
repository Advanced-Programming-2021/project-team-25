package controllers;

import controllers.Constants.Initialize;
import controllers.menues.*;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;
    public User loggedUser = null;

    public void run() {
        Initialize.init();

        while (currentMenu != Menu.EXIT) {
            String command = UserInterface.getUserInput();

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches())
                System.out.println(currentMenu);
            else if(Regex.getMatcher(command, Regex.menuEnter).matches())
                directMenu(Regex.getMatcher(command, Regex.menuEnter));
            else if(Regex.getMatcher(command, Regex.menuExit).matches())
                exitMenu();
            else if (currentMenu == Menu.LOGIN_MENU)
                new LoginMenu().runLoginMenu(command);
            else if (currentMenu == Menu.DUEL_MENU)
                new DuelMenu().runDuelMenu(command);
            else if (currentMenu == Menu.DECK_MENU)
                new DeckMenu().runDeckMenu(command);
//            else if (currentMenu == Menu.SHOP_MENU)
//                new ShopMenu().runShopMenu(command);
            else if (currentMenu == Menu.PROFILE_MENU)
                new ProfileMenu().runProfileMenu(command);
            else if (currentMenu == Menu.SCOREBOARD_MENU)
                new ScoreBoardMenu().runScoreBoardMenu(command);
            else
                UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    private void directMenu(Matcher matcher){
        if ( loggedUser == null ) System.out.println("please login first");
        else if(matcher.group(1).equals("Main")) currentMenu = Menu.MAIN_MENU;
        else if(currentMenu == Menu.MAIN_MENU || currentMenu  == Menu.LOGIN_MENU){
            switch (matcher.group(1)) {
                case "Login":
                    currentMenu = Menu.LOGIN_MENU;
                    break;
                case "Duel":
                    currentMenu = Menu.DUEL_MENU;
                    break;
                case "Shop":
                    currentMenu = Menu.SHOP_MENU;
                    break;
                case "Deck":
                    currentMenu = Menu.DECK_MENU;
                    break;
                case "ScoreBoard":
                    currentMenu = Menu.SCOREBOARD_MENU;
                    break;
                case "Profile":
                    currentMenu = Menu.PROFILE_MENU;
                    break;
            }
        }
        else System.out.println("menu navigation is not possible");
    }

    private void exitMenu(){
        if(currentMenu == Menu.LOGIN_MENU ) currentMenu = Menu.EXIT;
        else if(currentMenu == Menu.MAIN_MENU ) currentMenu = Menu.LOGIN_MENU;
        else currentMenu = Menu.MAIN_MENU;
    }

}
