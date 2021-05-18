package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;

public class SwordsOfRevealingLight extends SpellAndTrap implements Serializable {
    //get opponent
    Duelist opponent;

    public SwordsOfRevealingLight (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
        expireTime = 4;// 3 times for opponent means 4 time for us!
    }

    public SwordsOfRevealingLight (Object object){
        super(((SwordsOfRevealingLight)object).getName(), ((SwordsOfRevealingLight)object).getCardsType(),
                ((SwordsOfRevealingLight)object).getDescription(), ((SwordsOfRevealingLight)object).getPrice(),
                ((SwordsOfRevealingLight)object).getIcon(), ((SwordsOfRevealingLight)object).getStatus());
        expireTime = 4;
    }

    @Override
    public void action(Battlefield battlefield) {
        if(expireTime == 0){
            expireTime = 4;
            setExpired(true);
            removeSpellOrTrap(battlefield);
        }
        else{
            //added all targeted monsters
            for(Card card : opponent.field.monsterZone){
               targetedMonsters.add((Monster)card);
            }
            //expire after three play
            expireTime--;

            opponent = battlefield.getOpponent();
            //face up all cards
            for(Card card : opponent.field.monsterZone){
                if(card.getCardsFace().equals(FaceUp.DEFENSE_BACK))
                    card.setCardsFace(FaceUp.DEFENSE_FRONT);
            }
            //if this card is face up opponent cant attack
            if(this.getCardsFace().equals(FaceUp.ATTACK) || expireTime!=0)
                setMonsterCanAttackOrNot(false);
        }
    }

    private void setMonsterCanAttackOrNot(boolean canAttack) {
        for(Monster monster: targetedMonsters){
            monster.setCanAttack(canAttack);
        }
    }

    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        expireTime = 3;
        setMonsterCanAttackOrNot(true);
        super.removeSpellOrTrap(battlefield);
    }
}
