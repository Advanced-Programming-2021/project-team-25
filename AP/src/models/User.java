package models;

import java.util.ArrayList;

public class User {

    private final String username;
    private final String password;
    private final String nickName;
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
