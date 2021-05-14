package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class UnitedWeStand extends SpellAndTrap implements Serializable {
    private static Duelist turn;
    private static int attackToIncrease = 0;

    public UnitedWeStand (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public UnitedWeStand (Object object){
        super(((UnitedWeStand)object).getName(), ((UnitedWeStand)object).getCardsType(),
                ((UnitedWeStand)object).getDescription(), ((UnitedWeStand)object).getPrice(),
                ((UnitedWeStand)object).getIcon(), ((UnitedWeStand)object).getStatus());
    }

    @Override
    public void action(Battlefield battlefield) {
        turn = battlefield.getTurn();

        int numberToIncrease = 0;
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null && (turn.field.monsterZone.get(i).getCardsFace() == FaceUp.ATTACK
                    || turn.field.monsterZone.get(i).getCardsFace() == FaceUp.DEFENSE_FRONT)){
                numberToIncrease += 1;
            }
        }

        int counter = 0;
        ArrayList<Monster> trueMonsters = new ArrayList<>();
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster sample = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(sample) && targetedMonsters.size() == 0) {
                    trueMonsters.add(sample);
                    counter += 1;
                }
            }
        }

        attackToIncrease = 800 * numberToIncrease;

        if (targetedMonsters.size() > 0)
            UserInterface.printResponse("This spell has already equipped a monster.");
        else if (counter == 0)
            UserInterface.printResponse("You don't have any monster in your monster zone");
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
                        equipMonster(trueMonster, attackToIncrease);
                        break;
                    }
                }
                if (name.equalsIgnoreCase("cancel"))
                    return;
                if (name.equals(" "))
                    UserInterface.printResponse("Insert a valid name please.");
                else
                    break;
            }
        }
    }


    public void equipMonster (Monster monster, int attack){
        targetedMonsters.add(monster);
        monster.setAttack(monster.getAttack() + attack);
        monster.setDefence(monster.getDefence() - attack);
    }

    @Override
    public void removeSpellOrTrap(Battlefield battlefield) {
        if (targetedMonsters.get(0) != null) {
            targetedMonsters.get(0).setAttack(targetedMonsters.get(0).getAttack() - attackToIncrease);
            targetedMonsters.get(0).setDefence(targetedMonsters.get(0).getDefence() + attackToIncrease);
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
