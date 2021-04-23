package controllers.menues;

import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreBoardMenu {
    public void runScoreBoardMenu(String command){
        if (Regex.getMatcher(command, Regex.scoreBoardShow).matches()) showScoreBoard();
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void showScoreBoard(){
        //getting users
        ArrayList<User> users = User.getUsers();
        //compare users
        Comparator<User> orderedUsers = Comparator.comparing(User::getScore).thenComparing(User::getNickName);
        //sorting users
        users.sort(orderedUsers);
        //for ranking users
        int rank=1;
        //itrate users
        for (User user : users) {
            UserInterface.printResponse(rank + " " + user.toString());
            rank++;
        }
        /*int i = 0;
        User[] allUsers = new User[User.getUsers().size()];
        User temp;
        for (User user: User.getUsers()) {
            allUsers[i] = user;
            i++;
        }
        for (int j = 0; j < User.getUsers().size(); j++) {
            for (int k = 0; k < User.getUsers().size() - 1 ; k++) {
                if(allUsers[k].getScore() < allUsers[k+1].getScore()){
                    temp = allUsers[k];
                    allUsers[k] = allUsers[k+1];
                    allUsers[k+1] = temp;
                }
                else if(allUsers[k].getScore() == allUsers[k+1].getScore()){
                    if(allUsers[k].getNickName().compareTo(allUsers[k+1].getNickName()) < 0 ){
                        temp = allUsers[k];
                        allUsers[k] = allUsers[k+1];
                        allUsers[k+1] = temp;
                    }
                }
            }
        }
        int rank = 1;
        for (int j = 0; j < User.getUsers().size(); j++) {
            System.out.println(rank + " " + allUsers[j].toString());
            if( allUsers[j].getScore() != allUsers[j+1].getScore() ) rank = j+2;
        }*/

    }
}
