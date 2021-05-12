package models.Monster;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;

import java.io.Serializable;

public class TheCalculator extends Monster implements Serializable {
    Duelist turn;
    Battlefield battlefield;
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

    @Override
    public void action(Battlefield battlefield) {

        this.battlefield = battlefield;
        turn = battlefield.getTurn();
        //calculate all attacks and cross them in 300
        int sumOfAttack = 0;

        for(Card card : turn.field.monsterZone){
            sumOfAttack+=((Monster)card).getAttack();
        }
        sumOfAttack*=300;
        this.setAttack(sumOfAttack);

        super.action(battlefield);
    }
}
