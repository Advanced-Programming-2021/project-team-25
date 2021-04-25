package models.Monster;

import models.CardStufs.Type;

import java.io.Serializable;

public class CommandKnight extends Monster implements Serializable {

    public CommandKnight (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public CommandKnight (Object object){
        super(((CommandKnight)object).getName(), ((CommandKnight)object).getCardsType(),
                ((CommandKnight)object).getDescription(), ((CommandKnight)object).getPrice(),
                ((CommandKnight)object).getLevel(), ((CommandKnight)object).getAttribute(),
                ((CommandKnight)object).getMonsterType(), ((CommandKnight)object).getCardTypeInExel(),
                ((CommandKnight)object).getAttack(), ((CommandKnight)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
