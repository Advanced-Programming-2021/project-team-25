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

    public UnitedWeStand (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public UnitedWeStand (Object object){
        super(((UnitedWeStand)object).getName(), ((UnitedWeStand)object).getCardsType(),
                ((UnitedWeStand)object).getDescription(), ((UnitedWeStand)object).getPrice(),
                ((UnitedWeStand)object).getIcon(), ((UnitedWeStand)object).getStatus());
    }

    @Override
    public void action() {
        Duelist turn = Battlefield.getTurn();

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

        int attackToIncrease = 800 * numberToIncrease;

        if (targetedMonsters.size() > 0)
            UserInterface.printResponse("This spell has already equiped a monster.");
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
                        equipMonster(trueMonster, attackToIncrease);
                        break;
                    }
                }
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
}
