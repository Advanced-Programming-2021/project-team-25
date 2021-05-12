package models.Monster;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.User;
import view.UserInterface;

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


    @Override
    public void removeMonster(Battlefield battlefield) {
        for (Card targetedMonster : targetedMonsters) {
            Monster temp = (Monster) targetedMonster;
            temp.setAttack(temp.getAttack() - 400);
        }
        super.removeMonster(battlefield);
    }

    @Override
    public int defenceFunc(Battlefield battlefield) {
//        return super.defenceFunc(battlefield);
        int counter = 0;
        for (int i = 0; i<5; ++i){
            if (battlefield.getOpponent().field.monsterZone.get(i) != null &&
            battlefield.getOpponent().field.monsterZone.get(i) != this){
                counter += 1;
            }
        }

        if (counter == 0)
            return super.defenceFunc(battlefield);
        else{
            UserInterface.printResponse("There are more monsters on your opponents monster zone" +
                    "so you can't attack this monster.");
            return 10;
        }


    }


}
