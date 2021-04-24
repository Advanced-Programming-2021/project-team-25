package models.Monster;

import models.CardStufs.Type;

public class Texchanger extends Monster {

    public Texchanger (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public Texchanger (Object object){
        super(((Texchanger)object).getName(), ((Texchanger)object).getCardsType(),
                ((Texchanger)object).getDescription(), ((Texchanger)object).getPrice(),
                ((Texchanger)object).getLevel(), ((Texchanger)object).getAttribute(),
                ((Texchanger)object).getMonsterType(), ((Texchanger)object).getCardTypeInExel(),
                ((Texchanger)object).getAttack(), ((Texchanger)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
