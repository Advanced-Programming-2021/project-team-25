package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;

public class SwordsOfRevealingLight extends SpellAndTrap implements Serializable {
    int expireTime = 3;
    //get opponent
    Duelist opponent = Battlefield.getOpponent();

    public SwordsOfRevealingLight (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SwordsOfRevealingLight (Object object){
        super(((SwordsOfRevealingLight)object).getName(), ((SwordsOfRevealingLight)object).getCardsType(),
                ((SwordsOfRevealingLight)object).getDescription(), ((SwordsOfRevealingLight)object).getPrice(),
                ((SwordsOfRevealingLight)object).getIcon(), ((SwordsOfRevealingLight)object).getStatus());
    }

    @Override
    public void action() {
        //face up all cards
        for(Card card : opponent.field.monsterZone){
            if(card.getCardsFace().equals(FaceUp.DEFENSE_BACK))
                card.setCardsFace(FaceUp.DEFENSE_FRONT);
            else if(card.getCardsFace().equals(FaceUp.ATTACK_BACK))
                card.setCardsFace(FaceUp.ATTACK);
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
