package models;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private String nickName;
    private int score;
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck activeDeck;
    private ArrayList<Card> cardsBought = new ArrayList<>();
    private int money;

    public User(String username , String password , String nickname){
        this.username = username;
        this.password = password;
        this.nickName = nickname;
    }
}
