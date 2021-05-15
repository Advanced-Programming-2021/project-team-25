package controllers.menues;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.Menu;
import controllers.Regex;
import models.Card;
import view.Responses;
import view.UserInterface;

import static controllers.ProgramController.currentMenu;

public class ImportExportMenu {

    private static ImportExportMenu singleToneClass = null;

    public static ImportExportMenu getInstance(){
        if (singleToneClass == null) singleToneClass = new ImportExportMenu();
        return singleToneClass;
    }

    private ImportExportMenu(){
        runIMPORTEXPORTMenu();
    }

    public void runIMPORTEXPORTMenu(){
        while (currentMenu == Menu.IMPORTEXPORT_MENU) {
            String command = UserInterface.getUserInput();

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if (Regex.getMatcher(command, Regex.importCard).matches()) importCard();
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    public void importCard() {
        String name = UserInterface.getUserInput();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(Card.allCards.get(name)));
    }

}
