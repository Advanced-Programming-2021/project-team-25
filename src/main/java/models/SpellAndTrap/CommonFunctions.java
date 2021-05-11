package models.SpellAndTrap;

import controllers.ShowCard;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import view.UserInterface;

public interface CommonFunctions {

    default boolean showGraveYard(Duelist duelist) {
        boolean isFoundMonster = false;
        for (Card card : duelist.field.graveYard) {
            if(card.getCardsType().equals(Type.MONSTER)){
                ShowCard.showCard(card.getName());
                isFoundMonster = true;
            }
        }
        if(!isFoundMonster) UserInterface.printResponse("No Monster Found in "+duelist.getName()+" graveyard");
        return isFoundMonster;
    }
}
