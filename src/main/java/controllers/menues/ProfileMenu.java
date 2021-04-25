package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class ProfileMenu {
    private static User currUser;

    public static void runProfileMenu(User user){
    currUser=user;
    String command = UserInterface.getUserInput();
        while(true){
            if(command.startsWith("Menu enter")) MainMenu.changeMenu(Regex.getMatcher(command, Regex.menuEnter),currUser);
            else if(command.startsWith("profile change --nickname")) changeNickname(Regex.getMatcher(command,Regex.changeNickname));
            else if(command.equals("profile change --password")) changPass(Regex.getMatcher(command,Regex.changePassword));
            else if(command.equals("menu show-current")) UserInterface.printResponse("Main Menu");
            else if (command.equals("menu exit")){
                ProgramController.currentMenu = Menu.MAIN_MENU;
                break;
            }
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }

    }
    public static void changeNickname(Matcher matcher){
        if(matcher.find()){
            String newNickname = matcher.group(1);
            currUser.setNickName(newNickname);
        }
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }
    public static void changPass(Matcher matcher){
        if(matcher.find()){
            String currentPass = matcher.group(1);
            String newPass = matcher.group(2);
            if(!currUser.getPassword().equals(currentPass))
                UserInterface.printResponse("current password is invalid");
            else if(currUser.getPassword().equals(newPass))
                UserInterface.printResponse("please enter a new password");
            else{
                currUser.setPassword(newPass);
                UserInterface.printResponse("password changed successfully!");
            }
        }
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

}
