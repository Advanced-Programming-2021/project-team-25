package controllers.menues;

import controllers.Menue;
import controllers.ProgramController;
import controllers.Regex;
import controllers.Response;
import models.User;

import java.util.regex.Matcher;

public class MainMenue {
    public void run(String command){
        Matcher matcher;
        if ((matcher = Regex.getMatcher(command, Regex.menuEnter)).matches()) directMenu(matcher);
        else if (Regex.getMatcher(command, Regex.menuExit).matches()) quit();
        else System.out.println(Response.invalidFormat);
    }

    private void directMenu(Matcher matcher){

    }

    private void quit(){

    }

}
