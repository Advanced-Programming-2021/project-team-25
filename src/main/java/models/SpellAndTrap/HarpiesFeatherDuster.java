package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;

import java.io.Serializable;
import java.util.Objects;

public class HarpiesFeatherDuster extends SpellAndTrap implements Serializable {
    private static Duelist opponent;

    public HarpiesFeatherDuster (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public HarpiesFeatherDuster (Object object){
        super(((HarpiesFeatherDuster)object).getName(), ((HarpiesFeatherDuster)object).getCardsType(),
                ((HarpiesFeatherDuster)object).getDescription(), ((HarpiesFeatherDuster)object).getPrice(),
                ((HarpiesFeatherDuster)object).getIcon(), ((HarpiesFeatherDuster)object).getStatus());
    }

    @Override
    public void action() {
        opponent = Battlefield.getOpponent();
        //checking not null
        if(!Objects.isNull(opponent)){
            for(Card card : opponent.field.spellTrapZone){
                opponent.field.graveYard.add(card);
                opponent.field.spellTrapZone.set(opponent.field.spellTrapZone.indexOf(card) , null);
            }
        }
    }
}
