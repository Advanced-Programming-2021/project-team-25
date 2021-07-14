package models;

import java.io.Serializable;

public class Duelist implements Serializable {

    private final User user;
    public Field field = new Field();
    public int LP = 8000;
    public boolean hasPutMonster = false;

    public Duelist(User user) {
        this.user = user;
        if(user.activeDeck != null)
            this.field.deck.addAll(user.activeDeck.mainDeck);
    }

    public User getUser() {
        return user;
    }

    public String getName(){
        return user.getNickName();
    }
}
