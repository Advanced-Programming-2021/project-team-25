package models;

import controllers.Database.DataBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    private static ArrayList<User> users = new ArrayList<>();
    private final String username;
    private String password;
    private String nickName;
    private int score;
    private boolean isLoggedIn;
    public Deck activeDeck;
    public ArrayList<String> cardsBought = new ArrayList<>();
    public int money = 100000;

    public User(String username , String password , String nickname){
        this.username = username;
        this.password = password;
        this.nickName = nickname;
        this.isLoggedIn = true; //When a User created by default be logged in
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
            if(user.getNickName().equals(nickName)) return user;

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
                username + ", nickname: " +
                nickName + "}";
    }

    public int getScore() {
        return score;
    }

    public String getNickName() {
        return nickName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public void setCardsBought(ArrayList<String> cardsBought) {
        this.cardsBought = cardsBought;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<String> getCardsBought() {
        return cardsBought;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public int getMoney() {
        return money;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn= isLoggedIn;
    }


}
