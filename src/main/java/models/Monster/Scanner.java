package models.Monster;

import models.CardStufs.Type;

public class Scanner extends Monster {

    public Scanner (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public Scanner (Object object){
        super(((Scanner)object).getName(), ((Scanner)object).getCardsType(),
                ((Scanner)object).getDescription(), ((Scanner)object).getPrice(),
                ((Scanner)object).getLevel(), ((Scanner)object).getAttribute(),
                ((Scanner)object).getMonsterType(), ((Scanner)object).getCardTypeInExel(),
                ((Scanner)object).getAttack(), ((Scanner)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
