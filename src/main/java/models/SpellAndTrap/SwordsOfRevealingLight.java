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
        expireTime = 3;
    }

    public SwordsOfRevealingLight (Object object){
        super(((SwordsOfRevealingLight)object).getName(), ((SwordsOfRevealingLight)object).getCardsType(),
                ((SwordsOfRevealingLight)object).getDescription(), ((SwordsOfRevealingLight)object).getPrice(),
                ((SwordsOfRevealingLight)object).getIcon(), ((SwordsOfRevealingLight)object).getStatus());
        expireTime = 3;
    }

    @Override
    public void action(Battlefield battlefield) {
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

    private void setMonsterCanAttackOrNot(boolean canAttack) {
        for(Card card : opponent.field.monsterZone){
            ((Monster)card).setCanAttack(canAttack);
        }
    }

    @Override
    public void removeSpellOrTrap(String name) {
        setMonsterCanAttackOrNot(true);
    }
}
