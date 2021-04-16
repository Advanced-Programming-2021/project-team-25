package controllers.menues;

import controllers.Regex;
import controllers.Response;


public class ScoreBoardMenu {
    public void runScoreBoardMenu(String command){

        if (Regex.getMatcher(command, Regex.scoreBoardShow).matches()) showScoreBoard();
        else System.out.println(Response.invalidFormat);
    }

    private  void showScoreBoard(){

    }
}
