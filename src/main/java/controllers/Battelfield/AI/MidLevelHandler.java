package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import view.UserInterface;

public class MidLevelHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(countOpponentMonsterInAttackPosition(battlefield)<=4 || someOfAttacksOfOpponentMonster(battlefield) <= 1000){

        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }

    }
}
