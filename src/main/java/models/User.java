package models;

import controllers.Database.DataBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    private static ArrayList<User> users = new ArrayList<>();
    private final String username;
    private final String password;
    private final String nickName;
    private int score;
    private Deck activeDeck;
    private ArrayList<Card> cardsBought = new ArrayList<>();
    private int money;

    public User(String username , String password , String nickname){
        this.username = username;
        this.password = password;
        this.nickName = nickname;
        users.add(this);
        DataBase.saveTheUserList(users);
    }

    public static User getUserByUsername(String username) {
        for(User user : users)
            if(user.getUsername().equals(username)) return user;

        return null;
    }

    public static User getUserByNickName(String nickName) {
        for(User user : users)
            if(user.getUsername().equals(nickName)) return user;

        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        if(!Objects.isNull(users))User.users = users;
    }

    @Override
    public String toString() {
        return "User{" + "username: " +
                username + ", password: " +
                password + ", nickname: " +
                nickName + "}";
    }
}
