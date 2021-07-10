package models.Monster;

import models.CardStufs.Type;

import java.io.Serializable;

public class GateGuardian extends Monster implements Serializable {

    public GateGuardian (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public GateGuardian (Object object){
        super(((GateGuardian)object).getName(), ((GateGuardian)object).getCardsType(),
                ((GateGuardian)object).getDescription(), ((GateGuardian)object).getPrice(),
                ((GateGuardian)object).getLevel(), ((GateGuardian)object).getAttribute(),
                ((GateGuardian)object).getMonsterType(), ((GateGuardian)object).getCardTypeInExel(),
                ((GateGuardian)object).getAttack(), ((GateGuardian)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
