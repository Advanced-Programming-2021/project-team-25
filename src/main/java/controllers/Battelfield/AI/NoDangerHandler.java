package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import models.Card;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import view.UserInterface;

import java.util.Objects;

public class NoDangerHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(battlefield.changedTurnTime<=1 || countOpponentMonsterInAttackPosition(battlefield) == 0){
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
}
