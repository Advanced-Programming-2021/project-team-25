package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import controllers.Battelfield.Phase;
import models.Card;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;
import java.util.ArrayList;

public class MessengerOfPeace extends SpellAndTrap implements Serializable {
    //get turn and opponent
    Duelist opponent;
    Duelist turn;

    public MessengerOfPeace (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MessengerOfPeace (Object object){
        super(((MessengerOfPeace)object).getName(), ((MessengerOfPeace)object).getCardsType(),
                ((MessengerOfPeace)object).getDescription(), ((MessengerOfPeace)object).getPrice(),
                ((MessengerOfPeace)object).getIcon(), ((MessengerOfPeace)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        opponent = battlefield.getOpponent();
        turn = battlefield.getTurn();
        //monster wit attack >= 1500 cant attack
        for(Card card : opponent.field.monsterZone){
            if(((Monster)card).getAttack()>=1500){
                targetedMonsters.add((Monster)card);
                ((Monster)card).setCanAttack(false);
            }
        }
    }

    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        //clearing things done
        for(Card card : opponent.field.monsterZone){
            if(((Monster)card).getAttack()>=1500){
                targetedMonsters.remove(card);
                ((Monster)card).setCanAttack(true);
            }
        }
    }
}
