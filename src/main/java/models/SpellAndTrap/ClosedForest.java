package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;

public class ClosedForest extends SpellAndTrap  implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;

    public ClosedForest (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public ClosedForest (Object object){
        super(((ClosedForest)object).getName(), ((ClosedForest)object).getCardsType(),
                ((ClosedForest)object).getDescription(), ((ClosedForest)object).getPrice(),
                ((ClosedForest)object).getIcon(), ((ClosedForest)object).getStatus());
    }

    @Override
    public void action() {
        turn = Battlefield.getTurn();
        opponent = Battlefield.getOpponent();

        int numberOfAttackToAdd = 100 * turn.field.graveYard.size();

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
}
