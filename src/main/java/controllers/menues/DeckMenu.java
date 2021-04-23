package controllers.menues;

import controllers.Database.DataBase;
import controllers.ProgramController;
import controllers.Regex;
import models.Card;
import models.Deck;
import models.User;
import view.Responses;
import view.UserInterface;

import java.util.Objects;
import java.util.regex.Matcher;

import static models.Deck.allDecks;

public class DeckMenu {
    private User currUser;
    public DeckMenu(User currUser){
        this.currUser=currUser;
    }
    public void runDeckMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.deckCreate)).matches()) createDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckDelete)).matches()) deleteDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckSetActive)).matches()) setActive(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckAddCard)).matches()) addCard(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckRemoveCard)).matches()) removeCard(matcher);
        else if (Regex.getMatcher(command, Regex.deckShowAll).matches()) deckShowAll();
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
        DataBase.storeData();
    }

    private void createDeck(Matcher matcher){
        String deckName = matcher.group(1);
        for (Deck deck:allDecks){
            if (deck.getDeckName().equals(deckName)){
                UserInterface.printResponse(Responses.INVALID_COMMAND);
                return;
            }
        }
        UserInterface.printResponse(Responses.DECK_CREATE_SUCCESS);
        new Deck(deckName , currUser.getUsername());
    }

    private void deleteDeck(Matcher matcher){
        boolean exists = false;
        for (Deck deck:allDecks)
            if (deck.getDeckName().equals(matcher.group(1))) {
                exists = true;
                break;
            }

        if (exists) {
            allDecks.removeIf(deck -> deck.getDeckName().equals(matcher.group(1)));
            UserInterface.printResponse(Responses.DECK_DELETE_SUCCESS);
        }
        else
            UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void setActive(Matcher matcher){
        String deckName = matcher.group(1);
        for (Deck deck:allDecks){
            if (deck.getDeckName().equals(deckName)){
                currUser.activeDeck = deck;
                UserInterface.printResponse(Responses.DECK_ACTIVE_SUCCESS);
                return;
            }
        }
        UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void addCard(Matcher matcher){
        String cardName = matcher.group(1) ,deckName = matcher.group(2);

        if(!currUser.cardsBought.contains(Card.allCards.get(cardName))) System.out.println("card with " + cardName + "does not exists");
        else if(Deck.getDeckByName(deckName) == null ) System.out.println("deck with "+ deckName + "does not exists");
        else if(Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.size() == 60) System.out.println("main deck is full");
        else if(Deck.getNumberOfCardsInDeck(deckName , cardName) == 3) System.out.println("there are already three cards whit name " + cardName + "in deck " + deckName);
        else{
            currUser.cardsBought.remove(Card.allCards.get(cardName));
            Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.add(Card.allCards.get(cardName));
            System.out.println("card added to deck successfully");
        }
    }

    private void removeCard(Matcher matcher){
        String cardName = matcher.group(1) ,deckName = matcher.group(2);

        if(Deck.getDeckByName(deckName) == null ) System.out.println("deck with "+ deckName + "does not exists");
        else if(Deck.getNumberOfCardsInDeck(deckName , cardName) == 0) System.out.println("card with name " + cardName + " does not exist in main deck");
        else{
            currUser.cardsBought.add(Card.allCards.get(cardName));
            Objects.requireNonNull(Deck.getDeckByName(deckName)).mainDeck.remove(Card.allCards.get(cardName));
            System.out.println("card removed from deck successfully");
        }
    }

    private void deckShowAll(){
        Deck activeDeck = currUser.activeDeck;
        System.out.println("Decks:");
        System.out.println("Active Deck:");
        if(activeDeck!= null)
            System.out.println(activeDeck.getDeckName() + ": " + activeDeck.mainDeck.size() + ", " + activeDeck.sideDeck.size() + ", " + "valid");
        System.out.println("Other Decks:");
        for (Deck deck: allDecks) {
            if(deck.getOwnerName().equals(currUser.getUsername()) && !deck.getDeckName().equals(activeDeck.getDeckName()))
                System.out.println(deck.getDeckName() + ": " + deck.mainDeck.size() + ", " + deck.sideDeck.size() + ", " + "valid");
        }
    }

}