package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Monster.Monster;

import java.io.Serializable;

public class MirrorForce extends SpellAndTrap implements Serializable {

    public MirrorForce (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MirrorForce (Object object){
        super(((MirrorForce)object).getName(), ((MirrorForce)object).getCardsType(),
                ((MirrorForce)object).getDescription(), ((MirrorForce)object).getPrice(),
                ((MirrorForce)object).getIcon(), ((MirrorForce)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        for (int i = 0; i < 5; i++) {
            if(battlefield.getOpponent().field.monsterZone.get(i) != null){
                if(battlefield.getOpponent().field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK){
                    battlefield.getOpponent().field.graveYard.add(battlefield.getOpponent().field.monsterZone.get(i));
                    battlefield.getOpponent().field.monsterZone.set(i,null);
                }
            }
        }
    }
}
