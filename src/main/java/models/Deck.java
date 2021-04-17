package models;

import java.util.ArrayList;

public class Deck {

    static int idCounter = 1;
    private final String deckName;
    private final int id;
    public ArrayList<Card> mainDeck = new ArrayList<>();
    public ArrayList<Card> sideDeck = new ArrayList<>();

    public Deck(String deckName){
        this.id=idCounter;
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }

    public int getId() {
        return id;
    }

    public static int getIdCounter() {
        return idCounter;
    }

}
