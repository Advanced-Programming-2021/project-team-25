package controllers.menues;

import controllers.DataBase;
import controllers.ProgramController;
import controllers.Regex;
import models.Deck;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class DeckMenu {
    public void runDeckMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.menuEnter)).matches()) directMenu(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckCreate)).matches()) createDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckDelete)).matches()) deleteDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckSetActive)).matches()) setActive(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckAddCard)).matches()) addCard(matcher);
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
    }

    private void directMenu(Matcher matcher){

    }

    private void createDeck(Matcher matcher){
        String deckName = matcher.group(1);
        if(DataBase.getDeckByName(deckName) == null) UserInterface.printResponse(Responses.INVALID_COMMAND);
        else{
            Deck deck = new Deck(deckName);
            DataBase.addDeck(deck);
        }
    }

    private void deleteDeck(Matcher matcher){
        String deckName = matcher.group(1);
        if(DataBase.getDeckByName(deckName) == null) UserInterface.printResponse(Responses.INVALID_COMMAND);
        else DataBase.deleteDeck(deckName);
    }

    private void setActive(Matcher matcher){

    }

    private void addCard(Matcher matcher){
        String cardName = matcher.group(1);
        String deckName = matcher.group(2);


        //if(new ProgramController().loggedUser.cardsBuied.(DataBase.getCardByName(cardName)) ) System.out.println("ca");
        //else if(DataBase.getDeckByName(deckName) == null ) System.out.println("ss");
        //else if(DataBase.getDeckByName(deckName).mainDeck.size() == 60) System.out.println("ss");
    }
}
