package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;
import java.util.ArrayList;

public class Umiiruka extends SpellAndTrap implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;

    public Umiiruka (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Umiiruka (Object object){
        super(((Umiiruka)object).getName(), ((Umiiruka)object).getCardsType(),
                ((Umiiruka)object).getDescription(), ((Umiiruka)object).getPrice(),
                ((Umiiruka)object).getIcon(), ((Umiiruka)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();
        opponent = battlefield.getOpponent();

        if (opponent.getName().equalsIgnoreCase("admin")) {
            opponent.field.fieldZone = this;
            opponent.field.hand.remove(this);
        }
        else {
            turn.field.fieldZone = this;
            turn.field.hand.remove(this);
        }

        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster turnMonster = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(turnMonster)){
                    commonAction(turnMonster);
                }
            }
            if (opponent.field.monsterZone.get(i) != null){
                Monster opponentMonster = (Monster) opponent.field.monsterZone.get(i);
                if (!targetedMonsters.contains(opponentMonster)){
                    commonAction(opponentMonster);
                }
            }
        }
    }

    public void commonAction (Monster monster){
        targetedMonsters.add(monster);
        if (monster.getMonsterType().equals("Aqua")){
            monster.setAttack(monster.getAttack() + 500);
            monster.setDefence(monster.getDefence() - 400);
        }
    }

    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        for (Monster targetedMonster : targetedMonsters) {
            targetedMonster.setAttack(targetedMonster.getAttack() - 500);
            targetedMonster.setDefence(targetedMonster.getDefence() + 400);
        }
        targetedMonsters = new ArrayList<>();
        turn.field.graveYard.add(this);
        turn.field.fieldZone = null;
    }
}
