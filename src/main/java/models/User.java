package models;

import controllers.Database.DataBase;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


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

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickName = nickname;
        this.isLoggedIn = true; //When a User created by default be logged in
        getImageRandom();
        users.add(this);
        DataBase.saveTheUserList(users);
    }

    private void getImageRandom() {
        Random random = new Random();
        int number = random.nextInt(29);
        number += 1;
        String from = Objects.requireNonNull(this.getClass().getResource("/models/UserImages/"+number + ".png")).getPath();
        String to = Objects.requireNonNull(this.getClass().getResource("/models/Images/1.png")).getPath();
        try{
            BufferedImage bi = ImageIO.read(new File(from));
            ImageIO.write(bi, "png", new File(this.getUsername()+".png"));
            //Files.copy(Path.of(from), Path.of(to.replace("1.png", "asqar.png")));
            System.out.println("Copied!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public User(String username , String nickName , int score){
        this.username = username;
        this.nickName = nickName;
        this.score = score;
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
