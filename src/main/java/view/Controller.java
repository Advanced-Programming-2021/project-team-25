package view;

import com.google.gson.Gson;
import controllers.Constants.Initialize;
import controllers.Database.DataBase;
import controllers.Regex;
import models.Deck;
import models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private static HashMap<String, User> loggedInUsers = new HashMap<>();

    private static Controller singleToneClass = null;

    public static Controller getInstance (){
        if (singleToneClass == null) singleToneClass = new Controller();
        return singleToneClass;

    }
    public User getUSerByToken(String token){
        for (Map.Entry<String, User> set : loggedInUsers.entrySet()) {
            if(set.getKey().equals(token))
                return set.getValue();
        }
        return null;
    }
    public String changeNickname(String command){
        Matcher matcher = Regex.getMatcher(command,Regex.changeNickname);
        if(matcher.find()){
            String newNickname = matcher.group("nickname");
            Matcher matcherToken = Regex.getMatcher(command,"--token (.+)");
            if(matcherToken.find()){
                User currUser = getUSerByToken(matcherToken.group(1));
                currUser.setNickName(newNickname);
                return UserInterface.printResponse("success","User nickname changed successfully");
            }
            else
                return UserInterface.printResponse("error","token not valid");

        }
        else  return UserInterface.printResponse("error",Responses.INVALID_COMMAND.getMessage());
    }

    public String changPass(String command){
        Matcher matcher = Regex.getMatcher(command,Regex.changePassword);
        if(matcher.find()){
            String currentPass = matcher.group("curr");
            String newPass = matcher.group("new");
            Matcher matcherToken = Regex.getMatcher(command,"--token (.+)");
            if(matcherToken.find()){
                User currUser = getUSerByToken(matcherToken.group(1));
                if(!currUser.getPassword().equals(currentPass))
                    return UserInterface.printResponse("error","current password is invalid");
                else if(currUser.getPassword().equals(newPass))
                    return UserInterface.printResponse("error","please enter a new password");
                else{
                    currUser.setPassword(newPass);
                    return UserInterface.printResponse("success","password changed successfully!");
                }
            }
            else
                return UserInterface.printResponse("error","token not valid");
        }
        else return UserInterface.printResponse("error",Responses.INVALID_COMMAND.getMessage());
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
                DataBase.saveTheUserList(User.getUsers());
                // by default user be logged in
                return "success description=\""+Responses.USER_CREATE_SUCCESS.getMessage()+"\"";
            }
        }
        else
            return "error description=\""+Responses.INVALID_COMMAND.getMessage()+"\"";
    }

    public synchronized String loginUser(String command){
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
                return "success description=\""+token+"\"";
            }
        }
        else
            return "error description=\""+Responses.INVALID_COMMAND.getMessage()+"\"";
    }


    public String initUserList (){
        //Initialize.initUserList();
        return "success description=\"successfully initiated users\"";
    }


    public String initDeckList (){
        //Initialize.initDeckList();
        return "success description=\"successfully initiated decks\"";
    }

    public boolean checkToken(String command) {
        Matcher mather = Regex.getMatcher(command,"--token (.+)");
        if(mather.find()){
            String token = mather.group(1);
            return loggedInUsers.containsKey(token);
        }
        else return false;
    }


    private static boolean isExistUsername(String username){
        return !Objects.isNull(User.getUserByUsername(username));
    }

    private static boolean isExistNickname(String nickname){
        return !Objects.isNull(User.getUserByNickName(nickname));
    }

    public Object getUserToJson(String command) {
        Matcher mather = Regex.getMatcher(command,"--token (.+)");
        if(mather.find()){
            String token = mather.group(1);
            return getUSerByToken(token);
        }
        else return UserInterface.printResponse("error","token not valid");
    }


    public String getDecksOfUser(String command) {
        Gson gson = new Gson();
        Matcher matcher = Regex.getMatcher(command, "--token (.+)");
        if (matcher.find()){
            String token = matcher.group(1);
            User user = getUSerByToken(token);
            String toReturn = "";
            for (int i = 0; i< Deck.allDecks.size(); ++i){
                if (Deck.allDecks.get(i).getOwnerName().equals(user.getUsername())){
                    toReturn += gson.toJson(Deck.allDecks.get(i));
                    toReturn += "&&&";
                }
            }
            return toReturn;
        }
        else return "error description=\"token not valid\"";
    }
}
