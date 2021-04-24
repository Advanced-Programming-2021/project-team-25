package models.Monster;

import models.CardStufs.Type;

public class YomiShip extends Monster {

    public YomiShip (String name, Type cardType, String description, int price, int level, String attribute,
                      String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public YomiShip (Object object){
        super(((YomiShip)object).getName(), ((YomiShip)object).getCardsType(),
                ((YomiShip)object).getDescription(), ((YomiShip)object).getPrice(),
                ((YomiShip)object).getLevel(), ((YomiShip)object).getAttribute(),
                ((YomiShip)object).getMonsterType(), ((YomiShip)object).getCardTypeInExel(),
                ((YomiShip)object).getAttack(), ((YomiShip)object).getDefence());
    }

//    @Override
//    public void action() {
//
//    }
}
