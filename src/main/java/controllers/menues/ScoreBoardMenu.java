package controllers.menues;

import controllers.Menu;
import controllers.Regex;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Responses;
import view.UserInterface;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

import static controllers.ProgramController.currentMenu;

public class ScoreBoardMenu {

    private static ScoreBoardMenu singleToneClass = null;

    public static ScoreBoardMenu getInstance(){
        if (singleToneClass == null) singleToneClass = new ScoreBoardMenu();
        return singleToneClass;
    }

    private ScoreBoardMenu(){
        runScoreBoardMenu();
    }

    public void runScoreBoardMenu(){
        while (currentMenu == Menu.SCOREBOARD_MENU) {
            String command = UserInterface.getUserInput();

            if (Regex.getMatcher(command, Regex.menuShowCurrent).matches()) System.out.println(currentMenu);
            else if (Regex.getMatcher(command, Regex.menuEnter).matches()) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if (Regex.getMatcher(command, Regex.menuExit).matches()) currentMenu = Menu.MAIN_MENU;
            else if (Regex.getMatcher(command, Regex.scoreBoardShow).matches()) showScoreBoard(User.getUsers());
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    static void showScoreBoard(ArrayList<User> users){
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getNickName);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank=1;
        //iterate users
        for (int i = users.size() - 1 ; i >= 0 ; i-- ) {
            if(users.get(i).getNickName().equals("Game")) continue;
            System.out.print((rank + "- " + users.get(i).getNickName() + " : " + users.get(i).getScore()) + "\n");
            if(i != 0 && users.get(i).getScore() > users.get(i-1).getScore() ) rank++;
        }
    }

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("alireza" , "alireza", 1000));
        users.add(new User("danial" , "danial", 10000));
        users.add(new User("kevin" , "kevin", 100));
        users.add(new User("alireza" , "alireza", 1000));
        users.add(new User("alireza" , "alireza", 1000));
        showScoreBoard(users);
    }
    @Test
    public void addition() {
        String expected = "1- danial : 10000\n" +
                "2- alireza : 1000\n" +
                "2- alireza : 1000\n" +
                "2- alireza : 1000\n" +
                "3- kevin : 100\n";
        Assertions.assertEquals(expected.trim(), outputStreamCaptor.toString().trim());
    }
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
