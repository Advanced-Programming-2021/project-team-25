package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;

import java.io.Serializable;

public class TorrentialTribute extends SpellAndTrap implements Serializable {

    public TorrentialTribute (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public TorrentialTribute (Object object){
        super(((TorrentialTribute)object).getName(), ((TorrentialTribute)object).getCardsType(),
                ((TorrentialTribute)object).getDescription(), ((TorrentialTribute)object).getPrice(),
                ((TorrentialTribute)object).getIcon(), ((TorrentialTribute)object).getStatus());
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
