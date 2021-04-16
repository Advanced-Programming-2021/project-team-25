package models;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> users = new ArrayList<>();
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
        users.add(this);
    }

    public static User getUserByUsername(String username) {
        for(User user : users)
            if(user.getUsername().equals(username)) return user;

        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
