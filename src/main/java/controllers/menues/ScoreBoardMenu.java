package controllers.menues;

import controllers.Regex;
import view.Responses;
import view.UserInterface;

public class ScoreBoardMenu {
    public void runScoreBoardMenu(String command){
        if (Regex.getMatcher(command, Regex.scoreBoardShow).matches()) showScoreBoard();
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private  void showScoreBoard(){

    }
}
