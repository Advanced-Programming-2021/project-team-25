package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;
import java.util.Objects;

public class Raigeki extends SpellAndTrap implements Serializable {

    public Raigeki (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Raigeki (Object object){
        super(((Raigeki)object).getName(), ((Raigeki)object).getCardsType(),
                ((Raigeki)object).getDescription(), ((Raigeki)object).getPrice(),
                ((Raigeki)object).getIcon(), ((Raigeki)object).getStatus());
    }

    @Override
    public void action() {
        Duelist opponent = Battlefield.getOpponent();
        //checking not null
        if(!Objects.isNull(opponent)){
            for(Card card : opponent.field.monsterZone){
                opponent.field.graveYard.add(card);
                opponent.field.monsterZone.set(opponent.field.monsterZone.indexOf(card) , null);
            }
        }
    }
}


