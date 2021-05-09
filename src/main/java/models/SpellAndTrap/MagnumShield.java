package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.FaceUp;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class MagnumShield extends SpellAndTrap  implements Serializable {

    public MagnumShield (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public MagnumShield (Object object){
        super(((MagnumShield)object).getName(), ((MagnumShield)object).getCardsType(),
                ((MagnumShield)object).getDescription(), ((MagnumShield)object).getPrice(),
                ((MagnumShield)object).getIcon(), ((MagnumShield)object).getStatus());
    }

    @Override
    public void action() {
        Duelist turn = Battlefield.getTurn();

        int counter = 0;
        ArrayList<Monster> trueMonsters = new ArrayList<>();
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster sample = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(sample) && targetedMonsters.size() == 0 && sample.getMonsterType().equals("Warrior")) {
                    trueMonsters.add(sample);
                    counter += 1;
                }
            }
        }

        if (targetedMonsters.size() > 0)
            UserInterface.printResponse("This spell has already equiped a monster.");
        else if (counter == 0)
            UserInterface.printResponse("You don't have monster with type Warrior in your monster zone");
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
                if (name.equalsIgnoreCase("cancel"))
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
        if (monster.getCardsFace() == FaceUp.ATTACK)
            monster.setAttack(monster.getAttack() + monster.getDefence());
        else
            monster.setDefence(monster.getDefence() + monster.getAttack());
    }
}
