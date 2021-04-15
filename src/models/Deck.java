package models;

import models.Card;

import java.util.ArrayList;

public class Deck {

    static int idCounter = 1;
    private final String deckName;
    private final int id;
    private ArrayList<Card> mainDeck = new ArrayList<>();
    private ArrayList<Card> sideDeck = new ArrayList<>();

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
}
