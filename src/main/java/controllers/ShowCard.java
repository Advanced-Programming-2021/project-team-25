package controllers;

import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import view.UserInterface;

public class ShowCard {

    public static void showCard(String cardsName){
        if (!Card.allCards.containsKey(cardsName)) UserInterface.printResponse("there is no card with this name");
        else{
            if (Card.allCards.get(cardsName).getCardsType() == Type.MONSTER){
                Monster monster = (Monster) Card.allCards.get(cardsName);
                UserInterface.printResponse("Name: " + cardsName);
                UserInterface.printResponse("Level: " + monster.getLevel());
                UserInterface.printResponse("Type: " + monster.getCardTypeInExel());
                UserInterface.printResponse("ATK: " + monster.getAttack());
                UserInterface.printResponse("DEF" + monster.getDefence());
                UserInterface.printResponse("Description" + monster.getDescription());
            }
            else{
                SpellAndTrap spellAndTrap = (SpellAndTrap) Card.allCards.get(cardsName);
                UserInterface.printResponse("Name: " + cardsName);
                if (spellAndTrap.getCardsType() == Type.SPELL) UserInterface.printResponse("Spell");
                else UserInterface.printResponse("Trap");
                UserInterface.printResponse("Type: " + spellAndTrap.getIcon());
                UserInterface.printResponse("Description: " + spellAndTrap.getDescription());
            }
        }
    }

}
