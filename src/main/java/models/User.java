package models;

import java.util.ArrayList;

public class User {
    private final String username;
    private final String password;
    private final String nickName;
    private int score;
    private Deck activeDeck = null;

    private int money;
    public ArrayList<Card> cardsBought = new ArrayList<>();

    public User(String username , String password , String nickname){
        this.username = username;
        this.password = password;
        this.nickName = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public Deck getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }
}
