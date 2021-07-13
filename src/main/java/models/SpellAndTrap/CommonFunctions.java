package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import controllers.ShowCard;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import models.Field;
import models.Monster.Monster;
import view.UserInterface;

import java.util.ArrayList;

public interface CommonFunctions {

    default boolean showGraveYard(Duelist duelist) {
        boolean isFoundMonster = false;
        for (Card card : duelist.field.graveYard) {
            if(card.getCardsType().equals(Type.MONSTER)){
                UserInterface.printResponse(card.getName());
                isFoundMonster = true;
            }
        }
        if(!isFoundMonster) UserInterface.printResponse("No Monster Found in "+duelist.getName()+" graveyard");
        return isFoundMonster;
    }
    default boolean showHand(Duelist duelist) {
        boolean isFoundMonster = false;
        for (Card card : duelist.field.hand) {
            if(card.getCardsType().equals(Type.MONSTER)){
                ShowCard.showCard(card.getName());
                isFoundMonster = true;
            }
        }
        if(!isFoundMonster) UserInterface.printResponse("No Monster Found in "+duelist.getName()+" hand");
        return isFoundMonster;
    }
    default boolean showDeck(Duelist duelist) {
        boolean isFoundMonster = false;
        for (Card card : duelist.field.deck) {
            if(card.getCardsType().equals(Type.MONSTER)){
                ShowCard.showCard(card.getName());
                isFoundMonster = true;
            }
        }
        if(!isFoundMonster) UserInterface.printResponse("No Monster Found in "+duelist.getName()+" Deck");
        return isFoundMonster;
    }
}
