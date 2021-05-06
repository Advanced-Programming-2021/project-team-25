package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class BlackPendant extends SpellAndTrap  implements Serializable {

    public BlackPendant (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public BlackPendant (Object object){
        super(((BlackPendant)object).getName(), ((BlackPendant)object).getCardsType(),
                ((BlackPendant)object).getDescription(), ((BlackPendant)object).getPrice(),
                ((BlackPendant)object).getIcon(), ((BlackPendant)object).getStatus());
    }

    @Override
    public void action() {
        Duelist turn = Battlefield.getTurn();

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

        if (targetedMonsters.size() > 0)
            UserInterface.printResponse("This spell has already equiped a monster.");
        else if (counter == 0)
            UserInterface.printResponse("You don't have any monster in your monster zone.");
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
                if (name.equals(" "))
                    UserInterface.printResponse("Insert a valid name please.");
                else
                    break;
            }
        }
    }


    public void equipMonster (Monster monster){
        targetedMonsters.add(monster);
        monster.setAttack(monster.getAttack() + 500);
    }
}
