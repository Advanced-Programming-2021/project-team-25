package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class ProfileMenu {
    private User currUser;
    private static ProfileMenu singleToneClass = null;

    public static ProfileMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new ProfileMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }
    private ProfileMenu(User currUser){
        this.currUser=currUser;
    }

    public void runProfileMenu(User user){
    currUser = user;
    String command;
        while(true){
            command = UserInterface.getUserInput();
            if(command.startsWith("Menu enter")) UserInterface.printResponse(Responses.NOT_POSSIBLE_NAVIGATION);
            else if(command.startsWith("profile change --nickname")) changeNickname(Regex.getMatcher(command,Regex.changeNickname));
            else if(command.startsWith("profile change --password")) changPass(Regex.getMatcher(command,Regex.changePassword));
            else if(command.equals("menu show-current")) UserInterface.printResponse("Profile Menu");
            else if (command.equals("menu exit")){
                ProgramController.currentMenu = Menu.MAIN_MENU;
                break;
            }
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }

    }
    public void changeNickname(Matcher matcher){
        if(matcher.find()){
            String newNickname = matcher.group(1);
            currUser.setNickName(newNickname);
        }
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }
    public void changPass(Matcher matcher){
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
