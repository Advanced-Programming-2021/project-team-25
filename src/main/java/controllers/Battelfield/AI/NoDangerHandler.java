package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import view.UserInterface;

import java.util.Objects;

public class NoDangerHandler extends AIHandler{
    @Override
    public void handle(Battlefield battlefield) {
        if(battlefield.changedTurnTime<=1 || isOpponentNoMonsterInAttackPosition(battlefield)){
            for(int i=1;i<=3;i++){
                battlefield.selectCard(Regex.getMatcher("select --hand 1",Regex.select));
                if(battlefield.selectedCard.getCardsType().equals(Type.SPELL)) battlefield.set();
                else if(battlefield.selectedCard.getCardsType().equals(Type.MONSTER)) battlefield.summon();
            }
        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }
    }

    private boolean isOpponentNoMonsterInAttackPosition(Battlefield battlefield) {
        for(Card card : battlefield.getTurn().field.monsterZone){
            if(!Objects.isNull(card)){
                if(card.getCardsFace().equals(FaceUp.ATTACK))
                    return false;
            }
        }
        return true;
    }
}
