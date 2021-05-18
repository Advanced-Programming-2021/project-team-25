package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class PotOfGreed extends SpellAndTrap implements Serializable {
    private static Duelist turn;

    public PotOfGreed (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
        expireTime = 1;
    }

    public PotOfGreed (Object object){
        super(((PotOfGreed)object).getName(), ((PotOfGreed)object).getCardsType(),
                ((PotOfGreed)object).getDescription(), ((PotOfGreed)object).getPrice(),
                ((PotOfGreed)object).getIcon(), ((PotOfGreed)object).getStatus());
        expireTime = 1;
    }

    @Override
    public void action(Battlefield battlefield) {
        if(expireTime == 0){
            expireTime = 1;
            removeSpellOrTrap(battlefield);
        }
        else {
            expireTime--;
            turn = battlefield.getTurn();
            //checking not null
            if(!Objects.isNull(turn)){
                if(getSizeOfHand()<=4){
                    //add two card
                    turn.field.hand.add(turn.field.deck.get(0));
                    turn.field.deck.remove(0);
                    turn.field.hand.add(turn.field.deck.get(0));
                    turn.field.deck.remove(0);
                }
                else
                    UserInterface.printResponse("you dont have enough space in your hand");
            }
        }
    }
    private int getSizeOfHand(){
        int count = 0;
        for(int i=0;i<6;i++){
            if(turn.field.hand.get(i) != null) count++;
        }
        return count;
    }
}
