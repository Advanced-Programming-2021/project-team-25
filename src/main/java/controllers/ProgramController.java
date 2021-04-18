package controllers;

import controllers.menues.*;
import models.User;
import view.UserInterface;

import static models.User.allUsers;
import static models.Deck.allDecks;

public class ProgramController {

    public static Menu currentMenu = Menu.LOGIN_MENU;
    public User loggedUser = null;
    public void run() {

        allUsers = DataBase.restoreUsers();
        allDecks = DataBase.restoreDecks();

        while (currentMenu != Menu.EXIT) {
            String command = UserInterface.getUserInput();
            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);

            else if (currentMenu == Menu.LOGIN_MENU) new LoginMenu().runLoginMenu(command);

            else if (currentMenu == Menu.MAIN_MENU) {
                new MainMenu().runMainMenu(command);
                chooseMenu(command);
            }
            chooseMenu(command);
        }

        DataBase.storeData();
    }

    private void chooseMenu(String command) {
        if (currentMenu == Menu.DUEL_MENU) new DuelMenu().runDuelMenu(command);
        else if (currentMenu == Menu.DECK_MENU) new DeckMenu().runDeckMenu(command);
        else if (currentMenu == Menu.SHOP_MENU) new ShopMenu().runShopMenu(command);
        else if (currentMenu == Menu.PROFILE_MENU) new ProfileMenu().runProfileMenu(command);
        else if (currentMenu == Menu.SCOREBOARD_MENU) new ScoreBoardMenu().runScoreBoardMenu(command);
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
