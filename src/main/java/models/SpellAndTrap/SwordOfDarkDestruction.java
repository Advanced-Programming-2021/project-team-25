package models.SpellAndTrap;

import controllers.Battelfield.Battlefield;
import models.CardStufs.Type;
import models.Duelist;
import models.Monster.Monster;
import view.UserInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class SwordOfDarkDestruction extends SpellAndTrap implements Serializable {

    public SwordOfDarkDestruction (String name, Type cardType, String description, int price, String icon, String status){
        super(name, cardType, description, price, icon, status);
    }

    public SwordOfDarkDestruction (Object object){
        super(((SwordOfDarkDestruction)object).getName(), ((SwordOfDarkDestruction)object).getCardsType(),
                ((SwordOfDarkDestruction)object).getDescription(), ((SwordOfDarkDestruction)object).getPrice(),
                ((SwordOfDarkDestruction)object).getIcon(), ((SwordOfDarkDestruction)object).getStatus());
    }

    @Override
    public void action() {
        Duelist turn = Battlefield.getTurn();

        int counter = 0;
        ArrayList<Monster> numbers = new ArrayList<>();
        for (int i = 0; i<5; ++i){
            if (turn.field.monsterZone.get(i) != null){
                Monster temp = (Monster) turn.field.monsterZone.get(i);
                if (!targetedMonsters.contains(temp) && temp.getMonsterType().equals("Fiend")) {
                    counter += 1;
                    numbers.add(temp);
                }
                else if (!targetedMonsters.contains(temp) && temp.getMonsterType().equals("Spellcaster")) {
                    counter += 1;
                    numbers.add(temp);
                }
            }
        }

        if (counter == 0)
            UserInterface.printResponse("You can't activate this spell because you dont have any monster " +
                    "with type of Fiend or Spellcaster");
        else{
            UserInterface.printResponse("Now select one of these card(s):");
            for (Monster number : numbers)
                UserInterface.printResponse(number.getName() + ":" + number.getDescription());


            String name = " ";
            while (true){
                String command = UserInterface.getUserInput();
                for (Monster number : numbers)
                    if (number.getName().equals(command)){
                        name = command;
                        spellActivate(number);
                        break;
                    }
                if (name.equals(" "))
                    UserInterface.printResponse("Please insert a valid monster name.");
            }
        }
    }

    public void spellActivate (Monster monster){
        targetedMonsters.add(monster);
        monster.setAttack(monster.getAttack() + 400);
        monster.setDefence(monster.getDefence() - 200);
    }


}
