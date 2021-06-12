package controllers.menues;

import controllers.ShowCard;
import models.Card;
import models.CardStufs.Type;
import models.Deck;
import models.User;
import view.UserInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;

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

    private void deckShowAll(){
        String activeDeckName = "";
        Deck activeDeck = currUser.activeDeck;

        UserInterface.printResponse("Decks:\nActive Deck:");
        if(activeDeck != null){
            if(Deck.isValid(activeDeck.getDeckName()))
                UserInterface.printResponse(activeDeck.getDeckName() + ": " + activeDeck.mainDeck.size() + ", " + activeDeck.sideDeck.size() + ", " + "valid");
            else
                UserInterface.printResponse(activeDeck.getDeckName() + ": " + activeDeck.mainDeck.size() + ", " + activeDeck.sideDeck.size() + ", " + "invalid");
            activeDeckName = activeDeck.getDeckName();
        }
        UserInterface.printResponse("Other Decks:");
        ArrayList<Deck> decks = new ArrayList<>();
        for (Deck deck: allDecks)
            if(deck.getOwnerName().equals(currUser.getUsername()) && !deck.getDeckName().equals(activeDeckName))
                decks.add(deck);

        Comparator<Deck> orderedDecks = Comparator.comparing(Deck::getDeckName);

        decks.sort(orderedDecks);
        for (Deck deck: decks)
            if(Deck.isValid(deck.getDeckName()))
                UserInterface.printResponse(deck.getDeckName() + ": " + deck.mainDeck.size() + ", " + deck.sideDeck.size() + ", " + "valid");
            else
                UserInterface.printResponse(deck.getDeckName() + ": " + deck.mainDeck.size() + ", " + deck.sideDeck.size() + ", " + "invalid");
    }

    private void showSideDeck(Matcher matcher){
        String deckName = matcher.group(2);
        if(Deck.getDeckByName(deckName)==null)
            UserInterface.printResponse("deck with name" + deckName + "does not exist");
        else {
            UserInterface.printResponse("Deck: " + deckName + "\nSide deck:\nMonsters:");

            ArrayList<Card> monsters = new ArrayList<>();
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck)
                if(card.getCardsType() == Type.MONSTER) monsters.add(card);

            Comparator<Card> orderedCards = Comparator.comparing(Card::getName);

            monsters.sort(orderedCards);
            for (Card card : monsters)
                UserInterface.printResponse(card.getName() + " : " + card.getDescription());

            UserInterface.printResponse("Spell and Traps:");
            ArrayList<Card> spellAndTraps = new ArrayList<>();
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).sideDeck)
                if(card.getCardsType() != Type.MONSTER) spellAndTraps.add(card);

            spellAndTraps.sort(orderedCards);
            for (Card card : spellAndTraps)
                UserInterface.printResponse(card.getName() + " : " + card.getDescription());
        }
    }

    private void showDeck(Matcher matcher){
        String deckName = matcher.group(2);
        if(Deck.getDeckByName(deckName)==null)
            UserInterface.printResponse("deck with name " + deckName + " does not exist");
        else {
            UserInterface.printResponse("Deck: " + deckName + "view.Main deck:\nMonsters:");

            ArrayList<Card> monsters = new ArrayList<>();
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck)
                if(card.getCardsType() == Type.MONSTER) monsters.add(card);

            Comparator<Card> orderedCards = Comparator.comparing(Card::getName);

            monsters.sort(orderedCards);
            for (Card card : monsters)
                UserInterface.printResponse(card.getName() + " : " + card.getDescription());

            UserInterface.printResponse("Spell and Traps:");
            ArrayList<Card> spellAndTraps = new ArrayList<>();
            for (Card card: Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck)
                if(card.getCardsType() != Type.MONSTER) spellAndTraps.add(card);

            spellAndTraps.sort(orderedCards);
            for (Card card : spellAndTraps)
                UserInterface.printResponse(card.getName() + " : " + card.getDescription());
        }
    }

    private void deckShowCards(){
        Collections.sort(currUser.cardsBought);
        for (String cardName : currUser.cardsBought)
            UserInterface.printResponse(cardName + " : " + Card.allCards.get(cardName).getDescription());
    }
}