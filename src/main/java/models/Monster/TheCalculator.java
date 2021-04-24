package models.Monster;

import models.CardStufs.Type;

public class TheCalculator extends Monster {

    public TheCalculator (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public TheCalculator (Object object){
        super(((TheCalculator)object).getName(), ((TheCalculator)object).getCardsType(),
                ((TheCalculator)object).getDescription(), ((TheCalculator)object).getPrice(),
                ((TheCalculator)object).getLevel(), ((TheCalculator)object).getAttribute(),
                ((TheCalculator)object).getMonsterType(), ((TheCalculator)object).getCardTypeInExel(),
                ((TheCalculator)object).getAttack(), ((TheCalculator)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
