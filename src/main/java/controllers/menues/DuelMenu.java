package controllers.menues;

import controllers.Battelfield.Battlefield;
import controllers.Menu;
import controllers.Regex;
import models.Deck;
import models.Duelist;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;

public class DuelMenu {

    private static DuelMenu singleToneClass = null;
    private final User currUser;

    public static DuelMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DuelMenu(currUser);
        return singleToneClass;
    }

    private DuelMenu(User currUser){
        this.currUser=currUser;
        runDuelMenu();
    }

    public void runDuelMenu(){
        while (currentMenu == Menu.DUEL_MENU) {
            String command = UserInterface.getUserInput();
            Matcher matcher;

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if ((matcher = Regex.getMatcher(command, Regex.duelNewAi)).matches()) newDuelAi(matcher);
            else if ((matcher = Regex.getMatcher(command, Regex.duelNew)).matches()) newDuel(matcher);
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    private void newDuelAi(Matcher matcher){
        String round = matcher.group(1);
        if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
        else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
        else if(!(round.equals("1") || round.equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
        //else new Battlefield(new Duelist(currUser),new Duelist(User.getUserByUsername(duelistName)));
    }

    private void newDuel(Matcher matcher){
        String duelistName = matcher.group(1) , round = matcher.group(2);
        if(User.getUserByUsername(duelistName) == null) UserInterface.printResponse("there is no player with this username");
        else if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + " has no active deck");
        else if(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck == null) System.out.println(duelistName + " has no active deck");
        else if(!Deck.isValid(currUser.activeDeck.getDeckName())) UserInterface.printResponse(currUser.getUsername() + "'s deck is not valid");
        else if(!Deck.isValid(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck.getDeckName())) UserInterface.printResponse(duelistName + "'s deck is not valid");
        else if(!(round.equals("1") || round.equals("3"))) UserInterface.printResponse(Responses.NOT_SUPPORTED_ROUNDS);
        else new Battlefield(new Duelist(currUser),new Duelist(Objects.requireNonNull(User.getUserByUsername(duelistName))));
    }

}
