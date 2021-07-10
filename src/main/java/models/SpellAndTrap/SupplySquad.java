package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import view.Responses;
import view.UserInterface;

import java.io.Serializable;

public class SupplySquad extends SpellAndTrap implements Serializable {
    public boolean isFirsTimeCalled = true;

    public SupplySquad (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SupplySquad (Object object){
        super(((SupplySquad)object).getName(), ((SupplySquad)object).getCardsType(),
                ((SupplySquad)object).getDescription(), ((SupplySquad)object).getPrice(),
                ((SupplySquad)object).getIcon(), ((SupplySquad)object).getStatus());
    }

    public void action(Battlefield battlefield){
        Duelist turn = battlefield.getTurn();

        //if the size of turn`s graveYard changed
        //we know that at least one card dead
        int firstTime = 0, secondTime = 0;

        if(isFirsTimeCalled){
            firstTime = turn.field.graveYard.size();
            isFirsTimeCalled = false;
        }
        else {
            secondTime = turn.field.graveYard.size();
            //if the cards died are monsters
            //then added one card from deck to monster zone
            addCardFromDeckToHand(turn, firstTime, secondTime);
        }

    }

    private void addCardFromDeckToHand(Duelist turn, int firstTime, int secondTime) {
        for(int i = firstTime; i<= secondTime; i++){
            //get card
            Card card = turn.field.graveYard.get(i);
            //check the type
            if(card.getCardsType().equals(Type.MONSTER)){
                //if we have space added card
                if(turn.field.hand.size()<6){
                    turn.field.hand.add(turn.field.deck.get(0));
                    turn.field.deck.remove(0);
                }
                else
                    UserInterface.printResponse(Responses.NOT_ENOUGH_SPACE_IN_HAND);
            }
        }
    }

}
