package controllers.menues;

import controllers.Regex;
import controllers.Response;

import java.util.regex.Matcher;

public class MainMenu {

    public void runMainMenu(String command){
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
