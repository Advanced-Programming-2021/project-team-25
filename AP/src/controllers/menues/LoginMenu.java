package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import controllers.Response;
import models.User;

import java.util.regex.Matcher;

public class LoginMenu {
    public void runLoginMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.register)).matches()) registerNewUser(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.login)).matches()) loginUser(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.userLogout)).matches()) logoutUser(matcher);

        else System.out.println(Response.invalidFormat);
    }

    private void registerNewUser(Matcher matcher){

    }

    private void loginUser(Matcher matcher){

    }

    private void logoutUser(Matcher matcher){

    }
}
