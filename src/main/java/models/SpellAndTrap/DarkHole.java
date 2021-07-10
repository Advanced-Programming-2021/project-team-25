package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import view.UserInterface;

import java.io.Serializable;
import java.util.Objects;

public class DarkHole extends SpellAndTrap implements Serializable {

    public DarkHole (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public DarkHole (Object object){
        super(((DarkHole)object).getName(), ((DarkHole)object).getCardsType(),
                ((DarkHole)object).getDescription(), ((DarkHole)object).getPrice(),
                ((DarkHole)object).getIcon(), ((DarkHole)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        Duelist turn = battlefield.getTurn();
        Duelist opponent = battlefield.getOpponent();
        //checking not null
        if(!Objects.isNull(turn) && !Objects.isNull(opponent)){
            //remove all monsters in rival zone
            for(Card card : opponent.field.monsterZone){
                opponent.field.graveYard.add(card);
                opponent.field.monsterZone.set(opponent.field.monsterZone.indexOf(card) , null);
            }
            //remove all monsters in our zone
            for(Card card : turn.field.monsterZone){
                turn.field.graveYard.add(card);
                turn.field.monsterZone.set(turn.field.monsterZone.indexOf(card) , null);
            }
        }
    }
}
