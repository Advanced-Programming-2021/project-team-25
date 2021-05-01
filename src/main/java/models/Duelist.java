package models;

public class Duelist {

    private final User user;
    public Field field = new Field();
    public final int LP = 8000;
    public boolean hasPutMonster = false;

    public Duelist(User user) {
        this.user = user;
        this.field.deck.addAll(user.activeDeck.mainDeck);
    }

    public User getUser() {
        return user;
    }

    public String getName(){
        return user.getNickName();
    }
}
