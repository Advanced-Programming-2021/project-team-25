package controllers.menues;

import controllers.DataBase;
import controllers.Regex;
import models.Deck;
import view.Responses;
import view.UserInterface;

import java.util.regex.Matcher;

public class DeckMenu {
    public void runDeckMenu(String command){
        Matcher matcher;

        if ((matcher = Regex.getMatcher(command, Regex.deckCreate)).matches()) createDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckDelete)).matches()) deleteDeck(matcher);
        else if ((matcher = Regex.getMatcher(command, Regex.deckSetActive)).matches()) setActive(matcher);
        //else if ((matcher = Regex.getMatcher(command, Regex.userLogout)).matches()) logoutUser(matcher);
        else UserInterface.printResponse(Responses.INVALID_COMMAND);
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

    }

    private void setActive(Matcher matcher){

    }

}
