package view;

import controllers.Menu;
import controllers.ProgramController;
import controllers.Regex;
import controllers.menues.MainMenu;
import models.User;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static HashMap<String, User> loggedInUsers;
    private static User currUser = null;

    private static Controller singleToneClass = null;

    public static Controller getInstance (){
        if (singleToneClass == null) singleToneClass = new Controller();
        return singleToneClass;

    }
    public String createNewUser(String command){
        Matcher matcher = Regex.getMatcher(command, Regex.userCreate);
        if(matcher.find()) {
            String username = matcher.group("username");
            String password = matcher.group("password");
            String nickname = matcher.group("nickname");

            if(isExistUsername(username))
                return UserInterface.printResponse("error","user with username " + username + " already exist!");
            else if(isExistNickname(nickname))
                return UserInterface.printResponse("error","user with nickname " + nickname + " already exist!");
            else
            {
                //creating new user
                new User(username,password,nickname);
                // by default user be logged in
                return "success description=\""+Responses.USER_CREATE_SUCCESS.getMessage()+"\"";
            }
        }
        else
            return "error description=\""+Responses.INVALID_COMMAND.getMessage()+"\"";
    }

    public String loginUser(String command){
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
                return "error description=\""+Responses.USER_PASS_NOT_MATCHED_ERROR.getMessage()+"\"";
            else {
                //set the user logged in
                user.setIsLoggedIn(true);
                String token = UUID.randomUUID().toString();
                loggedInUsers.put(token, user);
                //update curr user
                currUser = user;
                return "success description=\""+token+"\"";
            }
        }
        else
                return "error description=\""+Responses.INVALID_COMMAND.getMessage()+"\"";
    }

    private static boolean isExistUsername(String username){
        return !Objects.isNull(User.getUserByUsername(username));
    }

    private static boolean isExistNickname(String nickname){
        return !Objects.isNull(User.getUserByNickName(nickname));
    }
}
