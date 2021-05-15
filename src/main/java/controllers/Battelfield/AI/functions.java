package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.FaceUp;
import models.Monster.Monster;

import java.util.Objects;

public interface functions {
    default int countOpponentMonsterInAttackPosition(Battlefield battlefield) {
        int counter = 0;
        for(Card card : battlefield.getTurn().field.monsterZone){
            if(!Objects.isNull(card)){
                if(card.getCardsFace().equals(FaceUp.ATTACK))
                    counter++;
            }
        }
        return counter;
    }
    default int someOfAttacksOfOpponentMonster(Battlefield battlefield) {
        int attack = 0;
        for(Card card : battlefield.getTurn().field.monsterZone){
            if(!Objects.isNull(card)){
                if(card.getCardsFace().equals(FaceUp.ATTACK))
                    attack += ((Monster)card).getAttack();
            }
        }
        return attack;
    }
}
