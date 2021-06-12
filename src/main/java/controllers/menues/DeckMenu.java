package controllers.menues;

import controllers.ShowCard;
import models.Card;
import models.Deck;
import models.User;
import java.util.Objects;

import static models.Deck.allDecks;

public class DeckMenu {
    private User currUser;
    private static DeckMenu singleToneClass = null;

    private DeckMenu(User currUser){
        this.currUser=currUser;
    }

    public static DeckMenu getInstance (User currUser){
        if (singleToneClass == null) singleToneClass = new DeckMenu(currUser);
        singleToneClass.currUser = currUser;
        return singleToneClass;
    }

    public String createDeck(String deckName){
        for (Deck deck : allDecks)
            if (deck.getDeckName().equals(deckName)) return "deck with name " + deckName + " already exists";

        new Deck(deckName , currUser.getUsername());
        return "deck created successfully!";
    }

    public String deleteDeck(String deckName){
        boolean exists = false;
        for (Deck deck:allDecks)
            if (deck.getDeckName().equals(deckName)) {
                exists = true;
                break;
            }

        if (exists) {
            allDecks.removeIf(deck -> deck.getDeckName().equals(deckName));
            return "deck deleted successfully!";
        }
        else return "invalid command";
    }

    public String setActive(String deckName){
        for (Deck deck:allDecks){
            if (deck.getDeckName().equals(deckName)){
                currUser.setActiveDeck(deck);
                return "deck activated successfully!";
            }
        }
        return "invalid command";
    }

    public String addCardToSide(String cardName, String deckName){
        if(Deck.getDeckByName(deckName) == null ) return "deck with name " + deckName + " does not exists";
        else if(numberOfCards(cardName,deckName)) return "card with name " + cardName + " does not exists";
        else if(Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck.size() == 15) return "side deck is full";
        else if(Deck.getNumberOfCardsInWholeDeck(deckName , cardName) == 3) return "there are already three cards with name " + cardName + " in deck " + deckName;
        else{
            Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck.add(ShowCard.Cards(cardName));
            return "card added to deck successfully";
        }
    }

    public String addCard(String cardName, String deckName){
        if(Deck.getDeckByName(deckName) == null ) return "deck with name " + deckName + " does not exists";
        else if(numberOfCards(cardName,deckName)) return "card with name " + cardName + " does not exists";
        else if(Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.size() == 60) return "main deck is full";
        else if(Deck.getNumberOfCardsInWholeDeck(deckName , cardName) == 3) return "there are already three cards with name " + cardName + " in deck " + deckName;
        else{
            Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.add(ShowCard.Cards(cardName));
            return "card added to deck successfully";
        }
    }

    public String removeCardFromSide(String cardName, String deckName){
        if(Deck.getDeckByName(deckName) == null ) return "deck with " + deckName + "does not exists";
        else if(Deck.getNumberOfCardsInSideDeck(deckName , cardName) == 0) return "card with name " + cardName + " does not exist in side deck";
        else{
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck)
                if(card.getName().equals(cardName)){
                    Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck.remove(card);
                    return "card removed from side deck successfully";
                }
        }
        return "invalid command";
    }

    public String removeCard(String cardName, String deckName){
        if(Deck.getDeckByName(deckName) == null ) return "deck with " + deckName + "does not exists";
        else if(Deck.getNumberOfCardsInMainDeck(deckName , cardName) == 0) return "card with name " + cardName + " does not exist in main deck";
        else{
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck)
                if(card.getName().equals(cardName)){
                    Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.remove(card);
                    return "card removed from main deck successfully";
                }
        }
        return "invalid command";
    }

    private boolean numberOfCards(String cardName ,String deckName){
        int i = 0;
        for (String name: currUser.cardsBought)
            if(name.equals(cardName)) i++;

        return i == Deck.getNumberOfCardsInWholeDeck(deckName, cardName);
    }
}