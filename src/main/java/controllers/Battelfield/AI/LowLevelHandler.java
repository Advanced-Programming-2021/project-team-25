package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import view.UserInterface;

public class LowLevelHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(countOpponentMonsterInAttackPosition(battlefield)<=2 || someOfAttacksOfOpponentMonster(battlefield) <= 600){

        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }
    }
}
