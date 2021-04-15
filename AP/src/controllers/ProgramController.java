package controllers;

import controllers.menues.*;

import java.util.Scanner;

public class ProgramController {

    public static Menue currentMenue = Menu.LOGIN_MENU;
    public static Scanner scanner = new Scanner(System.in);

    public void run() {
        while ( currentMenue != Menue.EXIT ) {
            String command = scanner.nextLine();

            if (currentMenue == Menue.LOGIN_MENUE) new LoginMenu().runLoginmenu(command);

            else if (currentMenue == Menue.MAIN_MENUE) {
                new MainMenue().run(command);
                if ( currentMenue == Menue.DUEL_MENUE ) new DuelMenue().run(command);
                else if ( currentMenue == Menue.DECK_MENUE ) new DeckMenue().run(command);
                else if ( currentMenue == Menue.SHOP_MENUE ) new ShopMenue().run(command);
                else if ( currentMenue == Menue.PROFILE_MENUE ) new ProfileMenue().run(command);
            }

            else if (currentMenue == Menue.DECK_MENUE)  new DeckMenue().run(command);
            else if (currentMenue == Menue.SHOP_MENUE)  new ShopMenue().run(command);
            else if (currentMenue == Menue.DUEL_MENUE)  new DuelMenue().run(command);
            else if (currentMenue == Menue.PROFILE_MENUE)  new ProfileMenue().run(command);
        }
    }

}
