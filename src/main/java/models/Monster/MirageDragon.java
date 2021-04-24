package models.Monster;

import models.CardStufs.Type;

public class MirageDragon extends Monster {

    public MirageDragon (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public MirageDragon (Object object){
        super(((MirageDragon)object).getName(), ((MirageDragon)object).getCardsType(),
                ((MirageDragon)object).getDescription(), ((MirageDragon)object).getPrice(),
                ((MirageDragon)object).getLevel(), ((MirageDragon)object).getAttribute(),
                ((MirageDragon)object).getMonsterType(), ((MirageDragon)object).getCardTypeInExel(),
                ((MirageDragon)object).getAttack(), ((MirageDragon)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
