package models.Monster;

import models.CardStufs.Type;

import java.io.Serializable;

public class ExploderDragon extends Monster implements Serializable {

    public ExploderDragon (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public ExploderDragon (Object object){
        super(((ExploderDragon)object).getName(), ((ExploderDragon)object).getCardsType(),
                ((ExploderDragon)object).getDescription(), ((ExploderDragon)object).getPrice(),
                ((ExploderDragon)object).getLevel(), ((ExploderDragon)object).getAttribute(),
                ((ExploderDragon)object).getMonsterType(), ((ExploderDragon)object).getCardTypeInExel(),
                ((ExploderDragon)object).getAttack(), ((ExploderDragon)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
