package controllers.menues;

import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoardMenu {

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
        for (User user : users) {
            UserInterface.printResponse(rank + "- " + user.getNickName() + ":" + user.getScore());
            rank++;
        }

    }
}
