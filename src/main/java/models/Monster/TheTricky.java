package models.Monster;

import models.CardStufs.Type;

import java.io.Serializable;

public class TheTricky extends Monster implements Serializable {

    public TheTricky (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public TheTricky (Object object){
        super(((TheTricky)object).getName(), ((TheTricky)object).getCardsType(),
                ((TheTricky)object).getDescription(), ((TheTricky)object).getPrice(),
                ((TheTricky)object).getLevel(), ((TheTricky)object).getAttribute(),
                ((TheTricky)object).getMonsterType(), ((TheTricky)object).getCardTypeInExel(),
                ((TheTricky)object).getAttack(), ((TheTricky)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
