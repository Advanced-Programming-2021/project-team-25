package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class SwordOfDarkDestruction extends SpellAndTrap implements Serializable {
    private static Duelist turn;

    public SwordOfDarkDestruction (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SwordOfDarkDestruction (Object object){
        super(((SwordOfDarkDestruction)object).getName(), ((SwordOfDarkDestruction)object).getCardsType(),
                ((SwordOfDarkDestruction)object).getDescription(), ((SwordOfDarkDestruction)object).getPrice(),
                ((SwordOfDarkDestruction)object).getIcon(), ((SwordOfDarkDestruction)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();

        int counter = 0;
        ArrayList<Monster> trueMonsters = new ArrayList<>();
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster sample = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(sample) && targetedMonsters.size() == 0 &&
                        (sample.getMonsterType().equals("Fiend") || sample.getMonsterType().equals("Spellcaster"))) {
                    trueMonsters.add(sample);
                    counter += 1;
                }
            }
        }

        if (targetedMonsters.size() > 0)
            UserInterface.printResponse("This spell has already equipped a monster.");
        else if (counter == 0)
            UserInterface.printResponse("You don't have monster with type of Fiend or Spellcaster.");
        else{
            UserInterface.printResponse("Now select one of these monsters to equip it");
            for (Monster trueMonster : trueMonsters) {
                UserInterface.printResponse(trueMonster.getName() + ":" + trueMonster.getDescription());
            }
            String name = " ";
            while (true){
                String command = UserInterface.getUserInput();
                for (Monster trueMonster : trueMonsters) {
                    if (trueMonster.getName().equals(command)) {
                        name = command;
                        equipMonster(trueMonster);
                        break;
                    }
                }
                if (name.equals("cancel"))
                    return;
                if (name.equals(" "))
                    UserInterface.printResponse("Insert a valid name please.");
                else
                    break;
            }
        }
    }


    public void equipMonster (Monster monster){
        targetedMonsters.add(monster);
        monster.setAttack(monster.getAttack() + 400);
        monster.setDefence(monster.getDefence() - 200);
    }


    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        if (targetedMonsters.get(0) != null) {
            targetedMonsters.get(0).setAttack(targetedMonsters.get(0).getAttack() - 400);
            targetedMonsters.get(0).setDefence(targetedMonsters.get(0).getDefence() + 200);
        }
        targetedMonsters = new ArrayList<>();
        turn.field.graveYard.add(this);
        for (int i = 0; i<5; ++i){
            if (turn.field.spellTrapZone.get(i) == this){
                turn.field.spellTrapZone.set(i, null);
                break;
            }
        }
    }

}
