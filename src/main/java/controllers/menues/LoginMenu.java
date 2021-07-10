package controllers.menues;

import controllers.Database.DataBase;
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

    private static User currUser = null;

    public static void runLoginMenu(){
        while (true) {
            String command = UserInterface.getUserInput();
            if (command.startsWith("user login")) loginUser(command,true);
            else if (command.startsWith("user create")) createNewUser(Regex.getMatcher(command, Regex.userCreate),true);
            else if (command.startsWith("menu enter")) UserInterface.printResponse(Responses.LOGIN_FIRST_ERROR);
            else if (command.equals("menu show-current")) UserInterface.printResponse("Login Menu");
            else if (command.equals("menu exit")) return;
            else if (!Objects.isNull(currUser)) {

            }
            else UserInterface.printResponse(Responses.INVALID_COMMAND);
        }
    }

    public static void createNewUser(Matcher matcher, boolean canGoOn){
        if(matcher.find()) {
            String username = matcher.group("username");
            String password = matcher.group("password");
            String nickname = matcher.group("nickname");

            if(isExistUsername(username))
                UserInterface.printResponse("user with username " + username + " already exist!");
            else if(isExistNickname(nickname))
                UserInterface.printResponse("user with nickname " + nickname + " already exist!");
            else
            {
                UserInterface.printResponse(Responses.USER_CREATE_SUCCESS);
                //creating new user
                User newUser = new User(username,password,nickname);
                // by default user be logged in
                ProgramController.setLoggedInUsers(newUser);
                //update curr user
                currUser = newUser;
                //change menu to main menu
                if(canGoOn) new MainMenu(newUser);
            }
        }
        else
            UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    public static void loginUser(String command, boolean canGoOn){
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
            //checking user exist
            User user = User.getUserByUsername(username);
            //checking correct password
            if(Objects.isNull(user) || !user.getPassword().equals(password))
                UserInterface.printResponse(Responses.USER_PASS_NOT_MATCHED_ERROR);
            else {
                UserInterface.printResponse(Responses.LOGIN_SUCCESS);
                //set the user logged in
                user.setIsLoggedIn(true);
                ProgramController.currentMenu = Menu.MAIN_MENU;
                //update curr user
                currUser = user;
                //change menu to main menu
                if(canGoOn) new MainMenu(user);
            }
        }
        else
            UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private static boolean isExistUsername(String username){
        return !Objects.isNull(User.getUserByUsername(username));
    }

    private static boolean isExistNickname(String nickname){
        return !Objects.isNull(User.getUserByNickName(nickname));
    }

}
