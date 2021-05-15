package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import controllers.Regex;
import models.CardStufs.Type;
import view.UserInterface;

public class HighLevelHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(countOpponentMonsterInAttackPosition(battlefield)>=4 || someOfAttacksOfOpponentMonster(battlefield)>=2000){
            //if we have yomi ship in hand or some spells
        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }
    }
}
