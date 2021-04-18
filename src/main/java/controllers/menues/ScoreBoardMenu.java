package controllers.menues;

import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

public class ScoreBoardMenu {
//    public void runScoreBoardMenu(String command){
//        if (Regex.getMatcher(command, Regex.scoreBoardShow).matches()) showScoreBoard();
//        else UserInterface.printResponse(Responses.INVALID_COMMAND);
//    }

//    private  void showScoreBoard(){
//        int i = 0;
//        User[] users = new User[allUsers.size()];
//        User temp;
//        for (User user: allUsers) {
//            users[i] = user;
//            i++;
//        }
//        for (int j = 0; j < allUsers.size(); j++) {
//            for (int k = 0; k < allUsers.size() - 1 ; k++) {
//                if(users[k].getScore() < users[k+1].getScore()){
//                    temp = users[k];
//                    users[k] = users[k+1];
//                    users[k+1] = temp;
//                }
//                else if(users[k].getScore() == users[k+1].getScore()){
//                    if(users[k].getNickName().compareTo(users[k+1].getNickName()) < 0 ){
//                        temp = users[k];
//                        users[k] = users[k+1];
//                        users[k+1] = temp;
//                    }
//                }
//            }
//        }
//        int rank = 1;
//        for (int j = 0; j < allUsers.size(); j++) {
//            System.out.println(rank + " " + users[j].toString());
//            if( users[j].getScore() != users[j+1].getScore() ) rank = j+2;
//        }
//
//    }
}
