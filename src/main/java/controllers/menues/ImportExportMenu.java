package controllers.menues;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.Menu;
import controllers.Regex;
import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import view.Responses;
import view.UserInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;

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
        while (currentMenu == Menu.IMPORT_EXPORT_MENU) {
            String command = UserInterface.getUserInput();
            Matcher matcher ;

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if ((matcher = Regex.getMatcher(command, Regex.importCard)).matches()) importCard(matcher);
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    public void importCard(Matcher matcher) {
        String name = matcher.group(1);

        if (!Card.allCards.containsKey(name)) UserInterface.printResponse("there is no card with this name");
        else{
            if (Card.allCards.get(name).getCardsType() == Type.MONSTER){
                Monster monster = (Monster) Card.allCards.get(name);
                try {
                    FileWriter myWriter = new FileWriter(name + ".txt");
                    myWriter.write("Name: " + name + "\n" +
                            "Level: " + monster.getLevel() + "\n" +
                            "Type: " + monster.getCardTypeInExel() + "\n" +
                            "ATK: " + monster.getAttack() + "\n" +
                            "DEF: " + monster.getDefence() + "\n" +
                            "Description: " + monster.getDescription());
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            else{
                SpellAndTrap spellAndTrap = (SpellAndTrap) Card.allCards.get(name);
                if (spellAndTrap.getCardsType() == Type.SPELL) {
                    try {
                        FileWriter myWriter = new FileWriter(name + ".txt");
                        myWriter.write("Name: " + name + "\n" +
                                "Spell\n" +
                                "Type: " + spellAndTrap.getIcon() + "\n" +
                                "Description: " + spellAndTrap.getDescription());
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        FileWriter myWriter = new FileWriter(name + ".txt");
                        myWriter.write("Name: " + name + "\n" +
                                "Trap\n" +
                                "Type: " + spellAndTrap.getIcon() + "\n" +
                                "Description: " + spellAndTrap.getDescription());
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
