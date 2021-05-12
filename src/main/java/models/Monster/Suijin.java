package models.Monster;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;

import java.io.Serializable;

public class Suijin extends Monster implements Serializable {
    private int counter = 0;

    public Suijin (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public Suijin (Object object){
        super(((Suijin)object).getName(), ((Suijin)object).getCardsType(),
                ((Suijin)object).getDescription(), ((Suijin)object).getPrice(),
                ((Suijin)object).getLevel(), ((Suijin)object).getAttribute(),
                ((Suijin)object).getMonsterType(), ((Suijin)object).getCardTypeInExel(),
                ((Suijin)object).getAttack(), ((Suijin)object).getDefence());
    }


    @Override
    public int defenceFunc(Battlefield battlefield) {
        if (counter == 0){
            counter += 1;
            int a = super.defenceFunc(battlefield);
            if(a == 1) battlefield.attackingMonster.removeMonster(battlefield);
            return a;
        }
        else{
            return super.defenceFunc(battlefield);
        }
    }


}
