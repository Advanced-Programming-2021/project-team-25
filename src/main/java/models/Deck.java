package models;

import java.util.ArrayList;

public class Deck {

    static public ArrayList<Deck> allDecks = new ArrayList<>();
    private final String deckName;
    private final String ownerName;
    public ArrayList<Card> mainDeck = new ArrayList<>();
    public ArrayList<Card> sideDeck = new ArrayList<>();

    public Deck(String deckName , String ownerName){
        this.deckName = deckName;
        this.ownerName = ownerName;
        allDecks.add(this);
    }

    public String getDeckName() {
        return deckName;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
