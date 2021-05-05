package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;

import java.io.Serializable;

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
    public void action() {
        turn = Battlefield.getTurn();
        opponent = Battlefield.getOpponent();


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
        if (monster.getMonsterType().equals("Aqua")){
            monster.setAttack(monster.getAttack() + 500);
            monster.setDefence(monster.getDefence() - 400);
        }
    }
}
