package models;

import java.util.ArrayList;
import models.Cards.Card;

public class Deck {

    public static ArrayList<Deck> allDecks = new ArrayList<>();
    private final String deckName;
    private final String ownerName;

    private ArrayList<Card> mainDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();

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
