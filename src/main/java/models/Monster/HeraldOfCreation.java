package models.Monster;

import models.CardStufs.Type;

import java.io.Serializable;

public class HeraldOfCreation extends Monster implements Serializable {

    public HeraldOfCreation (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public HeraldOfCreation (Object object){
        super(((HeraldOfCreation)object).getName(), ((HeraldOfCreation)object).getCardsType(),
                ((HeraldOfCreation)object).getDescription(), ((HeraldOfCreation)object).getPrice(),
                ((HeraldOfCreation)object).getLevel(), ((HeraldOfCreation)object).getAttribute(),
                ((HeraldOfCreation)object).getMonsterType(), ((HeraldOfCreation)object).getCardTypeInExel(),
                ((HeraldOfCreation)object).getAttack(), ((HeraldOfCreation)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
