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
        if(countOpponentMonsterInAttackPosition(battlefield) == 0 || (someOfAttacksOfOpponentMonster(battlefield) >= 0 && someOfAttacksOfOpponentMonster(battlefield) < 500)){

        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }
    }
}
