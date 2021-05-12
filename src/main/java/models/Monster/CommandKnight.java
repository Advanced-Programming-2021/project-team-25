package models.Monster;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;

import java.io.Serializable;
import java.util.ArrayList;

public class CommandKnight extends Monster implements Serializable {
    public ArrayList<Card> targetedMonsters = new ArrayList<>();

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
//    public void removeMonster(Battlefield battlefield) {
//
//    }

//    @Override
//    public int defenceFunc(Battlefield battlefield) {
//
//    }
}
