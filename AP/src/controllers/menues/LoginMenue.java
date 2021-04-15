package controllers.menues;

import controllers.Menue;
import controllers.ProgramController;
import controllers.Regex;
import controllers.Response;
import models.User;

import java.util.regex.Matcher;

public class LoginMenue {
    public void run(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.register)).matches()) {
            registerNewUser(matcher);
        } else if ((matcher = Regex.getMatcher(command, Regex.login)).matches()) {
            loginUser(matcher);
        } else if ((matcher = Regex.getMatcher(command, Regex.remove)).matches()) {
            removeUser(matcher);
        } else if ((Regex.getMatcher(command, Regex.exit)).matches()) {
            ProgramController.currentMenue = Menue.EXIT;
            System.out.println(Response.programEnded);
        } else if ((Regex.getMatcher(command, Regex.listUsers)).matches()) {
            User.listUsers();
        } else if ((Regex.getMatcher(command, Regex.help)).matches()) {
            help();
        } else {
            System.out.println(Response.invalidFormat);
        }
    }
}
