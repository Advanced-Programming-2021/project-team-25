package controllers.menues;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {
    public void runLoginMenu(String command){

        if (command.startsWith("user login")) loginUser(command);
        else if (command.startsWith("user create")) createNewUser(Regex.getMatcher(command,Regex.userCreate));
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void createNewUser(Matcher matcher){
        if(matcher.find()) {
            String username = matcher.group("username");
            String password = matcher.group("password");
            String nickname = matcher.group("nickname");

            if(isExistUsername(username))
                UserInterface.printResponse("user with username " + username + " already exist!");
            else if(isExistNickname(nickname))
                UserInterface.printResponse("user with nickname " + nickname + " already exist!");
            else
                UserInterface.printResponse(Responses.USER_CREATE_SUCCESS);
                new User(username,password,nickname);
        }
        else
            UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void loginUser(String command){
        String username;
        String password;
        Matcher matcher = Pattern.compile(Regex.userLogin).matcher(command);

        if(matcher.find()) {
            //checking which group is been captured
            if(!Objects.isNull(matcher.group("group1"))) {
             password = matcher.group("password");
             username = matcher.group("username");
            }
            else {
              password = matcher.group("password2");
              username = matcher.group("username2");
            }
            //checking correct password
            if(!Objects.requireNonNull(User.getUserByUsername(username)).getPassword().equals(password))
                UserInterface.printResponse(Responses.USER_PASS_NOT_MATCHED_ERROR);
            else {
                UserInterface.printResponse(Responses.LOGIN_SUCCESS);
                ProgramController.currentMenu = Menu.MAIN_MENU;
            }
        }
        else
            UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void logoutUser(Matcher matcher){

    }

    private boolean isExistUsername(String username){
        return  !Objects.isNull(User.getUserByUsername(username));
    }

    private boolean isExistNickname(String nickname){
        return  !Objects.isNull(User.getUserByNickName(nickname));
    }
}
