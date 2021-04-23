package controllers.menues;

import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;

public class DuelMenu {

    private final User currUser;

    public DuelMenu(User currUser){
        this.currUser=currUser;
    }
    public void runDuelMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.duelNew)).matches()) newDuel(matcher);
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void newDuel(Matcher matcher){
    String duelistName = matcher.group(1) , round = matcher.group(2);
    if(User.getUserByUsername(duelistName) == null)
        System.out.println("there is no player with this username");
    else if(currUser.activeDeck == null) System.out.println(currUser.getUsername() + "has no active deck");
    else if(Objects.requireNonNull(User.getUserByUsername(duelistName)).activeDeck == null) System.out.println(duelistName + "has no active deck");



    }
}
