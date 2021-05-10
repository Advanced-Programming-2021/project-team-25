package models.Monster;

import controllers.Battelfield.Battlefield;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import view.UserInterface;

import javax.management.BadAttributeValueExpException;
import java.io.Serializable;

public class Marshmallon extends Monster implements Serializable {

    public Marshmallon (String name, Type cardType, String description, int price, int level, String attribute,
                              String monsterType, String cardTypeInExel, int attack, int defence){
        super(name, cardType, description, price, level, attribute, monsterType, cardTypeInExel, attack, defence);
    }

    public Marshmallon (Object object){
        super(((Marshmallon)object).getName(), ((Marshmallon)object).getCardsType(),
                ((Marshmallon)object).getDescription(), ((Marshmallon)object).getPrice(),
                ((Marshmallon)object).getLevel(), ((Marshmallon)object).getAttribute(),
                ((Marshmallon)object).getMonsterType(), ((Marshmallon)object).getCardTypeInExel(),
                ((Marshmallon)object).getAttack(), ((Marshmallon)object).getDefence());
    }

    public void action(Battlefield battlefield) {

        if(this.getCardsFace().equals(FaceUp.DEFENSE_BACK))
            defence(battlefield);
//        else if(this.getCardsFace().equals(FaceUp.ATTACK))
//            battlefield.attack();

    }
    @Override
    public void removeMonster(Battlefield battlefield){
        UserInterface.printResponse("Marshmallon can not die in normal fight!");
    }
    @Override
    public void defence(Battlefield battlefield){
        battlefield.getOpponent().LP -= 1000;
    }
}
