package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Deck implements Serializable {

    public static ArrayList<Deck> allDecks = new ArrayList<>();
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

    public static Deck getDeckByName(String name){
        for (Deck deck: allDecks)
            if (deck.getDeckName().equals(name)) return deck;
        return null;
    }

    public static int getNumberOfCardsInWholeDeck(String deckName , String cardName){
        int i = 0;
        for (Card card: Objects.requireNonNull(getDeckByName(deckName)).mainDeck)
            if (card.getName().equals(cardName)) i++;
        for (Card card: Objects.requireNonNull(getDeckByName(deckName)).sideDeck)
            if (card.getName().equals(cardName)) i++;
        return i;
    }

    public static int getNumberOfCardsInMainDeck(String deckName , String cardName){
        int i = 0;
        for (Card card: Objects.requireNonNull(getDeckByName(deckName)).mainDeck)
            if (card.getName().equals(cardName)) i++;

        return i;
    }

    public static int getNumberOfCardsInSideDeck(String deckName , String cardName){
        int i = 0;
        for (Card card: Objects.requireNonNull(getDeckByName(deckName)).sideDeck)
            if (card.getName().equals(cardName)) i++;

        return i;
    }

    public static boolean isValid(String deckName){
        return (
                Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.size() <= 60 &&
                Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.size() >= 40 &&
                Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck.size() <= 15
        );
    }
}
