package controllers.menues;

import controllers.ProgramController;
import controllers.Regex;
import models.Deck;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

import static models.Deck.allDecks;

public class DeckMenu {
    public void runDeckMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.menuEnter)).matches()) directMenu(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckCreate)).matches()) createDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckDelete)).matches()) deleteDeck(matcher);
        //else if ((matcher = Regex.getMatcher(command, Regex.deckSetActive)).matches()) setActive(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckAddCard)).matches()) addCard(matcher);
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void directMenu(Matcher matcher){

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
        new Deck(deckName , new ProgramController().loggedUser.getUsername());
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

//    private void setActive(Matcher matcher){
//        String deckName = matcher.group(1);
//        for (Deck deck:allDecks){
//            if (deck.getDeckName().equals(deckName)){
//                new ProgramController().loggedUser.setActiveDeck(deck);
//                UserInterface.printResponse(Responses.DECK_ACTIVE_SUCCESS);
//                return;
//            }
//        }
//        UserInterface.printResponse(Responses.INVALID_COMMAND);
//    }

    private void addCard(Matcher matcher){
        String cardName = matcher.group(1);
        String deckName = matcher.group(2);


        //if(new ProgramController().loggedUser.cardsBuied.(DataBase.getCardByName(cardName)) ) System.out.println("ca");
        //else if(DataBase.getDeckByName(deckName) == null ) System.out.println("ss");
        //else if(DataBase.getDeckByName(deckName).mainDeck.size() == 60) System.out.println("ss");
    }

}