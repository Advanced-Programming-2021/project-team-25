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
    private static Duelist opponent;

    public PotOfGreed (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public PotOfGreed (Object object){
        super(((PotOfGreed)object).getName(), ((PotOfGreed)object).getCardsType(),
                ((PotOfGreed)object).getDescription(), ((PotOfGreed)object).getPrice(),
                ((PotOfGreed)object).getIcon(), ((PotOfGreed)object).getStatus());
    }

    @Override
    public void action() {
        turn = Battlefield.getTurn();
        opponent = Battlefield.getOpponent();
        //checking not null
        if(!Objects.isNull(turn) && !Objects.isNull(opponent)){
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
    private int getSizeOfHand(){
        int count = 0;
        for(int i=0;i<6;i++){
            if(turn.field.hand.get(i) != null) count++;
        }
        return count;
    }
}
