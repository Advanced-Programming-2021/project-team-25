package controllers.menues;

import controllers.Menu;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;

import static controllers.ProgramController.currentMenu;

public class ScoreBoardMenu {

    private static ScoreBoardMenu singleToneClass = null;
    private final User currUser;

    public static ScoreBoardMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new ScoreBoardMenu(currUser);
        return singleToneClass;
    }

    private ScoreBoardMenu(User currUser){
        this.currUser=currUser;
        runScoreBoardMenu();
    }

    public void runScoreBoardMenu(){
        while (currentMenu == Menu.SCOREBOARD_MENU) {
            String command = UserInterface.getUserInput();

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if (Regex.getMatcher(command, Regex.scoreBoardShow).matches()) showScoreBoard();
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    static void showScoreBoard(){
        //getting users
        ArrayList<User> users = User.getUsers();
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getNickName);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank=1;
        //iterate users
        for (int i = 0 ; i < users.size() ; i++ ) {
            UserInterface.printResponse(rank + "- " + users.get(i).getNickName() + ":" + users.get(i).getScore());
            if(i != users.size() - 1 && users.get(i).getScore() > users.get(i+1).getScore() ) rank++;
        }

    }
}
