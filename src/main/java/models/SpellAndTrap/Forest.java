package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;
import java.util.ArrayList;

public class Forest extends SpellAndTrap implements Serializable {
    private static Duelist turn;
    private static Duelist opponent;

    public Forest (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public Forest (Object object){
        super(((Forest)object).getName(), ((Forest)object).getCardsType(),
                ((Forest)object).getDescription(), ((Forest)object).getPrice(),
                ((Forest)object).getIcon(), ((Forest)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();
        opponent = battlefield.getOpponent();

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
        if (monster.getMonsterType().equals("Beast-Warrior") || monster.getMonsterType().equals("Beast") ||
                monster.getMonsterType().equals("Insect")){
            monster.setAttack(monster.getAttack() + 200);
            monster.setDefence(monster.getDefence() + 200);
        }
    }

    @Override
    public void removeSpellOrTrap(String name) {
        for (int i = 0; i<targetedMonsters.size(); ++i){
            targetedMonsters.get(i).setAttack(targetedMonsters.get(i).getAttack() - 200);
            targetedMonsters.get(i).setDefence(targetedMonsters.get(i).getDefence() - 200);
        }
        targetedMonsters = new ArrayList<>();
        turn.field.graveYard.add(this);
        turn.field.fieldZone = null;
    }
}
