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
    default int getMostAttackingMonsterPower(Battlefield battlefield) {
        int attack = 0;
        for(Card card : battlefield.getTurn().field.monsterZone){
            if(!Objects.isNull(card)){
                if(card.getCardsFace().equals(FaceUp.ATTACK) && ((Monster)card).getAttack() > attack)
                    attack = ((Monster)card).getAttack();
            }
        }
        return attack;
    }
    default Monster getMostAttackingMonsterOfHand(Battlefield battlefield) {
        Monster  monster = null;
        for(Card card : battlefield.getOpponent().field.hand){
            if(!Objects.isNull(card)){
                if (Objects.isNull(monster) || ((Monster) card).getAttack() > monster.getAttack())
                monster = (Monster) card;
            }
        }
        return monster;
    }
    default Monster getMostDefendingMonsterOfHand(Battlefield battlefield) {
        Monster  monster = null;
        for(Card card : battlefield.getOpponent().field.hand){
            if(!Objects.isNull(card)){
                if (Objects.isNull(monster) || ((Monster) card).getDefence() > monster.getDefence())
                    monster = (Monster) card;
            }
        }
        return monster;
    }
}
