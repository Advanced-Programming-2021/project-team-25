package models.Monster;

import models.CardStufs.Type;

public class ManEaterBug extends Monster {

    public ManEaterBug (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public ManEaterBug (Object object){
        super(((ManEaterBug)object).getName(), ((ManEaterBug)object).getCardsType(),
                ((ManEaterBug)object).getDescription(), ((ManEaterBug)object).getPrice(),
                ((ManEaterBug)object).getLevel(), ((ManEaterBug)object).getAttribute(),
                ((ManEaterBug)object).getMonsterType(), ((ManEaterBug)object).getCardTypeInExel(),
                ((ManEaterBug)object).getAttack(), ((ManEaterBug)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
