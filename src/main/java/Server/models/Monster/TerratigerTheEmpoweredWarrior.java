package models.Monster;

import models.CardStufs.Type;

import java.io.Serializable;

public class TerratigerTheEmpoweredWarrior extends Monster implements Serializable {

    public TerratigerTheEmpoweredWarrior (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public TerratigerTheEmpoweredWarrior (Object object){
        super(((TerratigerTheEmpoweredWarrior)object).getName(), ((TerratigerTheEmpoweredWarrior)object).getCardsType(),
                ((TerratigerTheEmpoweredWarrior)object).getDescription(), ((TerratigerTheEmpoweredWarrior)object).getPrice(),
                ((TerratigerTheEmpoweredWarrior)object).getLevel(), ((TerratigerTheEmpoweredWarrior)object).getAttribute(),
                ((TerratigerTheEmpoweredWarrior)object).getMonsterType(), ((TerratigerTheEmpoweredWarrior)object).getCardTypeInExel(),
                ((TerratigerTheEmpoweredWarrior)object).getAttack(), ((TerratigerTheEmpoweredWarrior)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
