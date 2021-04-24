package controllers.menues;

import com.google.inject.Singleton;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;

public class DuelMenu {

    private static DuelMenu single_instance = null;
    private final User currUser;

    private DuelMenu(User currUser){
        this.currUser=currUser;
        runDuelMenu(UserInterface.getUserInput());
    }

    public void runDuelMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.duelNew)).matches()) newDuel(matcher);
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void newDuel(Matcher matcher){
    String duelistName = matcher.group(1) , round = matcher.group(2);
    if(User.getUserByUsername(duelistName) == null)
        UserInterface.printResponse("there is no player with this username");
    else if(currUser.activeDeck == null) UserInterface.printResponse(currUser.getUsername() + "has no active deck");
    else if(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck == null) System.out.println(duelistName + "has no active deck");

    }

    // static method to create instance of Singleton class
    public static DuelMenu getInstance(User currUser)
    {
        if (single_instance == null)
            single_instance = new DuelMenu(currUser);
        return single_instance;
    }
}
