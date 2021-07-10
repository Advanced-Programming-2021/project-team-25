package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;
import java.util.ArrayList;

public class ClosedForest extends SpellAndTrap  implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;
    private static int numberOfAttackToAdd = 0;

    public ClosedForest (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public ClosedForest (Object object){
        super(((ClosedForest)object).getName(), ((ClosedForest)object).getCardsType(),
                ((ClosedForest)object).getDescription(), ((ClosedForest)object).getPrice(),
                ((ClosedForest)object).getIcon(), ((ClosedForest)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();
        opponent = battlefield.getOpponent();

        if (opponent.getName().equalsIgnoreCase("Game")) {
            opponent.field.fieldZone = this;
            opponent.field.hand.remove(this);
            turn = battlefield.getOpponent();
            opponent = battlefield.getTurn();
        }
        else {
            turn.field.fieldZone = this;
            turn.field.hand.remove(this);
        }


        numberOfAttackToAdd = 100 * turn.field.graveYard.size();

        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster turnMonster = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(turnMonster)){
                    commonAction(turnMonster, numberOfAttackToAdd);
                }
            }
        }
    }

    public void commonAction (Monster monster, int numberToAdd){
        targetedMonsters.add(monster);
        if (monster.getMonsterType().equals("Beast")){
            monster.setAttack(monster.getAttack() + numberToAdd);
            monster.setDefence(monster.getDefence() + numberToAdd);
        }
    }

    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        for (Monster targetedMonster : targetedMonsters) {
            targetedMonster.setAttack(targetedMonster.getAttack() - numberOfAttackToAdd);
            targetedMonster.setDefence(targetedMonster.getDefence() - numberOfAttackToAdd);
        }
        targetedMonsters = new ArrayList<>();
        if (battlefield.getTurn().field.fieldZone == this) {
            battlefield.getTurn().field.graveYard.add(this);
            battlefield.getTurn().field.fieldZone = null;
        }
        if (battlefield.getOpponent().field.fieldZone == this) {
            battlefield.getOpponent().field.graveYard.add(this);
            battlefield.getOpponent().field.fieldZone = null;
        }
    }


}
