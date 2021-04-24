package models.Monster;

import models.CardStufs.Type;

public class Suijin extends Monster {

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

//    @Override
//    public void action() {
//
//    }
}
