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
        expireTime = 1;
    }

    public Raigeki (Object object){
        super(((Raigeki)object).getName(), ((Raigeki)object).getCardsType(),
                ((Raigeki)object).getDescription(), ((Raigeki)object).getPrice(),
                ((Raigeki)object).getIcon(), ((Raigeki)object).getStatus());
        expireTime = 1;
    }

    @Override
    public void action(Battlefield battlefield) {
        if(expireTime == 0){
            expireTime = 1;
            removeSpellOrTrap(battlefield);
        }
        else{
            expireTime-- ;
            Duelist opponent = battlefield.getOpponent();
            //checking not null
            if(!Objects.isNull(opponent)){
                for (int i = 0; i<5; ++i){
                    if (opponent.field.monsterZone.get(i) != null)
                        ((Monster)opponent.field.monsterZone.get(i)).removeMonster(battlefield);
                }
            }
        }
    }
}


