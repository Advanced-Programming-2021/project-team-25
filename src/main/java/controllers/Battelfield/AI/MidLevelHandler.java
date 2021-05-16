package controllers.Battelfield.AI;

import controllers.Battelfield.Battlefield;
import models.Card;
import models.CardStufs.Type;
import models.Monster.Monster;
import models.SpellAndTrap.SpellAndTrap;
import view.UserInterface;

public class MidLevelHandler extends AIHandler implements functions{
    @Override
    public void handle(Battlefield battlefield) {
        if(countOpponentMonsterInAttackPosition(battlefield)<=4 || someOfAttacksOfOpponentMonster(battlefield) <= 1000){

            int mostPowerOfOpponentMonster = getMostAttackingMonsterPower(battlefield);
            Monster mostAttackingMonsterOfHand = getMostAttackingMonsterOfHand(battlefield);
            Monster mostDefenceMonsterInHand = getMostDefendingMonsterOfHand(battlefield);
            //in this statement try to active spell or trap
            if(mostAttackingMonsterOfHand.getAttack() < mostPowerOfOpponentMonster){
                if(battlefield.getOpponent().field.spellTrapZone.size()>0)
                    useSpellOrTrapForDefence(battlefield);
                else {
                    if(mostDefenceMonsterInHand.getLevel()<5){
                        battlefield.selectedCard = mostDefenceMonsterInHand;
                        battlefield.set();
                    }
                    //else need command
                }
            }
        }
        else{
            if(nextHandler != null) nextHandler.handle(battlefield);
            else UserInterface.printResponse("Ai Done!");
        }

    }

    private void useSpellOrTrapForDefence(Battlefield battlefield) {
        for(Card card : battlefield.getOpponent().field.spellTrapZone){
            SpellAndTrap spellAndTrap = (SpellAndTrap) card;
            String name = spellAndTrap.getName();
            if(name.equals("Swords of Revealing Light") ||
                    spellAndTrap.getName().equals("Dark Hole") ||
                    spellAndTrap.getName().equals("Spell Absorption") ||
                    spellAndTrap.getName().equals("Magic Cylinder")){
                spellAndTrap.action(battlefield);
                return;
            }
        }
    }
}
